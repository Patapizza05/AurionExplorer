package fr.clementduployez.aurionexplorer.MesNotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/14/2016.
 */
public class MarksAdapter extends RecyclerView.Adapter<MarksHolder> {

    private ArrayList<MarksInfo> marksInfos;

    public MarksAdapter(ArrayList<MarksInfo> marksInfos) {
        this.marksInfos = marksInfos;
    }

    /**
     * Used to create the view holder used by the adapter
     * @param parent
     * @param i
     * @return
     */
    @Override
    public MarksHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mark_recycler_item, parent, false);
        return new MarksHolder(view);
    }

    @Override
    public void onBindViewHolder(MarksHolder holder, int position) {
        holder.bind(marksInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return marksInfos.size();
    }

    public void setData(ArrayList<MarksInfo> data) {
        this.marksInfos = data;
    }
}
