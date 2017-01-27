package fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.Adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.StaffDirectoryFragment;
import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.Holders.StaffHeader;
import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.Holders.StaffHolder;
import fr.clementduployez.aurionexplorer.Model.StaffInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class StaffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<StaffInfo> staffData;
    private StaffDirectoryFragment staffDirectoryFragment;
    private StaffHeader mHeader;

    public StaffAdapter(ArrayList<StaffInfo> staffInfoArrayList, StaffDirectoryFragment staffDirectoryFragment) {
        this.staffData = staffInfoArrayList;
        this.staffDirectoryFragment = staffDirectoryFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_directory_staff_form, parent, false);
            mHeader = new StaffHeader(view, this.staffDirectoryFragment);
            return mHeader;
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_directory_staff_recycler_item, parent, false);
            return new StaffHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StaffHeader)
        {
            ((StaffHeader)holder).bind();
        }
        else {
            ((StaffHolder)holder).bind(this.staffData.get(position-1));
        }
    }

    public void setData(List<StaffInfo> staffData) {
        this.staffData = staffData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.staffData.size()+1;
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
                ((AppCompatActivity) (this.staffDirectoryFragment.getActivity())).getSupportActionBar().setSubtitle(size - 1 + " Résultats");
            }
            catch (NullPointerException ex) {
                //No Toolbar
            }
        }
        else {
            try {
                ((AppCompatActivity) (this.staffDirectoryFragment.getActivity())).getSupportActionBar().setSubtitle(size - 1 + " Résultat");
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