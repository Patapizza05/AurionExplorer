package fr.clementduployez.aurionexplorer;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by cdupl on 2/17/2016.
 */
public class Informer {

    public static View rootView = null;

    public static void inform(String message) {
        if (rootView != null)
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }
}
