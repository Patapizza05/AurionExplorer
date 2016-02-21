package fr.clementduployez.aurionexplorer.MonPlanning;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Informer;
import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;
import fr.clementduployez.aurionexplorer.Utils.AurionCookies;
import fr.clementduployez.aurionexplorer.Utils.JSoupUtils;

/**
 * Created by cdupl on 2/17/2016.
 */
public class LoadCalendarListAsync  extends AsyncTask<String,String,ArrayList<CalendarInfo>> {

    private final CalendarFragment calendarFragment;
    private Date beginDate;
    private Date endDate;

    private static DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

    private static DateFormat currentDateFormat = new SimpleDateFormat("MM/yyyy");
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public LoadCalendarListAsync(CalendarFragment calendarFragment, String beginDate, String endDate) {
        this.calendarFragment = calendarFragment;
        try {
            this.beginDate = f.parse(beginDate);
            this.endDate = f.parse(endDate);
            if (this.beginDate.compareTo(this.endDate)>0)
            { //beginDate is after endDate
                Date swap = this.endDate;
                this.endDate = this.beginDate;
                this.beginDate = swap;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Informer.inform("Les dates fournies sont non valides");
        }
    }

    @Override
    protected ArrayList<CalendarInfo> doInBackground(String... params) {
        /*
        form:calendarDebutInputDate:22/02/16
        form:calendarDebutInputCurrentDate:02/2016
        form:calendarFinInputDate:29/02/16
        form:calendarFinInputCurrentDate:02/2016
         */


        ArrayList<CalendarInfo> calendarData = null;
        Connection.Response response = null;
        try {
            response = connectToPlanningWithDates(AurionBrowser.connectToPage("Mon planning"));

            if (response != null && response.statusCode() == 200) {
                Log.i("Response","received");
                calendarData = parseCalendar(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (calendarData == null) {
            calendarData = new ArrayList<>();
        }

        return calendarData;
    }

    private Connection.Response connectToPlanningWithDates(Connection.Response response) {

        Map<String,String> data = JSoupUtils.getHiddenInputData(response);

        HashMap<String, String> customData = new HashMap<>();
        customData.put("form:calendarDebutInputDate",dateFormat.format(this.beginDate));
        customData.put("form:calendarDebutInputCurrentDate",currentDateFormat.format(this.beginDate));
        customData.put("form:calendarFinInputDate",dateFormat.format(this.endDate));
        customData.put("form:calendarFinInputCurrentDate", currentDateFormat.format(this.endDate));
        customData.put("form:btnOk","form:btnOk");
        customData.put("form","form");
        customData.put("form:largeurDivCenter","1600");
        data.putAll(customData);

        try {
            Connection.Response result = Jsoup.connect("https://aurion-lille.isen.fr/faces/Planning.xhtml") //
                    .header("Content-Type", AurionBrowser.CONTENT_TYPE)
                    .userAgent(AurionBrowser.USER_AGENT)
                    .referrer(AurionBrowser.AURION_URL)
                    .cookies(AurionCookies.cookies)
                    .data(data)
                    .method(Connection.Method.POST)
                    .execute();

            AurionCookies.cookies.putAll(result.cookies());

            return result;

        } catch (IOException e) {
            e.printStackTrace();
            Informer.inform("Erreur pendant la connexion à la page \"Mon Planning (dates)\"");
        }
        return null;
    }

    private ArrayList<CalendarInfo> parseCalendar(Connection.Response response) throws IOException {
        Log.i("URL",response.url().toString());
        Document document = response.parse();
        int size = document.getElementsByClass("evenement").size();

        ArrayList<CalendarInfo> data = new ArrayList<>(size);
        int i = 0;
        while (i < size) {
            Element element = document.getElementById("form:composantsInterventions:" + i + ":case");
            String date = document.getElementById("form:composantsInterventions:" + i + ":detail").child(1).getElementsByTag("td").get(1).html();
            data.add(new CalendarInfo(date, element.html()));
            i++;
        }

        return data;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Informer.inform(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<CalendarInfo> calendarData) {
        super.onPostExecute(calendarData);
        Informer.inform("Récupération du calendrier terminée.");
        this.calendarFragment.onAsyncResult(calendarData);
    }
}
