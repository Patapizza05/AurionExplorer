package fr.clementduployez.aurionexplorer.Fragments.Planning.Holders;

import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by cdupl on 11/22/2016.
 */

public class DateHolder {
    private Button button;
    private Date date;
    private SimpleDateFormat formatter;

    public DateHolder(Button button, Date date, SimpleDateFormat formatter) {
        this.button = button;
        this.formatter = formatter;
        this.setDate(date);
    }

    public void setDate(Date date) {
        this.date = date;
        this.button.setText(getFormattedDate());
    }

    public void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        setDate(calendar.getTime());
    }

    public void setCalendar(Calendar calendar) {
        setDate(calendar.getTime());
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        return formatter.format(date);
    }

    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        return calendar;
    }

    public Button getButton() {
        return button;
    }

    public void addDays(int nbDays) {
        Calendar calendar = getCalendar();
        calendar.add(Calendar.DATE, nbDays);
        setCalendar(calendar);
    }

    public boolean isBefore(DateHolder other) {
        return isBefore(other.getDate());
    }

    public boolean isBefore(Date other) {
        return this.date.compareTo(other) <= 0;
    }

    public long daysBetween(DateHolder other) {
        return daysBetween(other.getDate());
    }

    public long daysBetween(Date other) {
        long diff = other.getTime() - date.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

}
