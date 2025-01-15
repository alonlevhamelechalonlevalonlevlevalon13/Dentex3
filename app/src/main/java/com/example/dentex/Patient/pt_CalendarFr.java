package com.example.dentex.Patient;

import android.Manifest;
import android.app.AlertDialog;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class pt_CalendarFr extends Fragment implements FBUserHelper.FBReply,PtAppointmentAdapter.ItemClickListener {
    private String param1;
    private String param2;
    private FBUserHelper fbUserHelper;
    private List<Appointment> appointment;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getString("param1");
            param2 = getArguments().getString("param2");
        }
         fbUserHelper = new FBUserHelper(this);
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
        AppointmentHelper.addAppointmentToUser(new Appointment(new Date(), "dr", "adasd"), new AppointmentHelper.AddAppointmentCallback() {
            @Override
            public void onAppointmentAdded(String appointmentId) {
                Toast.makeText(getContext(), "added appointment", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAppointmentError(Exception e) {

            }
        });
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                if (!appointments.isEmpty()){
                    PtAppointmentAdapter itemAdapter = new PtAppointmentAdapter((ArrayList<Appointment>) appointments);
                    RecyclerView recyclerView = view.findViewById(R.id.Rv1);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(itemAdapter);
                }

                return appointments;
            }

            @Override
            public void onAppointmentsError(Exception e) {

            }
        });

    }



    @Override
    public void getAllSuccess(ArrayList<User> users) {

    }

    @Override
    public void getOneSuccess(User user) {
        appointment = user.getAppointments();
    }

    @Override
    public void onItemClick(View view, int position) {
        setupDialog();
    }

    private void setupDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("האם אתה בטוח?")
                .setMessage("אתה בטוח שאתה רוצה לבטל את התור הזה?")
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
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void performAction() {
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                appointments.remove(0);
                //TODO לבטל את ההתראה יום לפני
                Toast.makeText(getContext(), "התור בוטל", Toast.LENGTH_SHORT).show();
                return appointments;
            }

            @Override
            public void onAppointmentsError(Exception e) {
                //Handle error
            }
        });
    }
}



