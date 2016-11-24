package fr.clementduployez.aurionexplorer.Fragments.Grades.AsyncTasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.GradesResponse;
import fr.clementduployez.aurionexplorer.Fragments.Grades.Receivers.ILoadGradesListAsyncReceiver;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;
import fr.clementduployez.aurionexplorer.Model.GradesInfo;

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
        Informer.getInstance().inform(AurionApi.Messages.GRADES_SUCCESS);
        this.receiver.onAsyncResult(gradesInfos);
    }
}
