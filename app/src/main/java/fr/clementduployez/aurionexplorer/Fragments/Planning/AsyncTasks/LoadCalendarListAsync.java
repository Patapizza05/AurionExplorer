package fr.clementduployez.aurionexplorer.Fragments.Planning.AsyncTasks;

import android.os.AsyncTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.PlanningResponse;
import fr.clementduployez.aurionexplorer.Fragments.Planning.CalendarFragment;
import fr.clementduployez.aurionexplorer.Utils.Callback;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;
import fr.clementduployez.aurionexplorer.Model.CalendarInfo;

/**
 * Created by cdupl on 2/17/2016.
 */
public class LoadCalendarListAsync  extends AsyncTask<Date,String,List<CalendarInfo>> {

    private final Callback<List<CalendarInfo>> callback;

    public LoadCalendarListAsync(Callback<List<CalendarInfo>> callback) {

        this.callback = callback;


    }

    @Override
    protected List<CalendarInfo> doInBackground(Date... params) {
        Date begin = params[0];
        Date end = params[1];

        if (begin.compareTo(end) > 0)
        { //beginDate is after endDate
            Date swap = end;
            end = begin;
            begin = swap;
        }

        PlanningResponse planningResponse = AurionApi.getInstance().planning(begin, end);
        if (planningResponse == null) return new ArrayList<>(0);

        return planningResponse.getCalendar();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Informer.getInstance().inform(values[0]);
    }

    @Override
    protected void onPostExecute(List<CalendarInfo> calendarData) {
        super.onPostExecute(calendarData);
        Informer.getInstance().inform(AurionApi.Messages.PLANNING_SUCCESS);
        this.callback.run(calendarData);
    }
}
