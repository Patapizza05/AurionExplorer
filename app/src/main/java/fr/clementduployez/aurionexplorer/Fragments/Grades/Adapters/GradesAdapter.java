package fr.clementduployez.aurionexplorer.Fragments.Grades.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Comparator;
import java.util.List;

import fr.clementduployez.aurionexplorer.Ui.Adapters.AurionAdapter;
import fr.clementduployez.aurionexplorer.Fragments.Abstract.AurionPageFragment;
import fr.clementduployez.aurionexplorer.Fragments.Grades.Utils.DateComparator;
import fr.clementduployez.aurionexplorer.Fragments.Grades.Holders.GradesHolder;
import fr.clementduployez.aurionexplorer.Model.GradesInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/14/2016.
 */
public class GradesAdapter extends AurionAdapter<GradesHolder,GradesInfo> {

    public GradesAdapter(List<GradesInfo> data, AurionPageFragment fragment) {
        super(data,fragment);
    }

    @Override
    public void updateSubtitle() {
        int size = getItemCount();
        if (size == 1) {
            setSubtitle(getItemCount() + " Note");
        }
        else {
            setSubtitle(getItemCount() + " Notes");
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
