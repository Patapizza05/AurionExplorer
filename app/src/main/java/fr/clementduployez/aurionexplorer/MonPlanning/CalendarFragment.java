package fr.clementduployez.aurionexplorer.MonPlanning;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.androflo.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.MesNotes.LoadMarksListAsync;
import fr.clementduployez.aurionexplorer.MesNotes.MarksAdapter;
import fr.clementduployez.aurionexplorer.MesNotes.MarksInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/12/2016.
 */
public class CalendarFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMarksListAsync loadMarksListAsync;
    private CalendarAdapter adapter;
    private LinearLayout loadingLayout;

    private boolean notLoadedYet = true;

    public static CalendarFragment newInstance() {
        final CalendarFragment calendarFragment= new CalendarFragment();
        return calendarFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.calendarRecyclerView);
        loadingLayout = (LinearLayout) rootView.findViewById(R.id.loadingLayout);

        //swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<CalendarInfo> testArray = new ArrayList<>();
        testArray.add(new CalendarInfo("lun. 22/2","17:30","19:15","Cours","Nature Of Sound","B305","M1"));
        testArray.add(new CalendarInfo("mar. 23/2","10:15","12:15","Cours","Module FHES d'Ethique et Psychologie","B802","M1"));
        testArray.add(new CalendarInfo("mar. 23/2","13:30","15:15","Cours","Module d'ouverture GRH","B802","M1"));
        testArray.add(new CalendarInfo("mer. 24/2","13:30","15:15","Cours","Module de Network Programming","B802","M1"));

        //create an adapter
        adapter = new CalendarAdapter(testArray);

        SectionedRecyclerViewAdapter mSectionedAdapter  = new    SectionedRecyclerViewAdapter(this.getActivity(),R.layout.calendar_recycler_section_item,R.id.calendar_section_title, adapter, adapter);
        mSectionedAdapter.setSections(testArray);//the list object do you use

        recyclerView.setAdapter(mSectionedAdapter);
        adapter.notifyDataSetChanged();
        mSectionedAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        //onRefresh();

        return rootView;
    }

    public void showProgressBar() {
        //Needs a runnable to be able to display the progress bar (in case the user launched the refreshTweets via the menu settings
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        if (notLoadedYet) loadingLayout.setVisibility(View.VISIBLE);
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
        /*if (loadMarksListAsync == null) {
            loadMarksListAsync = new LoadMarksListAsync(this);
            loadMarksListAsync.execute();
            showProgressBar();
        } else {
            hideProgressBar();
        }*/

    }

    public void inform(String text) {
        try{
            Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT).show();
        }
        catch (NullPointerException e) {
            //Do nothing. The app might have been closed for example.
        }
    }

    public void onAsyncResult(ArrayList<MarksInfo> marksInfos) {
        loadMarksListAsync = null;
        hideProgressBar();
        setAdapter(marksInfos);
    }

    public void setAdapter(ArrayList<MarksInfo> marksInfos) {
        /*if (marksInfos != null && marksInfos.size() > 0) {
            if (adapter == null) {
                adapter = new MarksAdapter(marksInfos, this);
                recyclerView.setAdapter(adapter);
            }
            else {
                adapter.setData(marksInfos);
            }
        }
        else {
            onRefresh();
        }*/
    }
}







