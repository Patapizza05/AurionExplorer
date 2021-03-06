package fr.clementduployez.aurionexplorer.Fragments.Directory.Staff;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.Adapters.StaffAdapter;
import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.AsyncTasks.SearchStaffAsync;
import fr.clementduployez.aurionexplorer.Fragments.DirectoryFragment;
import fr.clementduployez.aurionexplorer.Model.StaffInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class StaffDirectoryFragment extends DirectoryFragment {
    private View rootView;
    private RecyclerView recyclerView;
    private StaffAdapter mAdapter;
    private SearchStaffAsync searchStaffAsync;

    public static StaffDirectoryFragment newInstance() {
        final StaffDirectoryFragment staffDirectoryFragment = new StaffDirectoryFragment();
        return staffDirectoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_directory_staff, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.directoryStaffRecyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mAdapter == null) {
            mAdapter = new StaffAdapter(new ArrayList<StaffInfo>(), this);
        }

        recyclerView.setAdapter(mAdapter);
        mAdapter.updateSubtitle();

        return rootView;
    }

    @Override
    public void sendForm(String lastName, String firstName, String code) {
        if (searchStaffAsync == null) {
            mAdapter.getHeader().showProgressBar();
            searchStaffAsync = new SearchStaffAsync(this, lastName, firstName, code);
            searchStaffAsync.execute();
        }
    }

    public void onAsyncResult(List<StaffInfo> staffData) {
        searchStaffAsync = null;
        if (staffData != null) {
            mAdapter.setData(staffData);
            ((AppCompatActivity) (this.getActivity())).getSupportActionBar().setSubtitle(mAdapter.getItemCount()-1+" Résultats");
            mAdapter.getHeader().hideProgressBar();
        }
    }


}
