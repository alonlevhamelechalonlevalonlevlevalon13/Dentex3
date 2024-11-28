package com.example.dentex.Patient;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentex.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Patient extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient);
        tv=findViewById(R.id.Tv1);
        replaceFragment(new pt_home_fr());
        bottomNavigationView = findViewById(R.id.bNav);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "כל התורים":
                        replaceFragment(new pt_CalendarFr());
                        tv.setText("כל התורים");
                        return true;
                    case "תור חדש":
                        tv.setText("בחר סוג טיפול ורופא");
                        replaceFragment(new pt_newAppointment_fr(Patient.this));
                        return true;
                    case "דף הבית":
                        replaceFragment(new pt_home_fr());
                        tv.setText("דף הבית");
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