package fr.clementduployez.aurionexplorer.MesConferences;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;

/**
 * Created by cdupl on 2/22/2016.
 */
public class LoadConferencesListAsync extends AsyncTask<Void,Void,ArrayList<ConferencesInfo>> {

    private final ConferencesFragment conferencesFragment;

    public LoadConferencesListAsync(ConferencesFragment conferencesFragment) {
        this.conferencesFragment = conferencesFragment;
    }

    @Override
    protected ArrayList<ConferencesInfo> doInBackground(Void... params) {
        Connection.Response response = AurionBrowser.connectToPage("Mes conférences");
        ArrayList<ConferencesInfo> data = null;
        Log.i("Response",""+response);
        if (response != null) {
            try {
                data = parseConferences(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    private ArrayList<ConferencesInfo> parseConferences(Connection.Response response) throws IOException {
        Document document = response.parse();
        ArrayList<ConferencesInfo> data = new ArrayList<>();
        Element e;
        int i = 0;
        while ( (e = document.getElementById("form:dataTableFavori:"+i)) != null) {
            Elements el = e.getElementsByTag("td");
            String title = el.get(0).text();
            String date = el.get(1).text();
            data.add(new ConferencesInfo(title,date));
            i++;
        }
        return data;
}

    @Override
    protected void onPostExecute(ArrayList<ConferencesInfo> data) {
        super.onPostExecute(data);
        this.conferencesFragment.onAsyncResult(data);
    }
}
