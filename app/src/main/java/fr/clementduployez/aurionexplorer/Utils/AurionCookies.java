package fr.clementduployez.aurionexplorer.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;

/**
 * Created by cdupl on 2/13/2016.
 */
public class AurionCookies {

    private static final String SHARED_PREF_KEY = "COOKIE";

    private static Map<String,String> cookies = load();

    public static void addAll(Map<String, String> newCookies) {
        cookies.putAll(newCookies);
        save();
    }

    private static void save() {
        SharedPreferences pref = AurionExplorerApplication.getContext().getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();

        for (String s : cookies.keySet()) {
            editor.putString(s, cookies.get(s));
        }

        editor.apply();
    }

    private static Map<String, String> load() {
        HashMap <String, String> cookies = new HashMap<>();
        SharedPreferences pref= AurionExplorerApplication.getContext().getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        Map<String, String> map = (Map<String, String>) pref.getAll();
        cookies.putAll(map);
        return cookies;
    }

    public static Map<String, String> get() {
        return cookies;
    }

    public static void clear() {
        SharedPreferences pref = AurionExplorerApplication.getContext().getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.clear().apply();
    }
}
