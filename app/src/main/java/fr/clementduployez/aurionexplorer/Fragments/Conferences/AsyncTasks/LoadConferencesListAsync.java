package fr.clementduployez.aurionexplorer.Fragments.Conferences.AsyncTasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.ConferencesResponse;
import fr.clementduployez.aurionexplorer.Fragments.Conferences.ConferencesFragment;
import fr.clementduployez.aurionexplorer.Model.ConferencesInfo;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;

/**
 * Created by cdupl on 2/22/2016.
 */
public class LoadConferencesListAsync extends AsyncTask<Void,Void,List<ConferencesInfo>> {

    private final ConferencesFragment conferencesFragment;

    public LoadConferencesListAsync(ConferencesFragment conferencesFragment) {
        this.conferencesFragment = conferencesFragment;
    }

    @Override
    protected List<ConferencesInfo> doInBackground(Void... params) {
        ConferencesResponse response = new AurionApi().conferences(0);
        return response != null ? response.getData() : new ArrayList<ConferencesInfo>();
    }


    @Override
    protected void onPostExecute(List<ConferencesInfo> data) {
        super.onPostExecute(data);
        if (data != null && !data.isEmpty()) {
            Informer.getInstance().inform(AurionApi.Messages.CONFERENCES_SUCCESS);
        }
        else {
            Informer.getInstance().inform(AurionApi.Messages.CONNECTION_FAIL);
        }
        this.conferencesFragment.onAsyncResult(data);
    }
}
