package com.example.dentex.controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.dentex.R;

public class NotificationWorker extends Worker {

    private static final String CHANNEL_ID = "appointment_channel";

    public NotificationWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {

        showNotification();

        // Return success result
        return Result.success();
    }

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        // Create Notification Channel for devices running Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Appointment Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        // Create the notification
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "REMINDER_CHANNEL")
                .setContentTitle("reminder")
                .setContentText("this is a reminder that you have an appointment 24 hours from now")
                .setSmallIcon(R.drawable.applogo)
                .setAutoCancel(true)
                .build();


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, notification);
    }
}
