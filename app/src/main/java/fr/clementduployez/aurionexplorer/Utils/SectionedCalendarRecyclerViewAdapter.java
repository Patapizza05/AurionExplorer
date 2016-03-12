package fr.clementduployez.aurionexplorer.Utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androflo.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import com.github.androflo.sectionedrecyclerviewadapter.Sectionizer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 3/12/2016.
 */
public class SectionedCalendarRecyclerViewAdapter extends SectionedRecyclerViewAdapter {


    public SectionedCalendarRecyclerViewAdapter(Context context, int sectionResourceId, int textResourceId, RecyclerView.Adapter baseAdapter, Sectionizer sectionizer) {
        super(context, sectionResourceId, textResourceId, baseAdapter, sectionizer);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, int position) {
        super.onBindViewHolder(sectionViewHolder,position);

        if(this.isSectionHeaderPosition(position)) {
            final Calendar calendar = Calendar.getInstance();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE d MMMM yyyy", new Locale("FR","fr"));
            String customDate = simpleDateFormat.format(calendar.getTime());
            customDate = customDate.substring(0, 1).toUpperCase() + customDate.substring(1);

            TextView view = ((SectionedRecyclerViewAdapter.SectionViewHolder)sectionViewHolder).title;
            if (view.getText().toString().equals(customDate))
            {
                view.setBackgroundResource(R.color.colorPrimary);
                view.setTextColor(Color.WHITE);

            } else {
                view.setBackgroundResource(R.color.transparent);
                view.setTextColor(AurionExplorerApplication.getContext().getResources().getColor(R.color.colorPrimary));
            }
        }
    }
}
