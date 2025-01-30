package com.example.dentex.Patient;

import static com.example.dentex.FireBase.FBUserHelper.db;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.dentex.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Date;

public class PatientActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient);
        createChannel();
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
                        replaceFragment(new pt_newAppointment_fr(PatientActivity.this));
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


    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ReminderChannel";
            String description = "Channel for reminder notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("REMINDER_CHANNEL", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);}
    }

    public void replaceFragment(Fragment fragment)
    {
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_section,fragment);
        fragmentTransaction.commit();
    }
}
