package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.clementduployez.aurionexplorer.MesConferences.ConferencesInfo;
import fr.clementduployez.aurionexplorer.Utils.JSoupUtils;

/**
 * Created by cdupl on 11/22/2016.
 */

public class ConferencesResponse extends AurionResponse {
    private final List<ConferencesInfo> data;


    public ConferencesResponse(Connection.Response response) throws IOException {
        super(response);
        this.data = parseConferences(response);
    }

    private List<ConferencesInfo> parseConferences(Connection.Response response) throws IOException {
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

    public List<ConferencesInfo> getData() {
        return data;
    }


    public static Map<String, String> prepareRequest(IndexResponse indexResponse, String title) throws IOException {
        HashMap<String, String> data = new HashMap<>();
        JSoupUtils.addValueToData(title, indexResponse.getDocument(), data);
        return data;
    }

}
