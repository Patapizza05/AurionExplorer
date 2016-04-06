package fr.clementduployez.aurionexplorer.MesNotes;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;
import fr.clementduployez.aurionexplorer.MainActivity;

/**
 * Created by cdupl on 4/6/2016.
 */
public class GradesUpdaterNotification {

    private static String TITLE = "Aurion Explorer - Grades Updater";
    private static final String TICKER = TITLE + " Service";
    private static int ONGOING_NOTIFICATION_ID = 1;
    private static int nbNotifs = ONGOING_NOTIFICATION_ID + 1;
    private static Notification currentNotification;

    private static Notification makeNotification(String text, int drawable, int color, boolean ongoing) {
        Context context = AurionExplorerApplication.getContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);


        /**
         * Expanded Notification Actions
         */
        //NotificationCompat.Action quitAction = makeAction("Close","Close",R.drawable.ic_power_settings_new_black_24dp);
        //NotificationCompat.Action settingsAction = makeAction("Settings","Settings",R.drawable.ic_settings_black_24dp);
        //NotificationCompat.Action refreshAction = makeAction("","Refresh",R.drawable.ic_autorenew_black_24dp);
        /**
         *
         */

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(text)
                .setContentText(TITLE)
                .setTicker(TICKER)
                .setContentIntent(pendingIntent)
                .setSmallIcon(drawable)
                .setOngoing(ongoing)
                .setColor(color)
                    //.addAction(settingsAction)
                        //.addAction(refreshAction)
                    //.addAction(quitAction)
                .setAutoCancel(true)
                .build();

        return notification;
    }

    public static void sendOngoingNotification(String text, int drawable, int color, GradesUpdaterService service) {
        sendNotification(makeNotification(text, drawable, color, true), service, ONGOING_NOTIFICATION_ID);
    }

    public static void sendNotification(String text, int drawable, int color, GradesUpdaterService service) {
        sendNotification(makeNotification(text, drawable, color, false), service, nbNotifs++);
    }

    private static void sendNotification(Notification notification, GradesUpdaterService service, int id) {
        if (id != ONGOING_NOTIFICATION_ID) {
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        Context context = AurionExplorerApplication.getContext();
        notify(context, notification, id);
        service.startForeground(id, notification);
        Intent updateIntent = new Intent("Update");
        service.sendBroadcast(updateIntent);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification, int id) {
        currentNotification = notification;
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(id, notification);
    }

    public static void removeNotifications() {
        Context context = AurionExplorerApplication.getContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationManager.cancel(NOTIFICATION_ID);
        notificationManager.cancelAll(); //Just in case
    }
}
