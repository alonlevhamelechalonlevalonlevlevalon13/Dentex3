package com.example.dentex.view.Fragments;

import static androidx.core.app.ActivityCompat.invalidateOptionsMenu;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;


import com.example.dentex.R;
import com.example.dentex.view.PatientActivity;

public class pt_newAppointment_fr extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String treatmentType;
    public static String drName;
    private Activity activity;
    private Button buttonDoc;
    private Button buttonTreat;
    private Button buttonSearch;
    private String mParam1;
    private String mParam2;

    public pt_newAppointment_fr() {

    }
    public pt_newAppointment_fr(Activity activity){
        this.activity = activity;
    }
    public static pt_newAppointment_fr newInstance(String param1, String param2) {
        pt_newAppointment_fr fragment = new pt_newAppointment_fr();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        buttonDoc = view.findViewById(R.id.buttonDoc);
        buttonDoc.setEnabled(false);
        buttonTreat = view.findViewById(R.id.button);
        buttonSearch = view.findViewById(R.id.buttonSubmit2);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity instanceof PatientActivity){
                    ((PatientActivity) activity).replaceFragment(new pt_Appoints_fr());
                }
            }
        });
        buttonTreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);


                popupMenu.getMenuInflater().inflate(R.menu.select_treatment, popupMenu.getMenu());
                MenuItem checkup = popupMenu.getMenu().findItem(R.id.option1);
                MenuItem uprooting = popupMenu.getMenu().findItem(R.id.option2);
                MenuItem filling = popupMenu.getMenu().findItem(R.id.option3);
                MenuItem cleaning = popupMenu.getMenu().findItem(R.id.option4);
                MenuItem root_canal = popupMenu.getMenu().findItem(R.id.option5);
                checkup.setEnabled(false);
                uprooting.setEnabled(false);
                filling.setEnabled(false);
                cleaning.setEnabled(false);
                root_canal.setEnabled(false);
                invalidateOptionsMenu(getActivity());
                if (drName == null) {
                    checkup.setEnabled(true);
                    uprooting.setEnabled(true);
                    filling.setEnabled(true);
                    cleaning.setEnabled(true);
                    root_canal.setEnabled(true);
                }
                else if (drName.equals("dor aines") || drName.equals("sivan shafir")) {
                    checkup.setEnabled(true);
                    filling.setEnabled(true);
                } else if (drName.equals("nofar levy")) {
                    cleaning.setEnabled(true);
                } else if (drName.equals("guy navon")) {
                    root_canal.setEnabled(true);
                } else if (drName.equals("gal herman")) {
                    uprooting.setEnabled(true);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        buttonTreat.setText(item.getTitle());
                        treatmentType = item.getTitle().toString();
                        buttonDoc.setEnabled(true);
                        return true;
                    }
                });

                // Show the PopupMenu
                popupMenu.show();
            }
        });
        buttonDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDoctorPopup(v);
            }
        });
        return view;
    }

    private void createDoctorPopup(View v) {
        // Create a PopupMenu
        PopupMenu popupMenu = new PopupMenu(getContext(), v);

        // Inflate the menu (from a resource file)
        popupMenu.getMenuInflater().inflate(R.menu.select_doc, popupMenu.getMenu());
        MenuItem dor = popupMenu.getMenu().findItem(R.id.option1);
        MenuItem guy = popupMenu.getMenu().findItem(R.id.option2);
        MenuItem gal = popupMenu.getMenu().findItem(R.id.option3);
        MenuItem sivan = popupMenu.getMenu().findItem(R.id.option4);
        MenuItem nofar = popupMenu.getMenu().findItem(R.id.option5);
        dor.setEnabled(false);
        guy.setEnabled(false);
        gal.setEnabled(false);
        sivan.setEnabled(false);
        nofar.setEnabled(false);
        invalidateOptionsMenu(getActivity());
        if (treatmentType.equals(null)) {
            dor.setEnabled(true);
            guy.setEnabled(true);
            gal.setEnabled(true);
            sivan.setEnabled(true);
            nofar.setEnabled(true);
        }
        if (treatmentType.equals("checkup")) {
            dor.setEnabled(true);
            sivan.setEnabled(true);
        } else if (treatmentType.equals("uprooting")) {
            gal.setEnabled(true);
        } else if (treatmentType.equals("filling")) {
            dor.setEnabled(true);
            sivan.setEnabled(true);
        } else if (treatmentType.equals("cleaning")) {
            nofar.setEnabled(true);
        } else if (treatmentType.equals("root canal")) {
            guy.setEnabled(true);
        }
        // Set item click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                buttonDoc.setText(item.getTitle());
                drName = item.getTitle().toString();
                return true;
            }
        });
        // Show the PopupMenu
        popupMenu.show();
    }

}