package fr.clementduployez.aurionexplorer.Fragments.Birthdays.Adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.clementduployez.aurionexplorer.Fragments.Birthdays.BirthdayFragment;
import fr.clementduployez.aurionexplorer.Fragments.Birthdays.Holders.BirthdayHolder;
import fr.clementduployez.aurionexplorer.Model.BirthdayInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 3/30/2016.
 */
public class BirthdaysAdapter extends RecyclerView.Adapter<BirthdayHolder> {

    private final BirthdayFragment fragment;
    private List<BirthdayInfo> data;

    public BirthdaysAdapter(List<BirthdayInfo> data,BirthdayFragment fragment) {
        this.data = data;
        this.fragment = fragment;
    }

    @Override
    public BirthdayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_birthday_item, parent, false);
        return new BirthdayHolder(view);
    }

    @Override
    public void onBindViewHolder(BirthdayHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateSubtitle() {
        int size = getItemCount();
        if (size == 1) {
            setSubtitle(getItemCount() + " Anniversaire");
        }
        else {
            setSubtitle(getItemCount() + " Anniversaires");
        }
    }

    public void setSubtitle(String text) {
        ((AppCompatActivity) (this.fragment.getActivity())).getSupportActionBar().setSubtitle(text);
    }

    public void setData(List<BirthdayInfo> data) {
        setData(data, null);
    }

    private void setData(List<BirthdayInfo> data, Comparator comparator) {
        if (data == null) {
            return;
        }

        this.data = data;
        if (comparator != null) {
            Collections.sort(this.data, comparator);
        }
        updateSubtitle();
        notifyDataSetChanged();
    }
}
