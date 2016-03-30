package fr.clementduployez.aurionexplorer.Anniversaires;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.MesNotes.GradesAdapter;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.SQL.SQLUtils;

/**
 * Created by cdupl on 3/15/2016.
 */
public class BirthdayPeriodFragment extends Fragment {
    private View rootView;
    private RecyclerView mRecyclerView;
    private BirthdayFragment birthdayFragment;
    private List<BirthdayInfo> data;
    private BirthdaysAdapter adapter;

    public static BirthdayPeriodFragment newInstance(BirthdayFragment fragment, List<BirthdayInfo> data) {
        final BirthdayPeriodFragment birthdayPeriodFragment = new BirthdayPeriodFragment();
        birthdayPeriodFragment.birthdayFragment = fragment;
        birthdayPeriodFragment.data = data;
        return birthdayPeriodFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_birthday_period, container, false);
        ((AppCompatActivity) (this.getActivity())).getSupportActionBar().setSubtitle(null);

        this.mRecyclerView = (RecyclerView) rootView.findViewById(R.id.birthdayRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setAdapter(new ArrayList<BirthdayInfo>());
        setForeground();
        return rootView;
    }

    public void setAdapter(List<BirthdayInfo> data) {
        if (data != null) {
            if (adapter == null) {
                adapter = new BirthdaysAdapter(data, this.birthdayFragment);
                mRecyclerView.setAdapter(adapter);
            }
            else {
                adapter.setData(data);
            }
        }
    }

    public void update(List<BirthdayInfo> data) {
        setAdapter(data);
    }

    public void setForeground() {
        if (adapter != null) {
            adapter.updateSubtitle();
        }

    }
}
