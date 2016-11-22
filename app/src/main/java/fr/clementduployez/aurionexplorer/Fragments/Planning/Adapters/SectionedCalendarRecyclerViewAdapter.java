package fr.clementduployez.aurionexplorer.Fragments.Planning.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.androflo.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import com.github.androflo.sectionedrecyclerviewadapter.Sectionizer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
                /*view.setBackgroundResource(R.color.colorPrimary);
                view.setTextColor(Color.WHITE);*/
                view.setTypeface(null, Typeface.BOLD);


            } else {
                /*view.setBackgroundResource(R.color.transparent);
                view.setTextColor(AurionExplorerApplication.getContext().getResources().getColor(R.color.black));*/
                view.setTypeface(null, Typeface.NORMAL);
            }
        }
    }
}
