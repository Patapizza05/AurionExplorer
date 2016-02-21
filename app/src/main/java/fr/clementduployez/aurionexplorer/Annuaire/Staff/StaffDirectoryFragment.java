package fr.clementduployez.aurionexplorer.Annuaire.Staff;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class StaffDirectoryFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private RecyclerView recyclerView;
    private StaffAdapter mAdapter;
    private RecyclerViewHeader recyclerViewHeader;
    private Button button;
    private EditText lastName;
    private EditText firstName;
    private EditText code;
    private SearchStaffAsync searchStaffAsync;

    public static StaffDirectoryFragment newInstance() {
        final StaffDirectoryFragment staffDirectoryFragment = new StaffDirectoryFragment();
        return staffDirectoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_directory_staff, container, false);

        recyclerViewHeader = (RecyclerViewHeader) rootView.findViewById(R.id.header);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.directoryStaffRecyclerView);
        button = (Button) rootView.findViewById(R.id.staff_directory_confirm_button);
        lastName = (EditText) rootView.findViewById(R.id.staff_directory_nom);
        firstName = (EditText) rootView.findViewById(R.id.staff_directory_prenom);
        code = (EditText) rootView.findViewById(R.id.staff_directory_code);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new StaffAdapter(new ArrayList<StaffInfo>(), this);
        recyclerView.setAdapter(mAdapter);
        recyclerViewHeader.attachTo(recyclerView, true);
        button.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (searchStaffAsync == null) {
            searchStaffAsync = new SearchStaffAsync(this,lastName.getText().toString(), this.firstName.getText().toString(), this.code.getText().toString());
            searchStaffAsync.execute();
        }
    }

    public void onAsyncResult(ArrayList<StaffInfo> staffData) {
        searchStaffAsync = null;
        if (staffData != null) {
            mAdapter.setData(staffData);
        }
    }
}
