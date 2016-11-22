package fr.clementduployez.aurionexplorer.Fragments.Planning.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.androflo.sectionedrecyclerviewadapter.Sectionizer;

import java.util.List;

import fr.clementduployez.aurionexplorer.Ui.Adapters.AurionAdapter;
import fr.clementduployez.aurionexplorer.Fragments.Abstract.AurionPageFragment;
import fr.clementduployez.aurionexplorer.Fragments.Planning.Holders.CalendarHolder;
import fr.clementduployez.aurionexplorer.Model.CalendarInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class CalendarAdapter extends AurionAdapter<CalendarHolder,CalendarInfo> implements Sectionizer<CalendarInfo> {

    public CalendarAdapter(List<CalendarInfo> data, AurionPageFragment<CalendarInfo> fragment) {
        super(data, fragment);
    }


    @Override
    public CalendarHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_calendar_recycler_item, parent, false);
        return new CalendarHolder(view);
    }

    @Override
    public void updateSubtitle() {
        int size = getItemCount();
        if (size == 1) {
            setSubtitle(size + " Évènement");
        }
        else {
            setSubtitle(size + " Évènements");
        }

    }

    @Override
    public String getSectionTitle(CalendarInfo s) {
        return s.getDay();
    }
}
