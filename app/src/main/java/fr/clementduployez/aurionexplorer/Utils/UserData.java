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

    public static SharedPreferences loadPreferences() {
        return AurionExplorerApplication.getContext().getSharedPreferences(SHARED_PREFERENCES_USER_DATA, Context.MODE_PRIVATE);
    }

    public void saveUsername(String value) {
        save("username",value);
    }

    public void savePassword(String password) {
        save("password",password); //Password will be accessible to rooted phones...
        //Encryption means we would need to store the encryption key : Can be accessed too on rooted phones.
    }

    public static String loadUsername() {
        return loadPreferences().getString(USER_KEY, null);
    }

    public static String loadPassword() {
        return loadPreferences().getString(PASSWORD_KEY, null);
    }

    public void save(String key, String value) {
        SharedPreferences prefs = loadPreferences();
        prefs.edit().putString(key, value).apply();
    }

}
