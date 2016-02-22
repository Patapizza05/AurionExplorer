package fr.clementduployez.aurionexplorer.MonPlanning;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.androflo.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.clementduployez.aurionexplorer.Informer;
import fr.clementduployez.aurionexplorer.R;
import android.support.v4.app.FragmentManager;
/**
 * Created by cdupl on 2/12/2016.
 */
public class CalendarFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, DatePickerDialog.OnDateSetListener, View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    private View rootView;
    private RecyclerView recyclerView;
    //private SwipeRefreshLayout swipeRefreshLayout;
    private LoadCalendarListAsync loadCalendarAsync;
    private CalendarAdapter mAdapter;
    private LinearLayout loadingLayout;
    private SwipeToLoadLayout swipeToLoadLayout;

    private boolean notLoadedYet = true;
    private SectionedRecyclerViewAdapter mSectionedAdapter;
    private Button confirmDateButton;
    private Button beginDateButton;
    private Button endDateButton;
    private DatePickerDialog datePickerDialog = initDatePicker();
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    private Button currentDateButton;

    public static CalendarFragment newInstance() {
        final CalendarFragment calendarFragment= new CalendarFragment();
        return calendarFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        swipeToLoadLayout = (SwipeToLoadLayout) rootView.findViewById(R.id.swipeToLoadLayout);
        //swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.swipe_target);
        loadingLayout = (LinearLayout) rootView.findViewById(R.id.loadingLayout);
        beginDateButton = (Button) rootView.findViewById(R.id.fragment_calendar_begin_date_button);
        endDateButton = (Button) rootView.findViewById(R.id.fragment_calendar_end_date_button);
        confirmDateButton = (Button) rootView.findViewById(R.id.fragment_calendar_confirm_date_button);

        initDateButtons();

        //swipeRefreshLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);


        beginDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        confirmDateButton.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<CalendarInfo> emptyArray = new ArrayList<>();
        /*testArray.add(new CalendarInfo("lun. 22/2","17:30","19:15","Cours","Nature Of Sound","B305","M1"));
        testArray.add(new CalendarInfo("mar. 23/2","10:15","12:15","Cours","Module FHES d'Ethique et Psychologie","B802","M1"));
        testArray.add(new CalendarInfo("mar. 23/2","13:30","15:15","Cours","Module d'ouverture GRH","B802","M1"));
        testArray.add(new CalendarInfo("mer. 24/2","13:30","15:15","Cours","Module de Network Programming","B802","M1"));*/

        if (mAdapter == null || mSectionedAdapter == null || mAdapter.getItemCount() == 0) {
            mAdapter = new CalendarAdapter(emptyArray,this);
            mSectionedAdapter  = new SectionedRecyclerViewAdapter(this.getActivity(),R.layout.fragment_calendar_recycler_section_item,R.id.calendar_section_title, mAdapter, mAdapter);
            mSectionedAdapter.setSections(emptyArray);
            recyclerView.setAdapter(mSectionedAdapter);
        }
        else {
            mSectionedAdapter.setSections(mAdapter.getData());
            recyclerView.setAdapter(mSectionedAdapter);
        }

        mAdapter.updateSubtitle();

        //swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        return rootView;
    }

    private void initDateButtons() {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        beginDateButton.setText(date.format(calendar.getTime()));
        calendar.add(Calendar.DATE, 6);
        endDateButton.setText(date.format(calendar.getTime()));
    }

    public DatePickerDialog initDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        datePickerDialog.setVibrate(false);
        datePickerDialog.setYearRange(calendar.get(Calendar.YEAR) - 1, calendar.get(Calendar.YEAR) + 5);
        datePickerDialog.setCloseOnSingleTapDay(false);
        return datePickerDialog;
    }

    public void showProgressBar() {
        //Needs a runnable to be able to display the progress bar (in case the user launched the refresh via the menu settings
        /*swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });*/

        /*swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });*/

        if (notLoadedYet) {
            swipeToLoadLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    public void showFooterProgressBar() {

    }

    public void hideProgressBar() {
        //swipeRefreshLayout.setRefreshing(false);
        swipeToLoadLayout.setRefreshing(false);
        if (notLoadedYet){
            swipeToLoadLayout.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            notLoadedYet = false;
        }
    }

    public void hideFooterProgressBar() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
            }
        }, 2000);
    }

    public void onAsyncResult(ArrayList<CalendarInfo> calendarData) {
        loadCalendarAsync = null;
        hideProgressBar();
        hideFooterProgressBar();
        setAdapter(calendarData);
    }

    public void setAdapter(ArrayList<CalendarInfo> calendarData) {
        mAdapter.setData(calendarData);
        mSectionedAdapter.setSections(calendarData);
        mAdapter.notifyDataSetChanged();
        mSectionedAdapter.notifyDataSetChanged();
    }

    private void setBeginDate(int year, int month, int day)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        this.beginDateButton.setText(sdf.format(calendar.getTime()));
    }

    private void setEndDate(int year, int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        this.endDateButton.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        this.currentDateButton.setText(sdf.format(calendar.getTime()));
    }



    private void showDatePicker() {
        datePickerDialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "datepicker");
    }

    @Override
    public void onClick(View v) {
            if (v.equals(beginDateButton))
            {
                this.currentDateButton = beginDateButton;
                showDatePicker();
            }
            else if (v.equals(endDateButton))
            {
                this.currentDateButton = endDateButton;
                showDatePicker();
            }
            else if (v.equals(confirmDateButton))
            {
                loadCalendar();
            }

    }

    @Override
    public void onLoadMore() {
        addOneWeekAfter();
        loadCalendar();
    }

    @Override
    public void onRefresh() {
        addOneWeekBefore();
        loadCalendar();
    }

    public void loadCalendar() {
        if (loadCalendarAsync == null) {
            loadCalendarAsync = new LoadCalendarListAsync(this,this.beginDateButton.getText().toString(),this.endDateButton.getText().toString());
            loadCalendarAsync.execute();
        } else {
            hideProgressBar();
            hideFooterProgressBar();
        }
        hideProgressBar();
        hideFooterProgressBar();
    }

    private void addOneWeekBefore() {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Calendar begin = dateToCalendar(date.parse(beginDateButton.getText().toString()));
            begin.add(Calendar.DATE, -7);
            setBeginDate(begin.get(Calendar.YEAR), begin.get(Calendar.MONTH), begin.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addOneWeekAfter() {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Calendar end = dateToCalendar(date.parse(endDateButton.getText().toString()));
            end.add(Calendar.DATE, 7);
            setEndDate(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}







