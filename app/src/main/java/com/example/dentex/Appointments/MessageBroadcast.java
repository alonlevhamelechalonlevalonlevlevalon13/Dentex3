package com.example.dentex.Appointments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.dentex.Patient.PatientActivity;
import com.example.dentex.R;

public class MessageBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,PatientActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent1, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(context, "REMINDER_CHANNEL")
                .setContentTitle("Reminder")
                .setContentText("מזכירים שיש לך תור למרפאה שלנו בעוד 24 שעות!")
                .setSmallIcon(R.drawable.applogo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.applogo,"title",pendingIntent)
                .build();


        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }
}
