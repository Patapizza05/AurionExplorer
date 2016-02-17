package fr.clementduployez.aurionexplorer.MonPlanning;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.androflo.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import fr.clementduployez.aurionexplorer.Informer;
import fr.clementduployez.aurionexplorer.R;
import android.support.v4.app.FragmentManager;
/**
 * Created by cdupl on 2/12/2016.
 */
public class CalendarFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, DatePickerDialog.OnDateSetListener {
    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadCalendarListAsync loadCalendarAsync;
    private CalendarAdapter mAdapter;
    private LinearLayout loadingLayout;

    private boolean notLoadedYet = true;
    private SectionedRecyclerViewAdapter mSectionedAdapter;

    public static CalendarFragment newInstance() {
        final CalendarFragment calendarFragment= new CalendarFragment();
        return calendarFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.calendarRecyclerView);
        loadingLayout = (LinearLayout) rootView.findViewById(R.id.loadingLayout);

        swipeRefreshLayout.setOnRefreshListener(this);
        ((AppCompatActivity) (this.getActivity())).getSupportActionBar().setSubtitle(null);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<CalendarInfo> emptyArray = new ArrayList<>();
        /*testArray.add(new CalendarInfo("lun. 22/2","17:30","19:15","Cours","Nature Of Sound","B305","M1"));
        testArray.add(new CalendarInfo("mar. 23/2","10:15","12:15","Cours","Module FHES d'Ethique et Psychologie","B802","M1"));
        testArray.add(new CalendarInfo("mar. 23/2","13:30","15:15","Cours","Module d'ouverture GRH","B802","M1"));
        testArray.add(new CalendarInfo("mer. 24/2","13:30","15:15","Cours","Module de Network Programming","B802","M1"));*/

        mAdapter = new CalendarAdapter(emptyArray);
        mSectionedAdapter  = new SectionedRecyclerViewAdapter(this.getActivity(),R.layout.fragment_calendar_recycler_section_item,R.id.calendar_section_title, mAdapter, mAdapter);
        mSectionedAdapter.setSections(emptyArray);
        recyclerView.setAdapter(mSectionedAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        //onRefresh();

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        datePickerDialog.setVibrate(false);
        datePickerDialog.setYearRange(2000, 2030);
        datePickerDialog.setCloseOnSingleTapDay(false);
        datePickerDialog.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(), "datepicker");

        return rootView;
    }

    public void showProgressBar() {
        //Needs a runnable to be able to display the progress bar (tin case the user launched the refreshTweets via the menu settings
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        if (notLoadedYet) {
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }
    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
        if (notLoadedYet){
            loadingLayout.setVisibility(View.GONE);
            notLoadedYet = false;
        }
    }

    @Override
    public void onRefresh() {
        if (loadCalendarAsync == null) {
            loadCalendarAsync = new LoadCalendarListAsync(this,"22/02/2016","29/02/2016");
            loadCalendarAsync.execute();
            showProgressBar();
        } else {
            hideProgressBar();
        }
        hideProgressBar();
    }

    public void onAsyncResult(ArrayList<CalendarInfo> calendarData) {
        loadCalendarAsync = null;
        hideProgressBar();
        setAdapter(calendarData);
    }

    public void setAdapter(ArrayList<CalendarInfo> calendarData) {
        mAdapter.setData(calendarData);
        mSectionedAdapter.setSections(calendarData);
        mAdapter.notifyDataSetChanged();
        mSectionedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        Informer.inform("new date:" + year + "-" + month + "-" + day);
    }
}







