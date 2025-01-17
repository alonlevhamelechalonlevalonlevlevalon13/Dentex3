package com.example.dentex.Appointments;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dentex.FireBase.FBAuthHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentHelper {

    public interface AppointmentsCallback {
        List<Appointment> onAppointmentsLoaded(List<Appointment> appointments);
        void onAppointmentsError(Exception e);
    }

    public static void getUserAppointments(AppointmentsCallback callback) {
        FirebaseUser currentUser = FBAuthHelper.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId).collection("appointments")
                    .orderBy("date", Query.Direction.ASCENDING) // Order by date
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<Appointment> appointments = new ArrayList<>();
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                Appointment appointment = document.toObject(Appointment.class);
                                appointments.add(appointment);
                            }
                            callback.onAppointmentsLoaded(appointments);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle errors
                            System.out.println("Error getting appointments: " + e.getMessage());
                            callback.onAppointmentsError(e);
                        }
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
    public static void setAlarmForAppointment(Context context,Appointment appointment,String string){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    setAlarmForAppointment(context,appointment);
                } else {
                    requestPermissions((Activity) context, new String[]{android.Manifest.permission.POST_NOTIFICATIONS},100);
                }
            } else {
                // For older versions, assume permission is granted
                setAlarmForAppointment(context,appointment);
            }
    }
    public static void setAlarmForAppointment(Context context, Appointment appointment) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MessageBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        Calendar triggerTime = Calendar.getInstance();
        triggerTime.setTime(appointment.getDate());
        triggerTime.add(Calendar.DAY_OF_YEAR, -1);
        Log.d("Alarm", "Trigger time: " + triggerTime.getTimeInMillis());
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime.getTimeInMillis(), pendingIntent);
        appointment.setAlarmManager(alarmManager);
    }
    public static void stopAlarm(Context context, Appointment appointment){
        Intent myIntent = new Intent(context, MessageBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        if (appointment.getAlarmManager()!= null)
            appointment.getAlarmManager().cancel(pendingIntent);
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