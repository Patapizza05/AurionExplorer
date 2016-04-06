package fr.clementduployez.aurionexplorer.MesNotes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.wellsql.generated.GradesInfoTable;
import com.yarolegovich.wellsql.WellSql;

import org.jsoup.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;
import fr.clementduployez.aurionexplorer.Utils.SQL.SQLUtils;

/**
 * Created by cdupl on 4/6/2016.
 */
public class GradesUpdaterService extends Service implements ILoadGradesListAsyncReceiver {

    private int test = 3;
    private GradesAlarm alarm = new GradesAlarm(this);
    private LoadGradesListAsync loadGradesListAsync;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null && intent.getAction().equals("Close")) {
            closeService(intent);
        }
        else if (intent != null && intent.getAction() != null && intent.getAction().equals("Settings"))
        {
            //openSettings();
        }
        else if (intent != null && intent.getAction() != null && intent.getAction().equals("Refresh"))
        {
            updateGrades();
        }
        else {
            setInterval();
            return Service.START_STICKY;
        }

        return Service.START_NOT_STICKY;

    }

    private void setInterval() {
        alarm.setAlarm(AurionExplorerApplication.getContext());
        GradesUpdaterNotification.sendOngoingNotification(
                "Test",
                R.drawable.ic_school_red_500_18dp,
                AurionExplorerApplication.getContext().getResources().getColor(R.color.colorPrimary),
                this);
        updateGrades();
    }

    public void updateGrades() {
        if (loadGradesListAsync == null) {
            loadGradesListAsync = new LoadGradesListAsync(this);
            loadGradesListAsync.execute();
        }
    }

    private void closeService(Intent intent) {
        GradesUpdaterNotification.removeNotifications();
        stopSelf();
        stopService(intent);
    }

    public void retrieveGrades() {
        Connection.Response response = AurionBrowser.connectToPage("Mes notes");
        ArrayList<GradesInfo> gradesInfos = null;
        if (response != null && response.statusCode() == 200) {
            try {
                gradesInfos = LoadGradesListAsync.parseMarks(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        while (gradesInfos != null && gradesInfos.size() >= 20) {
            response = AurionBrowser.connectToNextPage(response,null);
            if (response != null && response.statusCode() == 200) {
                try {
                    gradesInfos.addAll(LoadGradesListAsync.parseMarks(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        saveGrades(gradesInfos);
    }

    private void saveGrades(List<GradesInfo> data) {

        data.add(new GradesInfo("Test","Test","20","Test"));

        int size = WellSql.select(GradesInfo.class).getAsModel().size();
        if (data.size() > size) {
            findNewGrades(data);
        }
    }

    private void findNewGrades(List<GradesInfo> data) {

        List<String> ids = new ArrayList<>();
        for (GradesInfo item : data) {
            ids.add(item.getGradeId());
            Log.i("item",item.getGradeId());
        }

        List<GradesInfo> sqlData = WellSql.select(GradesInfo.class).getAsModel();
        List<GradesInfo> newData = new LinkedList<>();
        for (GradesInfo info : data) {
            if (!containsId(sqlData, info.getGradeId()))
            {
                newData.add(info);
            }
        }

        Log.i("New grades", "" + newData.size());

        for (GradesInfo item : newData) {
            GradesUpdaterNotification.sendNotification(
                    item.getTitle()+": "+item.getValue(),
                    R.drawable.ic_school_red_500_18dp,
                    AurionExplorerApplication.getContext().getResources().getColor(R.color.colorPrimary),
                    this);
        }
    }


    @Override
    public void onAsyncResult(List<GradesInfo> data) {
        data.add(new GradesInfo("Test","Test","20","Test"));
        Log.i("Service", "onAsyncResult");
        findNewGrades(data);
        loadGradesListAsync = null;
    }

    @Override
    public void onAsyncProgress(List<GradesInfo> data) {
        //We'll do it all on AsyncResult
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
