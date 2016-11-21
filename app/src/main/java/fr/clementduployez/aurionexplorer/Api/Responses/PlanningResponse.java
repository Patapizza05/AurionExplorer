package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.clementduployez.aurionexplorer.MonPlanning.CalendarInfo;
import fr.clementduployez.aurionexplorer.Utils.JSoupUtils;

/**
 * Created by cdupl on 11/21/2016.
 */

public class PlanningResponse extends AurionResponse {

    private static DateFormat currentDateFormat = new SimpleDateFormat("MM/yyyy");
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    private List<CalendarInfo> calendar;

    public PlanningResponse(Connection.Response response) throws IOException {
        super(response);
        this.calendar = parseCalendar(response);
    }

    private List<CalendarInfo> parseCalendar(Connection.Response response) throws IOException {
        Document document = response.parse();
        int size = document.getElementsByClass("evenement").size();

        List<CalendarInfo> data = new ArrayList<>(size);
        int i = 0;
        while (i < size) {
            Element element = document.getElementById("form:composantsInterventions:" + i + ":case");
            String date = document.getElementById("form:composantsInterventions:" + i + ":detail").child(1).getElementsByTag("td").get(1).html();
            data.add(new CalendarInfo(date, element.ownText()));
            i++;
        }
        return data;
    }

    public List<CalendarInfo> getCalendar() {
        return calendar;
    }

    public static Map<String, String> prepareRequest(IndexResponse indexResponse, String title) throws IOException {
        HashMap<String, String> data = new HashMap<>();
        JSoupUtils.addValueToData(title, indexResponse.getDocument(), data);
        return data;
    }

    public static Map<String, String> prepareRequest(Date beginDate, Date endDate) {
        HashMap<String, String> customData = new HashMap<>();
        customData.put("form:calendarDebutInputDate",dateFormat.format(beginDate));
        customData.put("form:calendarDebutInputCurrentDate",currentDateFormat.format(beginDate));
        customData.put("form:calendarFinInputDate",dateFormat.format(endDate));
        customData.put("form:calendarFinInputCurrentDate", currentDateFormat.format(endDate));
        customData.put("form:btnOk","form:btnOk");
        customData.put("form","form");
        customData.put("form:largeurDivCenter","1600");

        return customData;
    }
}
