package fr.clementduployez.aurionexplorer.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;

/**
 * Created by cdupl on 2/14/2016.
 */
public class UserData {
    private static final String SHARED_PREFERENCES_USER_DATA = "fr.clementduployez.aurionexplorer.data";

    private static final String USER_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    private static String username;
    private static String password;

    private static boolean stayLoggedIn = false;

    public static SharedPreferences loadPreferences() {
        return AurionExplorerApplication.getContext().getSharedPreferences(SHARED_PREFERENCES_USER_DATA, Context.MODE_PRIVATE);
    }

    public static void saveUsername(String value) {
        if (isStayLoggedIn()) {
            save("username",value);
        }
        username = value;

    }

    public static void savePassword(String value) {
        if (isStayLoggedIn()) {
            save("password",value); //Password will be accessible to rooted phones...
            //Encryption means we would need to store the encryption key : Can be accessed too on rooted phones.
        }
        password = value;
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

    private static String loadUsername() {
        username = loadPreferences().getString(USER_KEY, null);
        return username;
    }

    private static String loadPassword() {
        password = loadPreferences().getString(PASSWORD_KEY, null);
        return password;
    }

    public static void save(String key, String value) {
        SharedPreferences prefs = loadPreferences();
        prefs.edit().putString(key, value).apply();
    }

    public static boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(boolean stayLoggedIn) {
        UserData.stayLoggedIn = stayLoggedIn;
    }
}
