package com.example.dentex;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    private static final String CHANNEL_ID = "appointment_channel";

    public NotificationWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
        // Get the appointment details from the input data (we'll pass it through the WorkRequest)
        String appointmentTitle = getInputData().getString("title");
        String appointmentMessage = getInputData().getString("message");

        // Show notification
        showNotification(appointmentTitle, appointmentMessage);

        // Return success result
        return Result.success();
    }

    private void showNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        // Create Notification Channel for devices running Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Appointment Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        // Create the notification
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "REMINDER_CHANNEL")
                .setContentTitle("Reminder")
                .setContentText("מזכירים שיש לך תור למרפאה שלנו בעוד 24 שעות!")
                .setSmallIcon(R.drawable.applogo)
                .setAutoCancel(true)
                .build();


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, notification);
    }
}
