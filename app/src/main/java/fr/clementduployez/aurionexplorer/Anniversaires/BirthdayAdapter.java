package fr.clementduployez.aurionexplorer.Anniversaires;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Comparator;
import java.util.List;

import fr.clementduployez.aurionexplorer.AurionAdapter;
import fr.clementduployez.aurionexplorer.AurionPageFragment;
import fr.clementduployez.aurionexplorer.MesNotes.DateComparator;
import fr.clementduployez.aurionexplorer.MesNotes.GradesHolder;
import fr.clementduployez.aurionexplorer.MesNotes.GradesInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 3/22/2016.
 */
public class BirthdayAdapter extends AurionAdapter<BirthdayHolder, BirthdayInfo> {

    public BirthdayAdapter(List<BirthdayInfo> data, AurionPageFragment fragment) {
        super(data,fragment);
    }

    @Override
    public void updateSubtitle() {
        int size = getItemCount();
        if (size == 1) {
            setSubtitle(getItemCount() + " Anniversaire");
        }
        else {
            setSubtitle(getItemCount() + " Anniversaires");
        }
    }

    /**
     * Used to create the view holder used by the adapter
     * @param parent
     * @param i
     * @return
     */
    @Override
    public BirthdayHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_grade_recycler_item, parent, false);
        return new BirthdayHolder(view);
    }

}
