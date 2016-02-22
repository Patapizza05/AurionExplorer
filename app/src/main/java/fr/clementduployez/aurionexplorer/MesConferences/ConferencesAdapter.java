package fr.clementduployez.aurionexplorer.MesConferences;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import fr.clementduployez.aurionexplorer.MesNotes.DateComparator;
import fr.clementduployez.aurionexplorer.MesNotes.GradesHolder;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/22/2016.
 */
public class ConferencesAdapter extends RecyclerView.Adapter<ConferencesHolder> {
    private ArrayList<ConferencesInfo> data;
    private final ConferencesFragment conferencesFragment;

    public ConferencesAdapter(ArrayList<ConferencesInfo> conferencesInfos, ConferencesFragment conferencesFragment) {
        this.conferencesFragment = conferencesFragment;
        this.data = conferencesInfos;
    }

    @Override
    public ConferencesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_conference_recycler_item, parent, false);
        return new ConferencesHolder(view);
    }

    @Override
    public void onBindViewHolder(ConferencesHolder holder, int position) {
        holder.bind(this.data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateSubtitle() {
            int size = getItemCount();
            if (size > 1) {
                try {
                    ((AppCompatActivity) (this.conferencesFragment.getActivity())).getSupportActionBar().setSubtitle(size + " Conférences");
                }
                catch (NullPointerException ex) {
                    //No toolbar
                }
            }
            else {
                try {
                    ((AppCompatActivity) (this.conferencesFragment.getActivity())).getSupportActionBar().setSubtitle(size + " Conférence");
                }
                catch (NullPointerException ex) {
                    //No toolbar
                }
            }
    }

    public void setData(ArrayList<ConferencesInfo> data) {
        this.data = data;
        Collections.sort(this.data, new ConferencesDateComparator());
        updateSubtitle();
        notifyDataSetChanged();
    }
}
