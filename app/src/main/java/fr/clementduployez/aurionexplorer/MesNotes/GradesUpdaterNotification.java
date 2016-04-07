package fr.clementduployez.aurionexplorer.MesNotes;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;

import java.util.List;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;
import fr.clementduployez.aurionexplorer.MainActivity;

/**
 * Created by cdupl on 4/6/2016.
 */
public class GradesUpdaterNotification {

    private static String TITLE = "Aurion Explorer - Grades Updater";
    private static final String TICKER = TITLE + " Service";
    private static int NOTIFICATION_ID = 1;
    private static final String GROUP_KEY_GRADES = "group_key_grades";

    private static Notification makeNotification(GradesInfo grade, int drawable, int color) {
        String title = getTitle(grade);
        String text = getText(grade);
        return makeNotification(title, text, drawable, color);
    }

    private static Notification makeSummaryNotification(List<GradesInfo> grades, int drawable, int color) {
        String[] titles = new String[grades.size()];
        String[] texts = new String[grades.size()];
        int i = 0;
        for (GradesInfo grade : grades) {
            titles[i] = getSummaryTitle(grade);
            texts[i] = getSummaryText(grade);
            i++;
        }
        return makeSummaryNotification(titles, texts, drawable, color);
    }

    private static String getTitle(GradesInfo grade) {
        return "Nouvelle note : "+grade.getTitle();
    }

    private static String getText(GradesInfo grade) {
        return grade.getValue()+"/20";
    }

    private static String getSummaryTitle(GradesInfo grade) {
        return grade.getTitle();
    }

    private static String getSummaryText(GradesInfo grade) {
        return grade.getValue();
    }

    private static Notification makeNotification(String title, String text, int drawable, int color) {
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
                .setContentTitle(title)
                .setContentText(text)
                .setTicker(TICKER)
                .setContentIntent(pendingIntent)
                .setSmallIcon(drawable)
                .setColor(color)
                    //.addAction(settingsAction)
                        //.addAction(refreshAction)
                    //.addAction(quitAction)
                .setAutoCancel(true)
                .setGroup(GROUP_KEY_GRADES)
                .build();

        return notification;
    }

    private static Notification makeSummaryNotification(String[] titles, String[] texts, int drawable, int color) {
        Context context = AurionExplorerApplication.getContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        String contentText = "";

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        for (int i = 0; i < titles.length; i++) {
            SpannableString str = new SpannableString(texts[i] + " : " + titles[i]);
            str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, texts[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.addLine(str);
            contentText += str + "\n";
        }
        style.setBigContentTitle(titles.length+" nouvelles notes");
        style.setSummaryText("Aurion Explorer");

        int i = 0;
        // Create an InboxStyle notification
        Notification summaryNotification = new NotificationCompat.Builder(context)
                .setContentTitle(titles.length+" nouvelles notes")
                .setSmallIcon(drawable)
                //.setLargeIcon(largeIcon)
                .setStyle(style)
                .setContentText(contentText)
                .setColor(color)
                .setGroup(GROUP_KEY_GRADES)
                .setGroupSummary(true)
                .build();

        return summaryNotification;
    }

    public static void sendNotification(String title, String text, int drawable, int color, Service service) {
        sendNotification(makeNotification(title, text, drawable, color), service);
    }

    public static void sendNotification(GradesInfo grade, int drawable, int color, Service service) {
        sendNotification(makeNotification(grade, drawable, color), service);
    }

    public static void sendNotification(List<GradesInfo> grades, int drawable, int color, Service service) {
        sendNotification(makeSummaryNotification(grades, drawable, color), service);
    }

    private static void sendNotification(Notification notification, Service service) {
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        Context context = AurionExplorerApplication.getContext();
        notify(context, notification, NOTIFICATION_ID);
        service.startForeground(NOTIFICATION_ID, notification);
        Intent updateIntent = new Intent("Update");
        service.sendBroadcast(updateIntent);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification, int id) {
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
