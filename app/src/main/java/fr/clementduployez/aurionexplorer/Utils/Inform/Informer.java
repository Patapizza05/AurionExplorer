package fr.clementduployez.aurionexplorer.Utils.Inform;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.MessageFormat;

/**
 * Created by cdupl on 2/17/2016.
 */
public class Informer {

    public static View rootView = null;

    public static void inform(String message) {
        if (rootView != null){
            Snackbar snack = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
            /*ViewGroup group = (ViewGroup) snack.getView();
            TextView tv = (TextView) group.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(AurionExplorerApplication.getContext().getResources().getColor(R.color.colorPrimary));
            group.setBackgroundColor(AurionExplorerApplication.getContext().getResources().getColor(R.color.white));*/

            snack.show();
        }
    }

    public static void inform(String format, Object... params) {
        inform(MessageFormat.format(format, params));
    }
}
