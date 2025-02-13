package com.example.dentex.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dentex.Appointments.Appointment;
import com.example.dentex.Appointments.AppointmentHelper;
import com.example.dentex.R;
import com.example.dentex.view.Fragments.pt_CalendarFr;
import com.example.dentex.view.Fragments.pt_home_fr;
import com.example.dentex.view.Fragments.pt_newAppointment_fr;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;

public class PatientActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient);
        titleText=findViewById(R.id.Tv1);
        replaceFragment(new pt_home_fr());
        bottomNavigationView = findViewById(R.id.bNav);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "כל התורים":
                        replaceFragment(new pt_CalendarFr());
                        titleText.setText("כל התורים");
                        return true;
                    case "תור חדש":
                        titleText.setText("בחר סוג טיפול ורופא");
                        replaceFragment(new pt_newAppointment_fr(PatientActivity.this));
                        return true;
                    case "דף הבית":
                        replaceFragment(new pt_home_fr());
                        titleText.setText("דף הבית");
                        return true;
                }
                return false;
            }
        });
        Date appointmentDate = new Date(System.currentTimeMillis() + 10000); // Appointment 1 minute from now
        Appointment appointment = new Appointment(appointmentDate, "sahddj", "sadh");
        AppointmentHelper.addAppointmentToUser(appointment, new AppointmentHelper.AddAppointmentCallback() {
            @Override
            public void onAppointmentAdded(String appointmentId) {
                AppointmentHelper.scheduleNotification(getApplicationContext(),appointment);
                Toast.makeText(PatientActivity.this, "TOASTTTT!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAppointmentError(Exception e) {

            }
        });

    }
    public void replaceFragment(Fragment fragment)
    {
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_section,fragment);
        fragmentTransaction.commit();
    }
}
