package fr.clementduployez.aurionexplorer.Annuaire.Staff;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class StaffDirectoryFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private StaffAdapter mAdapter;
    private RecyclerViewHeader recyclerViewHeader;

    public static StaffDirectoryFragment newInstance() {
        final StaffDirectoryFragment staffDirectoryFragment = new StaffDirectoryFragment();
        return staffDirectoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_directory_staff, container, false);

        recyclerViewHeader = (RecyclerViewHeader) rootView.findViewById(R.id.header);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.directoryStaffRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new StaffAdapter(new ArrayList<StaffInfo>(), this);
        recyclerView.setAdapter(mAdapter);
        recyclerViewHeader.attachTo(recyclerView, true);
        return rootView;
    }
}
