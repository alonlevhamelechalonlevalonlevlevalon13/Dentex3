package com.example.dentex.Patient;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dentex.Appointments.Appointment;
import com.example.dentex.Appointments.AppointmentHelper;
import com.example.dentex.Appointments.PtAppointmentAdapter;
import com.example.dentex.R;

import java.util.ArrayList;
import java.util.List;

public class pt_Appoints_fr extends Fragment implements PtAppointmentAdapter.ItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    PtAppointmentAdapter adapter;
    private String mParam1;
    private String mParam2;

    public pt_Appoints_fr() {
    }


    public static pt_Appoints_fr newInstance(String param1, String param2) {
        pt_Appoints_fr fragment = new pt_Appoints_fr();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pt_appoints, container, false);
        ArrayList<Appointment> appointments = getlist();
        RecyclerView recyclerView = view.findViewById(R.id.Appointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PtAppointmentAdapter(getContext(), appointments);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        return view;

    }

    private ArrayList<Appointment> getlist() {
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                return appointments;
            }

            @Override
            public void onAppointmentsError(Exception e) {

            }
        });
        return null;
    }

    @Override
    public void onItemClick(View view, int position) {
        setupDialog();
    }

    private void setupDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("האם אתה בטוח?")
                .setMessage("אתה בטוח שאתה רוצה לקבוע את התור הזה?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "Yes" action
                        performAction();
                    }
                })
                .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "הוספת התור התבטלה", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void performAction() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS},100);
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                Appointment nearestAppointment = AppointmentHelper.getNearestAppointment(appointments);
                if (nearestAppointment != null) {
                    AppointmentHelper.setAlarmForAppointment(getContext(), nearestAppointment, "");
                    Toast.makeText(getContext(), "nearest"+nearestAppointment.getDate().toString(), Toast.LENGTH_SHORT).show();
                }
                return appointments;
            }

            @Override
            public void onAppointmentsError(Exception e) {
                //Handle error
            }
        });
    }
}