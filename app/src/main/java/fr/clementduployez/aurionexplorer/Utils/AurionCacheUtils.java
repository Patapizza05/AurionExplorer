package fr.clementduployez.aurionexplorer.Utils;

import android.util.LruCache;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.MesNotes.GradesInfo;

/**
 * Created by cdupl on 3/12/2016.
 */
public class AurionCacheUtils {


    private static LruCache<String, ArrayList<GradesInfo>> gradesCache = new LruCache<>(4*1024*1024);

    public static void saveGrades(ArrayList<GradesInfo> data) {
        gradesCache.put("grades",data);
    }

    public static ArrayList<GradesInfo> loadGrades() {
        return gradesCache.get("grades");
    }
}
