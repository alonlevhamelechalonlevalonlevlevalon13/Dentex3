package com.example.dentex.view.Fragments;

import static com.example.dentex.utils.FBUserHelper.DataBase;
import static com.example.dentex.view.Fragments.pt_newAppointment_fr.drName;
import static com.example.dentex.view.Fragments.pt_newAppointment_fr.treatmentType;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dentex.model.Appointment;
import com.example.dentex.R;
import com.example.dentex.view.Adapters.PtFreeAppointmentAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class pt_Appoints_fr extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    Query query;
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
        View view = inflater.inflate(R.layout.fragment_pt_appoints, container, false);
        Context context = getContext();
        PtFreeAppointmentAdapter itemAdapter = new PtFreeAppointmentAdapter(context, options());
        recyclerView = view.findViewById(R.id.Appointments);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(itemAdapter);
            itemAdapter.startListening();
        }
        return  view;
    }

    private FirestoreRecyclerOptions<Appointment> options() {
        if (drName==null||treatmentType==null){
             query = DataBase.collection("openappointments")
                    .whereGreaterThan("date", new Date())
                    .orderBy("date", Query.Direction.ASCENDING);
        } else {
            query = DataBase.collection("openappointments")
                .whereGreaterThan("date", new Date())
                .whereEqualTo("drname", drName)
                .whereEqualTo("treatmentType", treatmentType)
                .orderBy("date", Query.Direction.ASCENDING);
        }
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query , Appointment.class)
                .build();
        return options;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

}