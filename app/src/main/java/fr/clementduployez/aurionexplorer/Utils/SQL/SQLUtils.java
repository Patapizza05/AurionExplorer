package fr.clementduployez.aurionexplorer.Utils.SQL;

import com.yarolegovich.wellsql.WellSql;

import java.util.List;

import fr.clementduployez.aurionexplorer.MesNotes.GradesInfo;

/**
 * Created by cdupl on 3/12/2016.
 */
public class SQLUtils {

    private static final Class GRADES_CLASS = GradesInfo.class;

    public static void removeAndSave(List<GradesInfo> data)
    {
        WellSql.delete(GRADES_CLASS).execute();
        WellSql.insert(data).asSingleTransaction(true).execute();
    }


/*WellSql.select(GradesInfo.class).getAsModelAsync(new SelectQuery.Callback<List<GradesInfo>>() {
                    @Override
                    public void onDataReady(List<GradesInfo> gradesInfos) {
                        Log.i("GRADES SIZE", "" + gradesInfos.size());
                        for (GradesInfo g : gradesInfos) {
                            Log.i("GRADES", g.getTitle());
                        }
                    }
                });*/
}
