package fr.clementduployez.aurionexplorer.Utils;

import android.app.AlarmManager;

/**
 * Created by cdupl on 2/21/2016.
 */
public class Settings {
    public static int CONNECTION_TIMEOUT = 100000; //ms
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36";


    public static boolean LITE = true;

    public static final long GRADES_UPDATE_INTERVAL = AlarmManager.INTERVAL_HOUR;
}
