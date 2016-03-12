package fr.clementduployez.aurionexplorer;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cdupl on 3/12/2016.
 */
public abstract class AurionHolder<T> extends RecyclerView.ViewHolder {


    public AurionHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T info);
}
