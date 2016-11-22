package fr.clementduployez.aurionexplorer.Fragments.Conferences.AsyncTasks;

import android.os.AsyncTask;

import java.util.List;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Fragments.Conferences.ConferencesFragment;
import fr.clementduployez.aurionexplorer.Model.ConferencesInfo;

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
        return new AurionApi().conferences(0).getData();
    }


    @Override
    protected void onPostExecute(List<ConferencesInfo> data) {
        super.onPostExecute(data);
        this.conferencesFragment.onAsyncResult(data);
    }
}
