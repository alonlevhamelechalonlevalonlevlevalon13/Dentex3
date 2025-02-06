package com.example.dentex.Patient;

import static androidx.core.app.ActivityCompat.invalidateOptionsMenu;

import static com.example.dentex.FireBase.FBUserHelper.db;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.example.dentex.Appointments.Appointment;
import com.example.dentex.Appointments.AppointmentHelper;
import com.example.dentex.R;

import java.util.Calendar;
import java.util.Date;

public class pt_newAppointment_fr extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String treatmentType = "All";
    public static String drName;
    private Activity activity;
    private Button buttonDoc;
    private Button buttonTreat;
    private Button buttonGen;
    private Button button1;
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
        button1 = view.findViewById(R.id.buttonSubmit2);
        button1.setOnClickListener(new View.OnClickListener() {
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
                // Create a PopupMenu
                PopupMenu popupMenu = new PopupMenu(getContext(), v);

                // Inflate the menu (from a resource file)
                popupMenu.getMenuInflater().inflate(R.menu.select_treatment, popupMenu.getMenu());

                // Set item click listener
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
        if (treatmentType.equals("All")) {
            dor.setEnabled(true);
            guy.setEnabled(true);
            gal.setEnabled(true);
            sivan.setEnabled(true);
            nofar.setEnabled(true);
        }
        if (treatmentType.equals("בדיקה")) {
            dor.setEnabled(true);
            sivan.setEnabled(true);
        } else if (treatmentType.equals("עקירה")) {
            gal.setEnabled(true);
        } else if (treatmentType.equals("סתימה")) {
            dor.setEnabled(true);
            sivan.setEnabled(true);
        } else if (treatmentType.equals("ניקוי")) {
            nofar.setEnabled(true);
        } else if (treatmentType.equals("טיפול שורש")) {
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