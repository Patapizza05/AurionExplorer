package fr.clementduployez.aurionexplorer.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;

/**
 * Created by cdupl on 2/14/2016.
 */
public class UserData {
    private static final String SHARED_PREFERENCES_USER_DATA = "fr.clementduployez.aurionexplorer.data";

    private static final String USER_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String NAME_KEY = "name";
    private static final String REFRESH_GRADES_SERVICE_KEY = "refreshGradesService";

    private static String username;
    private static String password;
    private static String name;

    private static Boolean refreshGradesService;

    private static boolean stayLoggedIn = false;


    public static SharedPreferences loadPreferences() {
        return AurionExplorerApplication.getContext().getSharedPreferences(SHARED_PREFERENCES_USER_DATA, Context.MODE_PRIVATE);
    }

    public static void saveUsername(String value) {
        if (isStayLoggedIn()) {
            save(USER_KEY,value);
        }
        username = value;

    }

    public static void savePassword(String value) {
        if (isStayLoggedIn()) {
            save(PASSWORD_KEY,value); //Password will be accessible to rooted phones...
            //Encryption means we would need to store the encryption key : Can be accessed too on rooted phones.
        }
        password = value;
    }

    public static void saveName(String value) {
        if (isStayLoggedIn()) {
            save(NAME_KEY,value);
        }
        name = value;
    }

    public static String getUsername() {
        if (username == null) {
            return loadUsername();
        }
        return username;
    }

    public static String getPassword() {
        if (password == null) {
            return loadPassword();
        }
        return password;
    }

    public static String getName() {
        if (name == null) {
            return loadName();
        }
        return name;
    }

    private static String loadUsername() {
        username = loadPreferences().getString(USER_KEY, null);
        return username;
    }

    private static String loadPassword() {
        password = loadPreferences().getString(PASSWORD_KEY, null);
        return password;
    }

    private static String loadName() {
        name = loadPreferences().getString(NAME_KEY, null);
        return name;
    }

    public static void save(String key, String value) {
        SharedPreferences prefs = loadPreferences();
        prefs.edit().putString(key, value).apply();
    }

    public static void save(String key, boolean value) {
        SharedPreferences prefs = loadPreferences();
        prefs.edit().putBoolean(key, value).apply();
    }

    public static boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(boolean stayLoggedIn) {
        UserData.stayLoggedIn = stayLoggedIn;
    }

    public static void clear() {
        save(USER_KEY,null);
        save(PASSWORD_KEY,null);
        save(NAME_KEY,null);
        username = null;
        password = null;
        name = null;
    }

    public static boolean isRefreshGradesService() {
        if (refreshGradesService == null) {
            refreshGradesService = loadPreferences().getBoolean(REFRESH_GRADES_SERVICE_KEY, true);
        }
        return refreshGradesService;
    }

    public static void saveRefreshGradesService(boolean val) {
        save(REFRESH_GRADES_SERVICE_KEY, val);
        refreshGradesService = val;
    }
}
