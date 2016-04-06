package fr.clementduployez.aurionexplorer.MesNotes;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yarolegovich.wellsql.SelectQuery;
import com.yarolegovich.wellsql.WellSql;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.AurionPageFragment;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.SQL.SQLUtils;

/**
 * Created by cdupl on 2/12/2016.
 */
public class GradesFragment extends AurionPageFragment<GradesInfo> implements ILoadGradesListAsyncReceiver, SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadGradesListAsync loadGradesListAsync;
    private GradesAdapter adapter;

    public static GradesFragment newInstance() {
        final GradesFragment gradesFragment = new GradesFragment();
        return gradesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        initViews();
        initAdapter();
        initData();
        return rootView;
    }

    @Override
    public void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.marksRecyclerView);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter == null) {
            adapter = new GradesAdapter(new ArrayList<GradesInfo>(), this);
        }
        recyclerView.setAdapter(adapter);
        adapter.updateSubtitle();
    }

    @Override
    public void initData() {
        if (adapter.getItemCount() == 0) {
            WellSql.select(GradesInfo.class).getAsModelAsync(new SelectQuery.Callback<List<GradesInfo>>() {
                @Override
                public void onDataReady(List<GradesInfo> gradesInfos) {
                        if (gradesInfos.size() > 0) {
                            setAdapter(gradesInfos);
                        } else {
                            onRefresh();
                        }
                }
            });
        }
    }

    @Override
    public void showProgressBar() {
        //Needs a runnable to be able to display the progress bar (in case the user launched the refreshTweets via the menu settings
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        onRefreshAsync();
    }

    public void onRefreshAsync() {
        if (loadGradesListAsync == null) {
            loadGradesListAsync = new LoadGradesListAsync(this);
            loadGradesListAsync.execute();
            showProgressBar();
        } else {
            hideProgressBar();
        }
    }

    public void onAsyncProgress(List<GradesInfo> data) {
        onAsyncResult(data);
    }

    public void onAsyncResult(List<GradesInfo> data) {
        loadGradesListAsync = null;
        hideProgressBar();
        setAdapter(data);
    }

    @Override
    public void setAdapter(List<GradesInfo> data) {
        if (data != null && data.size() > 0) {
            if (adapter == null) {
                adapter = new GradesAdapter(data, this);
                recyclerView.setAdapter(adapter);
            }
            else {
                adapter.setData(data);
                SQLUtils.removeAndSave(data);
            }
        }
    }

    public void addToAdapter(List<GradesInfo> data) {
        if (data != null && data.size() > 0) {
            if (adapter == null) {
                adapter = new GradesAdapter(data, this);
                recyclerView.setAdapter(adapter);
            }
            else {
                adapter.getData().addAll(data);
                adapter.notifyDataSetChanged();
                SQLUtils.add(data);
            }
        }
    }


}