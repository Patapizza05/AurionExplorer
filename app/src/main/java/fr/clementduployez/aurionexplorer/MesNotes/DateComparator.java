package fr.clementduployez.aurionexplorer.MesNotes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import fr.clementduployez.aurionexplorer.MesConferences.ConferencesInfo;
import fr.clementduployez.aurionexplorer.MesNotes.GradesInfo;

/**
 * Created by cdupl on 2/17/2016.
 */
public class DateComparator implements Comparator<GradesInfo> {

    private static DateFormat f = new SimpleDateFormat("dd/MM/yy");

    @Override
    public int compare(GradesInfo o1, GradesInfo o2) {
        try {
            return f.parse(o2.getDate()).compareTo(f.parse(o1.getDate()));
        }
        catch (ParseException e) {
            try {
                f.parse(o2.getDate());
            }
            catch(ParseException ex) {
                return -1;
            }

            try {
                f.parse(o1.getDate());
            }
            catch(ParseException ex) {
                return 1;
            }
        }
        return 0;
    }

}

/*Comparator<GradesInfo>() {
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
