package fr.clementduployez.aurionexplorer.MesNotes;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.Informer;
import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;

/**
 * Created by cdupl on 2/14/2016.
 */
public class LoadGradesListAsync extends AsyncTask<String,ArrayList<GradesInfo>,ArrayList<GradesInfo>> {

    private final GradesFragment gradesFragment;
    private boolean isFirstValues = true;

    public LoadGradesListAsync(GradesFragment gradesFragment) {
        this.gradesFragment = gradesFragment;
    }

    @Override
    protected ArrayList<GradesInfo> doInBackground(String... params) {
        isFirstValues = true;
        Connection.Response response = AurionBrowser.connectToPage("Mes notes");
        ArrayList<GradesInfo> gradesInfos = null;
        if (response != null && response.statusCode() == 200) {
            try {
                gradesInfos = parseMarks(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        while (gradesInfos != null && gradesInfos.size() >= 20) {
            publishProgress(gradesInfos);
            response = AurionBrowser.connectToNextPage(response,null);
            if (response != null && response.statusCode() == 200) {
                try {
                    gradesInfos = parseMarks(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (gradesInfos == null) {
            gradesInfos = new ArrayList<>();
        }

        return gradesInfos;
    }

    private ArrayList<GradesInfo> parseMarks(Connection.Response response) throws IOException {
        ArrayList<GradesInfo> gradesInfos = new ArrayList<>();
        Document document = response.parse();
        Elements tableRows = document.getElementsByTag("tr");
        for (Element tr : tableRows)
        {
            String trId = tr.attr("id");
            if (trId != null && trId.startsWith("form:dataTableFavori") && !trId.equals("form:dataTableFavori:ch"))
            {
                Elements tableColumns = tr.getElementsByTag("td");
                if (tableColumns.size() >= 6) {
                    GradesInfo info = new GradesInfo(tableColumns.get(2).html(),tableColumns.get(1).html(),tableColumns.get(3).html(),tableColumns.get(0).html());
                    gradesInfos.add(info);
                }
            }
        }
        return gradesInfos;
    }

    @Override
    protected void onProgressUpdate(ArrayList<GradesInfo>... data) {
        super.onProgressUpdate(data);
        this.gradesFragment.onAsyncProgress(data,isFirstValues);
        isFirstValues = false;
    }



    @Override
    protected void onPostExecute(ArrayList<GradesInfo> gradesInfos) {
        super.onPostExecute(gradesInfos);
        Informer.inform("Récupération des notes terminée.");
        this.gradesFragment.onAsyncResult(gradesInfos,isFirstValues);
    }
}

        /*Connection.Response testResponse = AurionBrowser.connectToPage("Salles disponibles");
        testResponse = AurionBrowser.connectToNextPage(testResponse,null);
        try {
            Document testDoc = testResponse.parse();
            for (Element e : testDoc.getElementsByClass("preformatted"))
            {
                Log.i("Pref",e.html());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
