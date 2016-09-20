package fr.clementduployez.aurionexplorer.MesNotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by cdupl on 4/7/2016.
 */
public class GradesAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "Refresh";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, GradesService.class);
        //i.putExtra("foo", "bar");
        i.setAction(ACTION);
        context.startService(i);
    }

    public static void startAlarm(Context context) {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(context, GradesAlarmReceiver.class);


        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, GradesAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_HOUR, pIntent);
    }

    public static void stopAlarm(Context context) {
        Intent intent = new Intent(context, GradesAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, GradesAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
}
