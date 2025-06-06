package com.example.dentex.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dentex.model.Appointment;
import com.example.dentex.utils.AppointmentHelper;
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
                    case "calendar":
                        replaceFragment(new pt_CalendarFr());
                        titleText.setText("personal calendar");
                        return true;
                    case "new appointment":
                        titleText.setText("find appointments");
                        replaceFragment(new pt_newAppointment_fr(PatientActivity.this));
                        return true;
                    case "home page":
                        replaceFragment(new pt_home_fr());
                        titleText.setText("home page");
                        return true;
                }
                return false;
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
