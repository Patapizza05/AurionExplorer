package fr.clementduployez.aurionexplorer.MesNotes;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cjj.MaterialHeadView;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yarolegovich.wellsql.SelectQuery;
import com.yarolegovich.wellsql.WellSql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.SQL.SQLUtils;

/**
 * Created by cdupl on 2/12/2016.
 */
public class GradesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

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

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.marksRecyclerView);

        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter == null) {
            adapter = new GradesAdapter(new ArrayList<GradesInfo>(), this);
        }
        recyclerView.setAdapter(adapter);
        adapter.updateSubtitle();


        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

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
    }

    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        onRefreshGrades();
    }

    public void onRefreshGrades() {
        if (loadGradesListAsync == null) {
            loadGradesListAsync = new LoadGradesListAsync(this);
            loadGradesListAsync.execute();
            showProgressBar();
        } else {
            hideProgressBar();
        }
    }

    public void onAsyncResult(ArrayList<GradesInfo> gradesInfos) {
        loadGradesListAsync = null;
        hideProgressBar();
        setAdapter(gradesInfos);
    }

    public void setAdapter(List<GradesInfo> gradesInfos) {
        if (gradesInfos != null && gradesInfos.size() > 0) {
            if (adapter == null) {
                adapter = new GradesAdapter(gradesInfos, this);
                recyclerView.setAdapter(adapter);
            }
            else {
                adapter.setData(gradesInfos);
                SQLUtils.removeAndSave(gradesInfos);
            }
        }
        else {
            onRefresh();
        }
    }
}