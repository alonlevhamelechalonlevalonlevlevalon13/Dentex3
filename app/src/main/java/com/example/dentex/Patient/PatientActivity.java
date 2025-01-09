package com.example.dentex.Patient;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dentex.Appointments.Appointment;
import com.example.dentex.Appointments.AppointmentHelper;
import com.example.dentex.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;
import java.util.List;

public class PatientActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient);
        setupAlarms();
        createChannel();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get the FCM token
                    String token = task.getResult();
                    Log.d("FCM", "FCM Token: " + token);

                    // You can send this token to your server to register the device for notifications

                });

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
    private void setupAlarms() {
        AppointmentHelper.addAppointmentToUser(new Appointment(new Date(2024,1,9),"dr","asda"), new AppointmentHelper.AddAppointmentCallback() {
            @Override
            public void onAppointmentAdded(String appointmentId) {
                // Appointment added successfully
                Toast.makeText(PatientActivity.this, "Appointment added with ID: ", Toast.LENGTH_SHORT).show();
                // You can now use the appointmentId to refer to this appointment
            }

            @Override
            public void onAppointmentError(Exception e) {
                // Handle the error
                Toast.makeText(PatientActivity.this, "Error adding appointment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public void onAppointmentsLoaded(List<Appointment> appointments) {
                Appointment nearestAppointment = AppointmentHelper.getNearestAppointment(appointments);
                if (nearestAppointment != null) {
                    AppointmentHelper.setAlarmForAppointment(PatientActivity.this, nearestAppointment, "");
                }
            }

            @Override
            public void onAppointmentsError(Exception e) {
                //Handle error
            }
        });
    }
}
