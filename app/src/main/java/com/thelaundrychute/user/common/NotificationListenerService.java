package com.thelaundrychute.user.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.web.WebActivity;
import com.thelaundrychute.user.common.web.WebPages;

/**
 * Created by Steve Baleno on 12/16/2015.
 */
public class NotificationListenerService extends GcmListenerService {

    private static final String TAG = "NotificationListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("Message");
        Gson parser = new GsonBuilder().create();
        NotificationResponse response = parser.fromJson(message, NotificationResponse.class);

        if (from.startsWith("/topics/")) {
            // Could potentially do different things with notifications based on topic
        } else {
            // normal downstream message.
        }

        if (response != null) {
            sendNotification(response.getSubject(), response.getContent());
        }
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     */
    private void sendNotification(String subject, String content) {
        //Open the messages view
        Intent intent = WebActivity.newIntent(this, WebPages.HOME);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int color = getResources().getColor(R.color.tlcLogoRed);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_tlc1024transparent)
                .setColor(color)
                .setContentTitle(subject)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}