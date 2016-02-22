package fr.clementduployez.aurionexplorer.MonPlanning.SwipeToLoadLayoutUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by cdupl on 2/22/2016.
 */
public class RefreshHeaderView extends TextView implements SwipeRefreshTrigger, SwipeTrigger {

    public RefreshHeaderView(Context context) {
        super(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onRefresh() {
        setText("Chargement en cours");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onSwipe(int yScrolled, boolean isComplete) {
        if (!isComplete) {
            if (yScrolled >= getHeight()) {
                setText("Relâchez pour charger la semaine précédente");
            } else {
                setText("Tirez pour charger la semaine précédente");
            }
        } else {
            setText("Chargement terminé");
        }
    }

    @Override
    public void onRelease() {
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
