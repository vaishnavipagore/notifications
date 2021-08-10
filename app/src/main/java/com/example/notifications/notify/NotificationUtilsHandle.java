package com.example.notifications.notify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.notifications.MainActivity;
import com.example.notifications.R;

import java.util.Date;

public class NotificationUtilsHandle {
    public static final String PUSH_NOTIFICATION = "com.example.notifications.pushNotification";
    private static final String NOTIFICATION_CHANNEL_ID = "100001";
    private static final String NOTIFICATION_CHANNEL_NAME = "Notification";

    private Context mContext;

    public NotificationUtilsHandle(Context mContext) {
        this.mContext = mContext;
    }

    public void displayNotification(String title, String message) {
        int notification_id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Intent resultIntent = new Intent(mContext, MainActivity.class);
        resultIntent.putExtra("notification_id", String.valueOf(notification_id));
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext, 0  /*Request code */, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);

        Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.firebase_lockup_400);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setStyle(inboxStyle)
                .setSmallIcon(R.drawable.firebase_lockup_400)
                .setLargeIcon(largeIcon)
                .setColor(mContext.getResources().getColor(R.color.black))
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = null;
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(notification_id, mBuilder.build());
    }
}
