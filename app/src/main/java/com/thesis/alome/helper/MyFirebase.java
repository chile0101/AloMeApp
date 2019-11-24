package com.thesis.alome.helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.thesis.alome.R;
import com.thesis.alome.activity.JobDetailsActivity;
import com.thesis.alome.activity.MainActivity;

import java.util.Map;
import java.util.Random;

public class MyFirebase extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotifications(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getClickAction());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    private void showNotifications(String title, String body, String click_action) {
        Intent notificationIntent = new Intent(click_action);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        String temp="test";
//        Intent intent = new Intent(click_action);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_ID="com.thesis";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_ID,"notifications",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("test");
            Log.d("notification","hihi");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,NOTIFICATION_ID);
        Log.d("notification","test");
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentInfo("info");
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setContentIntent(intent);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());



    }
}
