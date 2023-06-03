package com.example.project;



import android.app.Notification;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;




public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMService";


    @Override
    public void onMessageReceived(RemoteMessage msg) {
        super.onMessageReceived(msg);

        Log.i("### msg : ", msg.toString());
        PushUtils.acquireWakeLock(this);
        if (msg.getData().size() > 0) {
            Log.i("### data : ", msg.getData().toString());
            sendTopNotification(msg.getData().get("title"), msg.getData().get("content"));


            if (true) {
                scheduleJob();
            } else {
                handleNow();
            }
        }

        if (msg.getNotification() != null) {
            Log.i("### notification : ", msg.getNotification().getBody());
            sendTopNotification(msg.getNotification().getTitle(), msg.getNotification().getBody());
        }
    }


    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();

    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }


    private void sendTopNotification(String title, String content) {
        final String CHANNEL_DEFAULT_IMPORTANCE = "channel_id";
        final int ONGOING_NOTIFICATION_ID = 1;
        PushUtils.releaseWakeLock();
        Intent notificationIntent = new Intent(this, CameraActivity.class);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE );
        Notification notification =
                new Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setSmallIcon(R.drawable.common_full_open_on_phone)
                        .setContentIntent(pendingIntent)
                        .setSound(defaultSoundUri) // 사운드
                        .setVibrate(new long[]{1, 1000}) // 진동
                        .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(ONGOING_NOTIFICATION_ID, notification);
    }


}