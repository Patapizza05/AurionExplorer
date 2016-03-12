package fr.clementduployez.aurionexplorer.MesConferences;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/22/2016.
 */
public class ConferencesHolder extends RecyclerView.ViewHolder {

    private final TextView date;
    private final TextView title;

    public ConferencesHolder(View itemView) {
        super(itemView);
        date = (TextView) itemView.findViewById(R.id.fragment_conference_date);
        title = (TextView) itemView.findViewById(R.id.fragment_conference_title);
    }

    public void bind(ConferencesInfo conferencesInfo) {
        Log.i(conferencesInfo.getDate(), conferencesInfo.getTitle());
        date.setText(conferencesInfo.getDate());

        title.setText(conferencesInfo.getTitle());

        Calendar c = Calendar.getInstance();
        try {
            if (c.before(ConferencesDateComparator.toCalendar(conferencesInfo))) {
                //itemView.setBackgroundResource(R.drawable.focus_red_ripple_background);
                date.setBackgroundResource(R.color.focus_red);
                date.setTextColor(Color.BLACK);
            }
            else {
                //itemView.setBackgroundResource(R.drawable.ripple_background);
                date.setBackgroundResource(R.color.colorPrimary);
                date.setTextColor(Color.WHITE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
