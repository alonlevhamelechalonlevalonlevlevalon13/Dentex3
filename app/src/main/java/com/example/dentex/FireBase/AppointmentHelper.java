package com.example.dentex.FireBase;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.dentex.Appointments.MessageBroadcast;
import com.example.dentex.Appointments.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentHelper {

    public interface AppointmentsCallback {
        void onAppointmentsLoaded(List<Appointment> appointments);
        void onAppointmentsError(Exception e);
    }

    public static void getUserAppointments(AppointmentsCallback callback) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("appointments")
                    .orderBy("date", Query.Direction.ASCENDING) // Order by date
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<Appointment> appointments = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Appointment appointment = document.toObject(Appointment.class);
                            appointments.add(appointment);
                        }
                        callback.onAppointmentsLoaded(appointments);
                    })
                    .addOnFailureListener(e -> {
                        // Handle errors
                        System.out.println("Error getting appointments: " + e.getMessage());
                        callback.onAppointmentsError(e);
                    });
        } else {
            // No user is signed in
            callback.onAppointmentsLoaded(new ArrayList<>());
        }
    }
    public static Appointment getNearestAppointment(List<Appointment> appointments) {
        if (appointments.isEmpty()) return null;

        Date now = new Date();
        for (Appointment appointment : appointments) {
            if (appointment.getDate().after(now)) {
                return appointment;
            }
        }
        return null;
    }
    public static void setAlarmForAppointment(Context context, Appointment appointment) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MessageBroadcast.class); // Create an AlarmReceiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Calculate the trigger time (one day before the appointment)
        Calendar triggerTime = Calendar.getInstance();
        triggerTime.setTime(new Date());
        triggerTime.add(Calendar.MINUTE,+1);
        //triggerTime.setTime(appointment.getDate());
        //triggerTime.add(Calendar.DAY_OF_YEAR, -1); // Subtract one day

        // Set the alarm
        Calendar now = Calendar.getInstance();
        int diff = (int) ((triggerTime.getTimeInMillis() - now.getTimeInMillis()) / 1000);
        Toast.makeText(context, "diff:"+diff+" time:"+triggerTime.getTimeInMillis(), Toast.LENGTH_SHORT).show();
        //TODO: fix this it doesn't call the MessageBroadcast
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime.getTimeInMillis(), pendingIntent);
    }
    public interface AddAppointmentCallback {
        void onAppointmentAdded(String appointmentId);
        void onAppointmentError(Exception e);
    }

    public static void addAppointmentToUser(Appointment appointment, AddAppointmentCallback callback) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Add a new document with a generated ID
            db.collection("users").document(userId).collection("appointments")
                    .add(appointment)
                    .addOnSuccessListener(documentReference -> {
                        // Appointment added successfully
                        String appointmentId = documentReference.getId();
                        callback.onAppointmentAdded(appointmentId);
                    })
                    .addOnFailureListener(e -> {
                        // Handle errors
                        System.err.println("Error adding appointment: " + e.getMessage());
                        callback.onAppointmentError(e);
                    });
        } else {
            // No user is signed in
            callback.onAppointmentError(new Exception("No user is signed in."));
        }
    }
}