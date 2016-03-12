package fr.clementduployez.aurionexplorer.MonPlanning;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.androflo.sectionedrecyclerviewadapter.Sectionizer;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.AurionAdapter;
import fr.clementduployez.aurionexplorer.AurionPageFragment;
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

    public void updateSubtitle() {
        int size = getItemCount();
        if (size == 1) {
            updateSubtitle(size + " Évènement");
        }
        else {
            updateSubtitle(size + " Évènements");
        }

    }

    @Override
    public String getSectionTitle(CalendarInfo s) {
        return s.getDay();
    }
}
