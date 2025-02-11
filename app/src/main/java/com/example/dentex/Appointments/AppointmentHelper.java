package com.example.dentex.Appointments;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.metrics.Event;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.dentex.FireBase.FBAuthHelper;
import com.example.dentex.NotificationWorker;
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
import java.util.concurrent.TimeUnit;

public class AppointmentHelper {

    public interface AppointmentsCallback {
        List<Appointment> onAppointmentsLoaded(List<Appointment> appointments);
        void onAppointmentsError(Exception e);
    }

    public interface RemoveAppointmentsCallback {
        void onAppointmentsRemoved();
        void onAppointmentsError(Exception e);
    }

    public interface AddAppointmentCallback {
        void onAppointmentAdded(String appointmentId);
        void onAppointmentError(Exception e);
    }

    public static void getUserAppointments(AppointmentsCallback callback) { //פעולה להחזיר את רשימת התורים של המשתמש המחובר על מנת לעבוד איתה
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

    public static void scheduleNotification(Context context, Appointment appointment) {
        // Get the time when the notification should be triggered (Appointment Date)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(appointment.getDate());

        // Calculate the delay in milliseconds from now until the appointment time
        long delay = calendar.getTimeInMillis() - System.currentTimeMillis();

        // If the appointment time is in the past, we do nothing
        if (delay <= 0) {
            return;
        }

        // Create input data for the Worker (Appointment data)
        Data inputData = new Data.Builder()
                .putString("title", "you have a "+appointment.getTreatmentType()+" with "+appointment.getDrname())
                .putString("message", "Reminder")
                .build();

        // Create the WorkRequest
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS) // Delay the execution until the appointment time
                .setInputData(inputData)
                .build();

        // Enqueue the work request with WorkManager
        WorkManager.getInstance(context).enqueue(workRequest);
    }

    public static void stopAlarm(Context context, Appointment appointment){//ביטול ההתראה - חלק מביטול התור
        Intent myIntent = new Intent(context, MessageBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        if (appointment.getAlarmManager()!= null)
            appointment.getAlarmManager().cancel(pendingIntent);

    }

    public static void addAppointmentToUser( Appointment appointment, AddAppointmentCallback callback) { // הוספת תור למערך של המשתמש המחובר
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
    public static void removeAppointment(String id, RemoveAppointmentsCallback callback) { //הוצאת התור מהדאטאבייס
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Add a new document with a generated ID
            db.collection("users").document(userId)
                    .collection("appointments")
                    .document(id)
                    .delete()
                    .addOnSuccessListener(documentReference -> {
                        callback.onAppointmentsRemoved();
                    })
                    .addOnFailureListener(e -> {
                        // Handle errors
                        System.err.println("Error adding appointment: " + e.getMessage());
                        callback.onAppointmentsError(e);
                    });
        } else {
            // No user is signed in
            callback.onAppointmentsError(new Exception("No user is signed in."));
        }
    }

}