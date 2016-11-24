package fr.clementduployez.aurionexplorer.Utils.Inform;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import java.text.MessageFormat;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class Informer {

    private static Informer instance;

    public static Informer getInstance() {
        if (instance == null) instance = new Informer();
        return instance;
    }

    private ViewGroup container;

    public void inform(String message) {
//        if (rootView != null){
//            Snackbar snack = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
//            /*ViewGroup group = (ViewGroup) snack.getView();
//            TextView tv = (TextView) group.findViewById(android.support.design.R.id.snackbar_text);
//            tv.setTextColor(AurionExplorerApplication.getContext().getResources().getColor(R.color.colorPrimary));
//            group.setBackgroundColor(AurionExplorerApplication.getContext().getResources().getColor(R.color.white));*/
//            snack.show();
//        }


        if (container != null) {
            final Snackbar snack = Snackbar.make(container, message, Snackbar.LENGTH_LONG);
            snack.setAction("Fermer", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snack.dismiss();
                }
            });
            snack.setActionTextColor(container.getContext().getResources().getColor(R.color.colorPrimary));

            snack.show();
        }
    }

    public void inform(String format, Object... params) {
        inform(MessageFormat.format(format, params));
    }

    public void initInformer(final ViewGroup container) {
        this.container = container;
    }
}
