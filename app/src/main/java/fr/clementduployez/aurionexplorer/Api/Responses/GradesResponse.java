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

import fr.clementduployez.aurionexplorer.Model.GradesInfo;
import fr.clementduployez.aurionexplorer.Api.Utils.JSoupUtils;

/**
 * Created by cdupl on 11/22/2016.
 */

public class GradesResponse extends AurionResponse {
    private final int page;
    private List<GradesInfo> data;
    private int nbPages;


    public GradesResponse(Connection.Response response, int page) throws IOException {
        super(response);
        this.page = page;
        this.data = parseGrades(getDocument());
        this.nbPages = parseNbPages(getDocument());
    }

    private List<GradesInfo> parseGrades(Document document) {
        ArrayList<GradesInfo> gradesInfos = new ArrayList<>();
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

    private int parseNbPages(Document document) {
        try {
            Element navigator = document.getElementById("form:bas");
            if (navigator != null) {
                return navigator.getElementsByClass("rf-ds-nmb-btn").size();
            }
        }
        catch(Exception ex) {
            //No other page
        }
        return 1;
    }

    public static Map<String, String> prepareRequest(IndexResponse indexResponse, String title) throws IOException {
        HashMap<String, String> data = new HashMap<>();
        JSoupUtils.addValueToData(title, indexResponse.getDocument(), data);
        return data;
    }

    public static Map<String, String> prepareRequest(Integer page) {
        Map<String, String> data = new HashMap<>();
        page = page+1;
        data.put("javax.faces.source","form:bas");
        data.put("javax.faces.partial.event","rich:datascroller:onscroll");
        data.put("javax.faces.partial.execute","form:bas @component");
        data.put("javax.faces.partial.render","@component");
        data.put("form:bas:page", page.toString()); //Page #page
        data.put("org.richfaces.ajax.component","form:haut");
        data.put("form:haut","form:bas");
        data.put("rfExt","null");
        data.put("AJAX:EVENT_COUNT","1");
        data.put("javax.faces.partial.ajax","true");

        return data;
    }

    public int getPage() {
        return page;
    }

    public int getNbPages() {
        return nbPages;
    }

    public List<GradesInfo> getData() {
        return data;
    }
}
