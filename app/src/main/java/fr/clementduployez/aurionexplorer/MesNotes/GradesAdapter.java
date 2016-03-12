package fr.clementduployez.aurionexplorer.MesNotes;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.AurionCacheUtils;

/**
 * Created by cdupl on 2/14/2016.
 */
public class GradesAdapter extends RecyclerView.Adapter<GradesHolder> {

    private final GradesFragment gradesFragment;
    private List<GradesInfo> gradesInfos;

    public GradesAdapter(List<GradesInfo> gradesInfos, GradesFragment gradesFragment) {
        setData(gradesInfos);
        this.gradesFragment = gradesFragment;
    }

    public void updateSubtitle() {
        int size = getItemCount();
        if (size > 1) {
            try {
                ((AppCompatActivity) (this.gradesFragment.getActivity())).getSupportActionBar().setSubtitle(getItemCount() + " Notes");
            }
            catch (NullPointerException ex) {
                //No toolbar
            }
        }
        else {
            try {
                ((AppCompatActivity) (this.gradesFragment.getActivity())).getSupportActionBar().setSubtitle(size + " Note");
            }
            catch (NullPointerException ex) {
                //No toolbar
            }
        }
    }

    /**
     * Used to create the view holder used by the adapter
     * @param parent
     * @param i
     * @return
     */
    @Override
    public GradesHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_grade_recycler_item, parent, false);
        return new GradesHolder(view);
    }

    @Override
    public void onBindViewHolder(GradesHolder holder, int position) {
        holder.bind(gradesInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return gradesInfos.size();
    }

    public void setData(List<GradesInfo> data) {
        if (data == null) {
            return;
        }

        this.gradesInfos = data;
        Collections.sort(this.gradesInfos, new DateComparator());
        updateSubtitle();
        notifyDataSetChanged();

    }
}
