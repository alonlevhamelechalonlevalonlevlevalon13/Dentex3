package com.example.dentex.Patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentex.R;

import java.util.ArrayList;
import java.util.Date;

public class pt_CalendarFr extends Fragment {
    private String param1;
    private String param2;
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
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar,
                container, false);
    }

    public static pt_CalendarFr newInstance(String param1,
                                            String param2)
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
        ArrayList<Appointment> Applist = getData();
        PtAppointmentAdapter itemAdapter = new PtAppointmentAdapter(Applist);
        RecyclerView recyclerView = view.findViewById(R.id.Rv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(itemAdapter);
    }
    public ArrayList<Appointment> getData(){
        ArrayList<Appointment> AppointmentList = new ArrayList<Appointment>();
        Date date = new Date(2024,12,03,11,30);
        Appointment emp1 = new Appointment(date, "גל הרמן", "עקירה");
        AppointmentList.add(emp1);
        Appointment emp2 = new Appointment(date,"גיא נבון", "טיפול שורש");
        AppointmentList.add(emp2);
        date.setMonth(11);
        Appointment emp3 = new Appointment(date,"דור איינס", "בדיקה");
        AppointmentList.add(emp3);
        return AppointmentList;
    }
}


