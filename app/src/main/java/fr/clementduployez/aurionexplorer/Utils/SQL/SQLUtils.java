package fr.clementduployez.aurionexplorer.Utils.SQL;

import android.util.Log;

import com.wellsql.generated.CalendarInfoTable;
import com.yarolegovich.wellsql.WellSql;
import com.yarolegovich.wellsql.core.Identifiable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


    public static void removeAndSave(List<GradesInfo> data)
    {
        WellSql.delete(GRADES_CLASS).execute();
        WellSql.insert(data).asSingleTransaction(true).execute();
    }

    public static void add(List<GradesInfo> data) {
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


    public static List<CalendarInfo> getCalendarItems(int endDayOffset) {

        final Calendar calendar = Calendar.getInstance();
        return getCalendarItems(calendar,endDayOffset);
    }

    public static List<CalendarInfo> getCalendarItems(Date begin, Date end)
    {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);

        int endDayOffset = (int) (end.getTime() - begin.getTime()) / (1000 * 60 * 60 *24);
        Log.i("EndDayOffset",""+endDayOffset);
        return getCalendarItems(calendar,endDayOffset);
    }

    public static List<CalendarInfo> getCalendarItems(final Calendar begin, int endDayOffset)
    {
        List<CalendarInfo> data = addDayItemsToList(begin.getTime(), null);

        int day = 0;
        while (day < endDayOffset)
        {
            begin.add(Calendar.DATE, 1);
            data = addDayItemsToList(begin.getTime(),data);
            day++;
        }

        return data;
    }

    private static List<CalendarInfo> addDayItemsToList(Date date, List<CalendarInfo> data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE d MMMM yyyy", new Locale("FR","fr"));
        String customDate = simpleDateFormat.format(date);
        customDate = customDate.substring(0, 1).toUpperCase() + customDate.substring(1);

        if (data == null) {
            data = (List<CalendarInfo>) WellSql.select(CALENDAR_CLASS)
                    .where()
                    .equals(CalendarInfoTable.DAY, customDate)
                    .endWhere()
                    .getAsModel();
        }
        else
        {
            data.addAll((List<CalendarInfo>) WellSql.select(CALENDAR_CLASS)
                    .where()
                    .equals(CalendarInfoTable.DAY, customDate)
                    .endWhere()
                    .getAsModel());
        }

        return data;
    }

    public static void clear() {
        WellSql.delete(CALENDAR_CLASS).execute();
        WellSql.delete(GRADES_CLASS).execute();
    }


}
