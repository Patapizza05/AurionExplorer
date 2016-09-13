package fr.clementduployez.aurionexplorer.MesNotes;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.yarolegovich.wellsql.WellSql;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.SQL.SQLUtils;

/**
 * Created by cdupl on 4/7/2016.
 */
public class GradesService extends IntentService implements ILoadGradesListAsyncReceiver {

    private LoadGradesListAsync loadGradesListAsync;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * name : Used to name the worker thread, important only for debugging.
     */
    public GradesService() {
        super("GradesUpdaterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getAction().equals("Refresh"))
        {
            updateGrades();
        }
    }

    public void updateGrades() {
        if (loadGradesListAsync == null) {
            loadGradesListAsync = new LoadGradesListAsync(this);
            loadGradesListAsync.execute();
        }
    }

    @Override
    public void onAsyncResult(List<GradesInfo> data) {
        /*data.add(new GradesInfo("Evaluation du module M1P1 Network and System Base","Test","20","Test"));
        data.add(new GradesInfo("Evaluation du module M1P1 Développement Android","Test2","18.5","Test2"));
        Log.i("Service", "onAsyncResult");*/
        findNewGrades(data);
        loadGradesListAsync = null;
    }

    @Override
    public void onAsyncProgress(List<GradesInfo> data) {
        //We'll do it all on AsyncResult
    }

    private void findNewGrades(List<GradesInfo> data) {

        /*List<String> ids = new ArrayList<>();
        for (GradesInfo item : data) {
            ids.add(item.getGradeId());
        }*/

        List<GradesInfo> sqlData = WellSql.select(GradesInfo.class).getAsModel();
        List<GradesInfo> newData = new LinkedList<>();
        for (GradesInfo info : data) {
            if (!containsId(sqlData, info.getGradeId()))
            {
                newData.add(info);
            }
        }

        Log.i("New grades", "" + newData.size());

        if (newData.size() > 0) {
            if (newData.size() == 1) {
                GradesUpdaterNotification.sendNotification(
                        newData.get(0),
                        R.drawable.ic_assignment_white_48dp,
                        AurionExplorerApplication.getContext().getResources().getColor(R.color.colorPrimary),
                        this, true);
            }
            else if (newData.size() > 1) {
                GradesUpdaterNotification.sendNotification(newData,
                        R.drawable.ic_assignment_white_48dp,
                        AurionExplorerApplication.getContext().getResources().getColor(R.color.colorPrimary),
                        this, true);
            }

            SQLUtils.add(newData);
        }
        /*else {
            GradesUpdaterNotification.sendNotification(new GradesInfo("test", "test", "test", "test"),
                    R.drawable.ic_assignment_white_48dp,
                    AurionExplorerApplication.getContext().getResources().getColor(R.color.colorPrimary),
                    this, false);
        }*/
    }

    public static boolean containsId(List<GradesInfo> list, String id) {
        for (GradesInfo object : list) {
            if (object.getGradeId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
