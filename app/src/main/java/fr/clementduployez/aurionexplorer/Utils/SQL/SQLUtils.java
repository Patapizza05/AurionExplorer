package fr.clementduployez.aurionexplorer.Utils.SQL;

import android.util.Log;

import com.wellsql.generated.CalendarInfoTable;
import com.yarolegovich.wellsql.WellSql;
import com.yarolegovich.wellsql.core.Identifiable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.clementduployez.aurionexplorer.MesNotes.GradesInfo;
import fr.clementduployez.aurionexplorer.MonPlanning.CalendarInfo;

/**
 * Created by cdupl on 3/12/2016.
 */
public class SQLUtils {

    private static final Class<? extends Identifiable> GRADES_CLASS = GradesInfo.class;
    private static final Class<? extends Identifiable> CALENDAR_CLASS = CalendarInfo.class;


    private static boolean isCalendarExpiredItemsCleaned = false;


    public static void removeAndSave(List<GradesInfo> data)
    {
        WellSql.delete(GRADES_CLASS).execute();
        WellSql.insert(data).asSingleTransaction(true).execute();
    }

    public static void save(List<CalendarInfo> data)
    {
        for (CalendarInfo info : data)
        {
            WellSql.delete(CALENDAR_CLASS)
                    .where()
                        .equals(CalendarInfoTable.DAY, info.getDay())
                    .endWhere()
                    .execute();
        }
        WellSql.insert(data).asSingleTransaction(true).execute();
    }

    public static List<CalendarInfo> getCalendar(int endDayOffset) {

        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("EEEE d MMMM yyyy", new Locale("FR","fr"));

        String customDate = date.format(calendar.getTime());
        customDate = customDate.substring(0, 1).toUpperCase() + customDate.substring(1);

        List<CalendarInfo> data = (List<CalendarInfo>) WellSql.select(CALENDAR_CLASS)
                .where()
                .equals(CalendarInfoTable.DAY, customDate)
                .endWhere()
                .getAsModel();

        Log.i("Date test",customDate);

        int day = 0;
        while (day < endDayOffset)
        {
            calendar.add(Calendar.DATE, 1);
            customDate = date.format(calendar.getTime());
            customDate = customDate.substring(0, 1).toUpperCase() + customDate.substring(1);
            data.addAll((List<CalendarInfo>) WellSql.select(CALENDAR_CLASS)
                    .where()
                    .equals(CalendarInfoTable.DAY, customDate)
                    .endWhere()
                    .getAsModel());
            day++;
        }


        return data;
    }

    public static void clear() {
        WellSql.delete(CALENDAR_CLASS).execute();
        WellSql.delete(GRADES_CLASS).execute();
    }
}
