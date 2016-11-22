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
import java.util.List;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.IndexResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.PlanningResponse;
import fr.clementduployez.aurionexplorer.Informer;
import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;
import fr.clementduployez.aurionexplorer.Utils.AurionCookies;
import fr.clementduployez.aurionexplorer.Utils.JSoupUtils;

/**
 * Created by cdupl on 2/17/2016.
 */
public class LoadCalendarListAsync  extends AsyncTask<String,String,List<CalendarInfo>> {

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
    protected List<CalendarInfo> doInBackground(String... params) {
        PlanningResponse planningResponse = AurionApi.getInstance().planning(beginDate, endDate);
        if (planningResponse == null) return new ArrayList<>(0);

        return planningResponse.getCalendar();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Informer.inform(values[0]);
    }

    @Override
    protected void onPostExecute(List<CalendarInfo> calendarData) {
        super.onPostExecute(calendarData);
        Informer.inform("Récupération du calendrier terminée.");
        this.calendarFragment.onAsyncResult(calendarData);
    }
}
