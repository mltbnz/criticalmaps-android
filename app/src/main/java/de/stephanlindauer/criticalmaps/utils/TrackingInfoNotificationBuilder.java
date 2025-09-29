package de.stephanlindauer.criticalmaps.utils;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import de.stephanlindauer.criticalmaps.Main;
import de.stephanlindauer.criticalmaps.R;

public class TrackingInfoNotificationBuilder {
    public static final int NOTIFICATION_ID = 12456;
    private static final int INTENT_CLOSE_ID = 176456;
    private static final int INTENT_OPEN_ID = 133256;
    private static final String NOTIFICATION_CHANNEL_ID = "cm_notification_channel_id";

    public static Notification getNotification(Application application) {
        int pendingIntentFlags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntentFlags += PendingIntent.FLAG_IMMUTABLE;
        }

        Intent openIntent = new Intent(application, Main.class);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openIntent.putExtra("shouldClose", false);
        PendingIntent openPendingIntent = PendingIntent.getActivity(application, INTENT_OPEN_ID,
                openIntent, pendingIntentFlags);

        Intent closeIntent = new Intent(application, Main.class);
        closeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        closeIntent.putExtra("shouldClose", true);
        PendingIntent closePendingIntent = PendingIntent.getActivity(application, INTENT_CLOSE_ID,
                closeIntent, pendingIntentFlags);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) application.getSystemService(Application.NOTIFICATION_SERVICE);

            NotificationChannel notificationChannel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                            application.getString(R.string.notification_channel_name),
                            NotificationManager.IMPORTANCE_LOW); // no sound, status bar visibility

            notificationChannel.setDescription(
                    application.getString(R.string.notification_channel_description_max300chars));
            notificationChannel.setBypassDnd(true);
            notificationChannel.setShowBadge(false);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(application, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_stat_logo)
                        .setContentTitle(application.getString(R.string.notification_tracking_title))
                        .setContentText(application.getString(R.string.notification_tracking_text))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(application.getString(R.string.notification_tracking_text)))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setCategory(NotificationCompat.CATEGORY_SERVICE)
                        .setContentIntent(openPendingIntent)
                        .addAction(R.drawable.ic_notification_open,
                                application.getString(R.string.notification_tracking_open),
                                openPendingIntent)
                        .addAction(R.drawable.ic_notification_close,
                                application.getString(R.string.notification_tracking_close),
                                closePendingIntent)
                        .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
                        .setOngoing(true);

        return builder.build();
    }
}
