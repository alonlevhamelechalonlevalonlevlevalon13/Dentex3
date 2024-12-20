package com.example.dentex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dentex.Patient.PatientActivity;

public class MessageBroadcast extends BroadcastReceiver {

    private void sendNotification(String messageBody) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "REMINDER_CHANNEL")
                .setContentTitle("Reminder")
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.applogo)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(PatientActivity.class);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification("מזכירים שיש לך תור למרפאה שלנו בעוד 24 שעות!");
        //TODO מצגת של זאב לשנות את הDATE לתאריך של התור מינוס 1 ביום
    }
}
