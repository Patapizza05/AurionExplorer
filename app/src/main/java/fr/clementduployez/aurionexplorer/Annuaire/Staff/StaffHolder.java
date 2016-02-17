package fr.clementduployez.aurionexplorer.Annuaire.Staff;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class StaffHolder extends RecyclerView.ViewHolder {
    private final ExpandableTextView expandableText;

    public StaffHolder(View itemView) {
        super(itemView);
        expandableText = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);
    }

    public void bind(StaffInfo staffInfo) {
        expandableText.setText("Module de Base Technologies et Système Microsoft 1\n" +
                "Cours d'Informatique pratique - CIR1 - Semestre 2\n" +
                "Cours d'Informatique théorique - CIR1 - Semestre 1\n" +
                "Cours d'Informatique théorique - CIR1 - Semestre 2\n" +
                "Module de Base Technologies Microsoft 2\n" +
                "Cours d'informatique pratique CIR3 - Semestre 1");
    }
}
