package fr.clementduployez.aurionexplorer.Fragments.Directory.Students.Adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.Holders.StaffHeader;
import fr.clementduployez.aurionexplorer.Fragments.Directory.Students.Holders.StudentHolder;
import fr.clementduployez.aurionexplorer.Fragments.DirectoryFragment;
import fr.clementduployez.aurionexplorer.Model.StudentInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by Clement on 10/02/2017.
 */

public class StudentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<StudentInfo> studentsData;
    private DirectoryFragment studentsDirectoryFragment;
    private StaffHeader mHeader;

    public StudentsAdapter(ArrayList<StudentInfo> studentInfoArrayList, DirectoryFragment studentsDirectoryFragment) {
        this.studentsData = studentInfoArrayList;
        this.studentsDirectoryFragment = studentsDirectoryFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_directory_staff_form, parent, false);
            mHeader = new StaffHeader(view, this.studentsDirectoryFragment);
            return mHeader;
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_directory_student_recycler_item, parent, false);
            return new StudentHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StaffHeader)
        {
            ((StaffHeader)holder).bind(false);
        }
        else {
            ((StudentHolder)holder).bind(this.studentsData.get(position-1));
        }
    }

    public void setData(List<StudentInfo> staffData) {
        this.studentsData = staffData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.studentsData.size()+1;
    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    public void updateSubtitle() {
        int size = getItemCount();
        if (size > 1) {
            try {
                ((AppCompatActivity) (this.studentsDirectoryFragment.getActivity())).getSupportActionBar().setSubtitle(size - 1 + " Résultats");
            }
            catch (NullPointerException ex) {
                //No Toolbar
            }
        }
        else {
            try {
                ((AppCompatActivity) (this.studentsDirectoryFragment.getActivity())).getSupportActionBar().setSubtitle(size - 1 + " Résultat");
            }
            catch (NullPointerException ex) {
                //No Toolbar
            }
        }

    }

    public StaffHeader getHeader() {
        return mHeader;
    }

}
