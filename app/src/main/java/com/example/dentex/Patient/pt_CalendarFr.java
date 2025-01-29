package com.example.dentex.Patient;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentex.Appointments.Appointment;
import com.example.dentex.Appointments.AppointmentHelper;
import com.example.dentex.Appointments.PtAppointmentAdapter;
import com.example.dentex.FireBase.FBUserHelper;
import com.example.dentex.FireBase.User;
import com.example.dentex.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class pt_CalendarFr extends Fragment  {
    private String param1;
    private String param2;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getString("param1");
            param2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    public static pt_CalendarFr newInstance(String param1, String param2)
    {
        pt_CalendarFr fragment = new pt_CalendarFr();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(), "YOW YOW YOP", Toast.LENGTH_SHORT).show();
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                if (appointments != null && !appointments.isEmpty()) {
                    Context context = requireContext();  // or getContext() if you're certain it's not null
                    PtAppointmentAdapter itemAdapter = new PtAppointmentAdapter(context, options());
                    recyclerView = view.findViewById(R.id.Rv1);

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
    private FirestoreRecyclerOptions<Appointment> options() {
        //Query query = FBUserHelper.getCollectionRefAppo();
        Query query = FBUserHelper.getCollectionRefAppo()
                .whereGreaterThan("date", new Date())
                .orderBy("date", Query.Direction.ASCENDING);
                FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query , Appointment.class)
                .build();
        return options;
    }

}



