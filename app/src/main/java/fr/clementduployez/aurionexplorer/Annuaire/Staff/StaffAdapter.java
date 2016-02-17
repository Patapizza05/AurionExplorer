package fr.clementduployez.aurionexplorer.Annuaire.Staff;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.MesNotes.GradesHolder;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class StaffAdapter extends RecyclerView.Adapter<StaffHolder> {

    private ArrayList<StaffInfo> staffData;

    public StaffAdapter(ArrayList<StaffInfo> staffInfoArrayList, StaffDirectoryFragment staffDirectoryFragment) {
        this.staffData = staffInfoArrayList;
    }

    @Override
    public StaffHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_directory_staff_recycler_item, parent, false);
        return new StaffHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffHolder holder, int position) {
        holder.bind(new StaffInfo("test","test","test","test","test",null));
    }

    public void setData(ArrayList<StaffInfo> staffInfoArrayList) {
        this.staffData = staffInfoArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //return this.staffData.size();
        return 3;
    }
}
