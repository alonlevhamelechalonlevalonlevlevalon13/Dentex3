package com.example.dentex.Patient;

import static com.example.dentex.FireBase.FBUserHelper.db;
import static com.example.dentex.Patient.pt_newAppointment_fr.drName;
import static com.example.dentex.Patient.pt_newAppointment_fr.treatmentType;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dentex.Appointments.Appointment;
import com.example.dentex.Appointments.AppointmentHelper;
import com.example.dentex.Appointments.PtCalendarAdapter;
import com.example.dentex.Appointments.PtFreeAppointmentAdapter;
import com.example.dentex.FireBase.FBUserHelper;
import com.example.dentex.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class pt_Appoints_fr extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    PtCalendarAdapter adapter;
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;

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
        return  inflater.inflate(R.layout.fragment_pt_appoints, container, false);
    }

    private FirestoreRecyclerOptions<Appointment> options() { //פילטרים לתורים פנויים
        Query query = db.collection("openappointments")
                .whereGreaterThan("date", new Date())
                .whereEqualTo("drname", drName)
                .whereEqualTo("treatmentType", treatmentType)
                .orderBy("date", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query , Appointment.class)
                .build();
        return options;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                if (appointments != null && !appointments.isEmpty()) {
                    Context context = requireContext();  // or getContext() if you're certain it's not null
                    PtFreeAppointmentAdapter itemAdapter = new PtFreeAppointmentAdapter(context, options());
                    recyclerView = view.findViewById(R.id.Appointments);

                    // Check if recyclerView is valid before using it
                    if (recyclerView != null) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(itemAdapter);
                        itemAdapter.startListening();
                    }
                }
                return appointments;
            }

            @Override
            public void onAppointmentsError(Exception e) {

            }
        });

    }

}