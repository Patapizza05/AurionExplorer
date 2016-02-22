package fr.clementduployez.aurionexplorer.MesConferences;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/22/2016.
 */
public class ConferencesFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private ConferencesAdapter adapter;
    private MaterialRefreshLayout materialRefreshLayout;
    private boolean notLoadedYet;
    private LinearLayout loadingLayout;
    private LoadConferencesListAsync loadConferencesListAsync;

    public static ConferencesFragment newInstance() {
        final ConferencesFragment conferencesFragment = new ConferencesFragment();
        return conferencesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grades, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.conferencesRecyclerView);
        materialRefreshLayout = (MaterialRefreshLayout) rootView.findViewById(R.id.material_swipe_refresh);
        loadingLayout = (LinearLayout) rootView.findViewById(R.id.loadingLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter == null) {
            adapter = new ConferencesAdapter(new ArrayList<ConferencesInfo>(), this);
        }
        recyclerView.setAdapter(adapter);
        adapter.updateSubtitle();

        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                loadData();
            }
        });

        if (adapter.getItemCount() == 0) {
            loadData();
        }

        return rootView;
    }

    private void loadData() {
        if (this.loadConferencesListAsync == null) {
            this.loadConferencesListAsync = new LoadConferencesListAsync(this);
            this.loadConferencesListAsync.execute();
        }
        hideProgressBar();
    }

    public void showProgressBar() {
        if (notLoadedYet) {
            materialRefreshLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            notLoadedYet = false;
        }
    }

    public void onAsyncResult(ArrayList<ConferencesInfo> data) {
        hideProgressBar();
        adapter.setData(data);
    }

    public void hideProgressBar() {
        materialRefreshLayout.finishRefresh();
        if (notLoadedYet){
            materialRefreshLayout.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
            notLoadedYet = false;
        }
    }
}
