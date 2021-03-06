package fr.clementduployez.aurionexplorer.Ui.Adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.yarolegovich.wellsql.core.Identifiable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.clementduployez.aurionexplorer.Fragments.Abstract.AurionPageFragment;
import fr.clementduployez.aurionexplorer.Ui.Holders.AurionHolder;

/**
 * Created by cdupl on 3/12/2016.
 */
public abstract class AurionAdapter<Holder extends AurionHolder<Info>, Info extends Identifiable> extends RecyclerView.Adapter<Holder>  {

    private final AurionPageFragment<Info> mFragment;
    private List<Info> data;

    public AurionAdapter(List<Info> data, AurionPageFragment<Info> fragment)
    {
        this.mFragment = fragment;
        setData(data);
    }

    public void setSubtitle(String text) {
        ((AppCompatActivity) (this.getFragment().getActivity())).getSupportActionBar().setSubtitle(text);
    }

    public void updateSubtitle() {
        int size = getItemCount();
        if (size == 1) {
            setSubtitle(size + " Élément");
        }
        else {
            setSubtitle(size + " Éléments");
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Info> data) {
        setData(data, getComparator());
    }

    private void setData(List<Info> data, Comparator comparator) {
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

    public List<Info> getData() {
        return data;
    }

    public Comparator getComparator() {
        return null;
    }

    public AurionPageFragment<Info> getFragment() {
        return mFragment;
    }
}
