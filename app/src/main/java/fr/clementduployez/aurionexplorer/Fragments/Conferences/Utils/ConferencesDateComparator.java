package fr.clementduployez.aurionexplorer.Fragments.Conferences.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import fr.clementduployez.aurionexplorer.Model.ConferencesInfo;

/**
 * Created by cdupl on 2/22/2016.
 */
public class ConferencesDateComparator implements Comparator<ConferencesInfo> {

    private static DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

    public int compare(ConferencesInfo o1, ConferencesInfo o2) {
        try {
            return toDate(o2).compareTo(toDate(o1));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Date toDate(ConferencesInfo o) throws ParseException {
        return f.parse(o.getDate());
    }

    public static Calendar toCalendar(ConferencesInfo o) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate(o));
        return cal;
    }
}
