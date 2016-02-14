package fr.clementduployez.aurionexplorer.MesNotes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/14/2016.
 */
public class MarksHolder extends RecyclerView.ViewHolder {


    private final TextView date;
    private final TextView title;
    private final TextView id;
    private final TextView value;

    public MarksHolder(View itemView) {
        super(itemView);
        this.date = (TextView) itemView.findViewById(R.id.mark_date);
        this.title = (TextView) itemView.findViewById(R.id.mark_title);
        this.id = (TextView) itemView.findViewById(R.id.mark_id);
        this.value = (TextView) itemView.findViewById(R.id.mark_value);
    }

    public void bind(MarksInfo marksInfo) {
        date.setText(marksInfo.getDate());
        title.setText(marksInfo.getTitle());
        id.setText(marksInfo.getId());
        value.setText(marksInfo.getValue());
    }
}
