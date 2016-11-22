package fr.clementduployez.aurionexplorer.Fragments.Birthdays.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.clementduployez.aurionexplorer.Ui.Adapters.AurionAdapter;
import fr.clementduployez.aurionexplorer.Fragments.Abstract.AurionPageFragment;
import fr.clementduployez.aurionexplorer.Fragments.Birthdays.Holders.BirthdayHolder;
import fr.clementduployez.aurionexplorer.Model.BirthdayInfo;
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
