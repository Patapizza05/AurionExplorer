package fr.clementduployez.aurionexplorer.Fragments.Planning;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.github.androflo.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import java.util.List;

import fr.clementduployez.aurionexplorer.Fragments.Abstract.AurionPageFragment;
import fr.clementduployez.aurionexplorer.Fragments.Planning.Adapters.CalendarAdapter;
import fr.clementduployez.aurionexplorer.Fragments.Planning.Adapters.SectionedCalendarRecyclerViewAdapter;
import fr.clementduployez.aurionexplorer.Settings.Settings;
import fr.clementduployez.aurionexplorer.Utils.Callback;
import fr.clementduployez.aurionexplorer.Fragments.Planning.AsyncTasks.LoadCalendarListAsync;
import fr.clementduployez.aurionexplorer.Model.CalendarInfo;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.SQL.SQLUtils;

/**
 * Created by cdupl on 2/12/2016.
 */
public class CalendarFragment extends AurionPageFragment<CalendarInfo> implements OnRefreshListener, OnLoadMoreListener {
    private View rootView;
    private RecyclerView recyclerView;
    private LoadCalendarListAsync loadCalendarAsync;
    private CalendarAdapter mAdapter;
    private LinearLayout loadingLayout;
    private SwipeToLoadLayout swipeToLoadLayout;

    private boolean notLoadedYet = true;
    private SectionedRecyclerViewAdapter mSectionedAdapter;

    private DateManager dateManager;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        initViews();
        initAdapter();
        initData();
        return rootView;
    }

    @Override
    public void initViews() {
        swipeToLoadLayout = (SwipeToLoadLayout) rootView.findViewById(R.id.swipeToLoadLayout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.swipe_target);
        loadingLayout = (LinearLayout) rootView.findViewById(R.id.loadingLayout);

        Button beginDateButton = (Button) rootView.findViewById(R.id.fragment_calendar_begin_date_button);
        Button endDateButton = (Button) rootView.findViewById(R.id.fragment_calendar_end_date_button);
        Button confirmDateButton = (Button) rootView.findViewById(R.id.fragment_calendar_confirm_date_button);
        this.dateManager = new DateManager(beginDateButton, endDateButton, confirmDateButton, this);

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @Override
    public void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mAdapter == null || mSectionedAdapter == null || mAdapter.getItemCount() == 0) {
            List<CalendarInfo> data = SQLUtils.getCalendarItems(Settings.Planning.DAY_OFFSET);
            mAdapter = new CalendarAdapter(data,this);
            mSectionedAdapter  = new SectionedCalendarRecyclerViewAdapter(this.getActivity(),R.layout.fragment_calendar_recycler_section_item,R.id.calendar_section_title, mAdapter, mAdapter);
            mSectionedAdapter.setSections(data);
            recyclerView.setAdapter(mSectionedAdapter);
        }
        else {
            mSectionedAdapter.setSections(mAdapter.getData());
            recyclerView.setAdapter(mSectionedAdapter);
        }


        mAdapter.updateSubtitle();
    }

    @Override
    public void initData() {
        //Already done in initAdapter();
    }

    @Override
    public void showProgressBar() {
        if (notLoadedYet) {
            swipeToLoadLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
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

    public void onAsyncResult(List<CalendarInfo> calendarData) {
        loadCalendarAsync = null;
        hideProgressBar();
        hideFooterProgressBar();
        setAdapter(calendarData);
    }

    @Override
    public void setAdapter(List<CalendarInfo> calendarData) {
        mAdapter.setData(calendarData);
        mSectionedAdapter.setSections(calendarData);
        mAdapter.notifyDataSetChanged();
        mSectionedAdapter.notifyDataSetChanged();
        SQLUtils.save(calendarData);
    }

    @Override
    public void onLoadMore() {
        dateManager.addOneWeekAfter();
        onRefreshAsync();
    }

    @Override
    public void onRefresh() {
        dateManager.addOneWeekBefore();
        onRefreshAsync();
    }

    @Override
    public void onRefreshAsync() {
        if (loadCalendarAsync == null) {

            Callback<CalendarInfo> callback = new Callback<CalendarInfo>() {
                @Override
                public void run(List<CalendarInfo> data) {
                    onAsyncResult(data);
                }
            };

            loadCalendarAsync = new LoadCalendarListAsync(callback);
            loadCalendarAsync.execute(this.dateManager.getBeginDate().getDate(), this.dateManager.getEndDate().getDate());
            List<CalendarInfo> data = SQLUtils.getCalendarItems(this.dateManager.getBeginDate().getDate(), this.dateManager.getEndDate().getDate());
            if (data != null && data.size() > 0)
            {
                setAdapter(data);
            }
        }
        hideProgressBar();
        hideFooterProgressBar();
    }




}







