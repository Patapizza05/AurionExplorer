package fr.clementduployez.aurionexplorer.MesNotes;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;

/**
 * Created by cdupl on 2/14/2016.
 */
public class LoadMarksListAsync extends AsyncTask<String,String,ArrayList<MarksInfo>> {

    private final MarksFragment marksFragment;

    public LoadMarksListAsync(MarksFragment marksFragment) {
        this.marksFragment = marksFragment;
    }

    @Override
    protected ArrayList<MarksInfo> doInBackground(String... params) {
        publishProgress("Récupération des notes...");
        Connection.Response response = AurionBrowser.connectToPage("Mes notes");
        ArrayList<MarksInfo> marksInfos = null;
        if (response != null && response.statusCode() == 200) {
            publishProgress("Traitement des données...");
            try {
                marksInfos = parseMarks(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (marksInfos == null) {
            marksInfos = new ArrayList<>();
        }
        return marksInfos;
    }

    private ArrayList<MarksInfo> parseMarks(Connection.Response response) throws IOException {
        ArrayList<MarksInfo> marksInfos = new ArrayList<>();
        Document document = response.parse();
        Elements tableRows = document.getElementsByTag("tr");
        for (Element tr : tableRows)
        {
            String trId = tr.attr("id");
            if (trId != null && trId.startsWith("form:dataTableFavori") && !trId.equals("form:dataTableFavori:ch"))
            {
                Elements tableColumns = tr.getElementsByTag("td");
                if (tableColumns.size() >= 6) {
                    MarksInfo info = new MarksInfo(tableColumns.get(2).html(),tableColumns.get(1).html(),tableColumns.get(3).html(),tableColumns.get(0).html());
                    marksInfos.add(info);
                }
            }
        }
        return marksInfos;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        this.marksFragment.inform(values[0]);
    }



    @Override
    protected void onPostExecute(ArrayList<MarksInfo> marksInfos) {
        super.onPostExecute(marksInfos);
        this.marksFragment.inform("Récupération des notes terminée.");
        this.marksFragment.onAsyncResult(marksInfos);
    }
}
