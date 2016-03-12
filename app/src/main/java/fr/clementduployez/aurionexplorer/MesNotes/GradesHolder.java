package fr.clementduployez.aurionexplorer.MesNotes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/14/2016.
 */
public class GradesHolder extends RecyclerView.ViewHolder {


    private final TextView date;
    private final TextView title;
    private final TextView id;
    private final TextView value;

    public GradesHolder(View itemView) {
        super(itemView);
        this.date = (TextView) itemView.findViewById(R.id.mark_date);
        this.title = (TextView) itemView.findViewById(R.id.mark_title);
        this.id = (TextView) itemView.findViewById(R.id.mark_id);
        this.value = (TextView) itemView.findViewById(R.id.mark_value);
    }

    public void bind(GradesInfo gradesInfo) {
        date.setText(gradesInfo.getDate());
        title.setText(gradesInfo.getTitle());
        id.setText(gradesInfo.getGradeId());
        setValue(gradesInfo.getValue());
    }

    private void setValue(String strValue) {
        this.value.setText(strValue);
        /*int n = Math.round(Float.valueOf(strValue.trim()))*5;
        int r = 255 - (255 * n) / 100;
        int g = 255 - (255 * (100 - n)) / 100;
        int b = 0;
        int argb = Color.argb(255,r,g,b);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(argb); // Changes this drawbale to use a single color instead of a gradient
        gd.setStroke(2, argb);
        this.value.setBackground(gd);
        this.value.setTextColor(argb);*/
    }
}
