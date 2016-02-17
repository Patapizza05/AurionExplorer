package fr.clementduployez.aurionexplorer.MesNotes;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/12/2016.
 */
public class MarksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMarksListAsync loadMarksListAsync;
    private MarksAdapter adapter;
    private LinearLayout loadingLayout;

    private boolean notLoadedYet = true;

    public static MarksFragment newInstance() {
        final MarksFragment marksFragment = new MarksFragment();
        //final Bundle arguments = new Bundle();
        //arguments.putParcelable(tweetKey, tweet);
        //tweetFragment.setArguments(arguments);
        return marksFragment;
    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            tweetClickListener = (TweetClickListener) activity;
        } catch (Exception ex) {
            //@users and #hashtags won't be clickable
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.marks_fragment, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.marksRecyclerView);
        loadingLayout = (LinearLayout) rootView.findViewById(R.id.loadingLayout);

        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MarksAdapter(new ArrayList<MarksInfo>(), this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        onRefresh();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (loadMarksListAsync == null) {
            loadMarksListAsync = new LoadMarksListAsync(this);
            loadMarksListAsync.execute();
            showProgressBar();
        } else {
            hideProgressBar();
        }

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
        if (marksInfos != null && marksInfos.size() > 0) {
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
        }
    }
}