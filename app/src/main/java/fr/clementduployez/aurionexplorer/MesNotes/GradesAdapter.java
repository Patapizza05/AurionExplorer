package fr.clementduployez.aurionexplorer.MesNotes;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.clementduployez.aurionexplorer.AurionAdapter;
import fr.clementduployez.aurionexplorer.AurionPageFragment;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.AurionCacheUtils;

/**
 * Created by cdupl on 2/14/2016.
 */
public class GradesAdapter extends AurionAdapter<GradesHolder,GradesInfo> {

    public GradesAdapter(List<GradesInfo> data, AurionPageFragment fragment) {
        super(data,fragment);
    }

    public void updateSubtitle() {
        int size = getItemCount();
        if (size == 1) {
            updateSubtitle(getItemCount()+" Note");
        }
        else {
            updateSubtitle(getItemCount()+" Notes");
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
    public Comparator getComparator() {
        return new DateComparator();
    }


}
