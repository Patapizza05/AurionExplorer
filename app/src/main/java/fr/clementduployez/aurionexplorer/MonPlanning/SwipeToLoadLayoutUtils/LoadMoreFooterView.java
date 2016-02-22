package fr.clementduployez.aurionexplorer.MonPlanning.SwipeToLoadLayoutUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by cdupl on 2/22/2016.
 */
public class LoadMoreFooterView extends TextView implements SwipeTrigger, SwipeLoadMoreTrigger {
    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
        setText("Chargement en cours");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onSwipe(int yScrolled, boolean isComplete) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
                setText("Relâchez pour charger la semaine suivante");
            } else {
                setText("Tirez pour charger la semaine suivante");
            }
        } else {
            setText("Chargement terminé");
        }
    }

    @Override
    public void onRelease() {
        setText("Chargement en cours");
    }

    @Override
    public void complete() {
        setText("Chargement terminé");
    }

    @Override
    public void onReset() {
        setText("");
    }
}
