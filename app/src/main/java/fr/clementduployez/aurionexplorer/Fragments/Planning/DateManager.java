package fr.clementduployez.aurionexplorer.Fragments.Planning;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by cdupl on 11/22/2016.
 */

public class DateManager implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private final CalendarFragment fragment;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private DateHolder beginDate;
    private DateHolder endDate;
    private DateHolder selectedDate;

    private DatePickerDialog datePickerDialog;
    private Button confirmDateButton;

    public DateManager(Button beginDateButton, Button endDateButton, Button confirmDateButton, CalendarFragment fragment) {
        this.fragment = fragment;
        this.confirmDateButton = confirmDateButton;
        this.datePickerDialog = initDatePicker();
        initDateButtons(beginDateButton, endDateButton);
        initListeners(beginDateButton, endDateButton, confirmDateButton);
    }

    private DatePickerDialog initDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        datePickerDialog.setVibrate(false);
        datePickerDialog.setYearRange(calendar.get(Calendar.YEAR) - 1, calendar.get(Calendar.YEAR) + 5);
        datePickerDialog.setCloseOnSingleTapDay(false);
        return datePickerDialog;
    }

    private void initDateButtons(Button begin, Button end) {
        final Calendar calendar = Calendar.getInstance();
        this.beginDate = new DateHolder(begin, calendar.getTime(), formatter);
        calendar.add(Calendar.DATE, Settings.Planning.DAY_OFFSET);
        this.endDate = new DateHolder(end, calendar.getTime(), formatter);
    }

    private void initListeners(Button begin, Button end, Button confirm) {
        begin.setOnClickListener(this);
        end.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(this.beginDate.getButton()))
        {
            this.selectedDate = this.beginDate;
            showDatePicker();
        }
        else if (v.equals(this.endDate.getButton()))
        {
            this.selectedDate = this.endDate;
            showDatePicker();
        }
        else if (v.equals(confirmDateButton))
        {
            fragment.onRefreshAsync();
        }

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        this.selectedDate.setDate(year, month, day);
        verifyDates();
    }

    public void addOneWeekBefore() {
        this.selectedDate = this.beginDate;
        this.beginDate.addDays(-7);
        verifyDatesLimit();
    }

    public void addOneWeekAfter() {
        this.selectedDate = this.endDate;
        this.endDate.addDays(7);
        verifyDatesLimit();
    }

    private void showDatePicker() {
        datePickerDialog.show(((AppCompatActivity) fragment.getActivity()).getSupportFragmentManager(), "datepicker");
    }

    public DateHolder getBeginDate() {
        return beginDate;
    }

    public DateHolder getEndDate() {
        return endDate;
    }

    public void verifyDates() {
        verifyDatesOrder();
        verifyDatesLimit();
    }

    public void verifyDatesOrder() {
        if (endDate.isBefore(beginDate)) {
            Date swap = endDate.getDate();
            endDate.setDate(beginDate.getDate());
            beginDate.setDate(swap);

            if (this.selectedDate == beginDate) {
                this.selectedDate = endDate;
            }
            else {
                this.selectedDate = beginDate;
            }
        }
    }

    private void verifyDatesLimit() {
        long nbDays = beginDate.daysBetween(endDate);
        if (nbDays > Settings.Planning.MAX_DAYS) {
            int daysToRemove = (int) nbDays - Settings.Planning.MAX_DAYS;
            if (this.selectedDate == beginDate) //Last change is at beginning --> remove on end
            {
                this.endDate.addDays(-daysToRemove);
            }
            else {
                this.beginDate.addDays(daysToRemove);
            }
        }
    }
}
