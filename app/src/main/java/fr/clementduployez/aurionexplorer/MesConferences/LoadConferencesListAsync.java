package fr.clementduployez.aurionexplorer.MesConferences;

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
import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;

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
