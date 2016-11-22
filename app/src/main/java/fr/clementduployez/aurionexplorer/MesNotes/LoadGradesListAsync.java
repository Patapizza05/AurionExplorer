package fr.clementduployez.aurionexplorer.MesNotes;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.GradesResponse;
import fr.clementduployez.aurionexplorer.Informer;
import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;

/**
 * Created by cdupl on 2/14/2016.
 */
public class LoadGradesListAsync extends AsyncTask<String,List<GradesInfo>,List<GradesInfo>> {

    private final ILoadGradesListAsyncReceiver receiver;

    public LoadGradesListAsync(ILoadGradesListAsyncReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected List<GradesInfo> doInBackground(String... params) {

        AurionApi api = AurionApi.getInstance();
        GradesResponse response = api.grades();

        List<GradesInfo> data = new ArrayList<>();
        data.addAll(response.getData());

        if (response.getNbPages() > 1) {
            GradesResponse lastResponse = response;
            for (int page = response.getPage() + 1; page < response.getNbPages(); page++) {
                lastResponse = api.grades(lastResponse, page);
                if (lastResponse != null) {
                    data.addAll(lastResponse.getData());
                    publishProgress(data);
                }
                else {
                    break;
                }
            }
        }

        return data;
    }

    @Override
    protected void onProgressUpdate(List<GradesInfo>... data) {
        super.onProgressUpdate(data);
        this.receiver.onAsyncProgress(data[0]);
    }

    @Override
    protected void onPostExecute(List<GradesInfo> gradesInfos) {
        super.onPostExecute(gradesInfos);
        Informer.inform("Récupération des notes terminée.");
        this.receiver.onAsyncResult(gradesInfos);
    }
}
