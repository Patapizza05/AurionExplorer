package fr.clementduployez.aurionexplorer.MesNotes;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/14/2016.
 */
public class MarksAdapter extends RecyclerView.Adapter<MarksHolder> {

    private final MarksFragment marksFragment;
    private ArrayList<MarksInfo> marksInfos;

    public MarksAdapter(ArrayList<MarksInfo> marksInfos, MarksFragment marksFragment) {
        setArrayList(marksInfos);
        this.marksFragment = marksFragment;
    }

    private void setArrayList(ArrayList<MarksInfo> marksInfo) {
        this.marksInfos = marksInfo;
        Collections.sort(this.marksInfos, new DateComparator());
        updateSubtitle();
        notifyDataSetChanged();
    }

    private void updateSubtitle() {
        try {
            ((AppCompatActivity) (this.marksFragment.getActivity())).getSupportActionBar().setSubtitle(getItemCount() + " Notes");
        }
        catch (NullPointerException ex) {
            //No toolbar
        }
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
        setArrayList(data);
    }
}
