package fr.clementduployez.aurionexplorer.MesNotes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Created by cdupl on 2/17/2016.
 */
public class DateComparator implements Comparator<MarksInfo> {

    private static DateFormat f = new SimpleDateFormat("dd/MM/yy");

    @Override
    public int compare(MarksInfo o1, MarksInfo o2) {
        try {
            return f.parse(o2.getDate()).compareTo(f.parse(o1.getDate()));
        }
        catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

/*Comparator<MarksInfo>() {
            DateFormat f = new SimpleDateFormat("dd/MM/yy");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });*/
