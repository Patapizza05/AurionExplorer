package fr.clementduployez.aurionexplorer.MonPlanning;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.androflo.sectionedrecyclerviewadapter.Sectionizer;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarHolder> implements Sectionizer<CalendarInfo> {

    private ArrayList<CalendarInfo> data;

    public CalendarAdapter(ArrayList<CalendarInfo> data) {
        setData(data);
    }

    @Override
    public CalendarHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_recycler_item, parent, false);
        return new CalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarHolder holder, int i) {
        //holder.bind(data.get(position));
        holder.bind(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<CalendarInfo> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public String getSectionTitle(CalendarInfo s) {
        return s.getDay();
    }
}
