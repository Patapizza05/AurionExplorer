package fr.clementduployez.aurionexplorer.Fragments.Directory.Students;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Fragments.Directory.Students.Adapters.StudentsAdapter;
import fr.clementduployez.aurionexplorer.Fragments.Directory.Students.AsyncTasks.SearchStudentsAsync;
import fr.clementduployez.aurionexplorer.Fragments.DirectoryFragment;
import fr.clementduployez.aurionexplorer.Model.StudentInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by Clement on 10/02/2017.
 */

public class StudentsDirectoryFragment extends DirectoryFragment {
    private View rootView;
    private RecyclerView recyclerView;
    private StudentsAdapter mAdapter;
    private SearchStudentsAsync searchStudentsAsync;

    public static StudentsDirectoryFragment newInstance() {
        final StudentsDirectoryFragment studentsDirectoryFragment = new StudentsDirectoryFragment();
        return studentsDirectoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_directory_staff, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.directoryStaffRecyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mAdapter == null) {
            mAdapter = new StudentsAdapter(new ArrayList<StudentInfo>(), this);
        }

        recyclerView.setAdapter(mAdapter);
        mAdapter.updateSubtitle();

        return rootView;
    }

    public void onAsyncResult(List<StudentInfo> staffData) {
        searchStudentsAsync = null;
        if (staffData != null) {
            mAdapter.setData(staffData);
            ((AppCompatActivity) (this.getActivity())).getSupportActionBar().setSubtitle(mAdapter.getItemCount()-1+" Résultats");
            mAdapter.getHeader().hideProgressBar();
        }
    }

    @Override
    public void sendForm(String lastName, String firstName, String code) {
        if (searchStudentsAsync == null) {
            mAdapter.getHeader().showProgressBar();
            searchStudentsAsync = new SearchStudentsAsync(this, lastName, firstName, code);
            searchStudentsAsync.execute();
        }
    }

}
