package fr.clementduployez.aurionexplorer.Fragments.Conferences;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cjj.MaterialHeadView;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Fragments.Abstract.AurionPageFragment;
import fr.clementduployez.aurionexplorer.Fragments.Conferences.Adapters.ConferencesAdapter;
import fr.clementduployez.aurionexplorer.Fragments.Conferences.AsyncTasks.LoadConferencesListAsync;
import fr.clementduployez.aurionexplorer.Model.ConferencesInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/22/2016.
 */
public class ConferencesFragment extends AurionPageFragment<ConferencesInfo> {

    private View rootView;
    private RecyclerView recyclerView;
    private ConferencesAdapter adapter;
    private MaterialRefreshLayout materialRefreshLayout;
    private boolean notLoadedYet = true;
    private LinearLayout loadingLayout;
    private LoadConferencesListAsync loadConferencesListAsync;
    private LinearLayout containerLayout;

    public static ConferencesFragment newInstance() {
        final ConferencesFragment conferencesFragment = new ConferencesFragment();
        return conferencesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conferences, container, false);
        initViews();
        initAdapter();
        initData();
        return rootView;
    }

    @Override
    public void initViews() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.conferencesRecyclerView);
        materialRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.conferences_refresh_layout);
        containerLayout = (LinearLayout) rootView.findViewById(R.id.material_swipe_refresh_container);
        loadingLayout = (LinearLayout) rootView.findViewById(R.id.loadingLayout);
    }

    @Override
    public void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter == null) {
            adapter = new ConferencesAdapter(new ArrayList<ConferencesInfo>(), this);
        }
        recyclerView.setAdapter(adapter);
        adapter.updateSubtitle();

        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                onRefreshAsync();
            }
        });
    }

    @Override
    public void initData() {
        if (adapter.getItemCount() == 0) {
            onRefreshAsync();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        /*Hack to change the size of the refresh head view*/
        Field field;
        try {
            field = materialRefreshLayout.getClass().getDeclaredField("materialHeadView");
            field.setAccessible(true);
            MaterialHeadView head = (MaterialHeadView) field.get(materialRefreshLayout);
            head.setProgressSize(35);
            head.setProgressStokeWidth(2);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefreshAsync() {
        if (this.loadConferencesListAsync == null) {
            this.loadConferencesListAsync = new LoadConferencesListAsync(this);
            this.loadConferencesListAsync.execute();
            showProgressBar();
        }
        else {
            hideProgressBar();
        }
    }

    @Override
    public void showProgressBar() {
        if (notLoadedYet) {
            containerLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAsyncResult(List<ConferencesInfo> data) {
        hideProgressBar();
        setAdapter(data);
        this.loadConferencesListAsync = null;
    }

    @Override
    public void hideProgressBar() {
        materialRefreshLayout.finishRefresh();
        if (notLoadedYet){
            containerLayout.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            notLoadedYet = false;
        }
    }

    @Override
    public void setAdapter(List<ConferencesInfo> data) {
        adapter.setData(data);
    }
}
