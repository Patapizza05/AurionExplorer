package fr.clementduployez.aurionexplorer.Anniversaires;

import android.view.View;
import android.widget.TextView;

import fr.clementduployez.aurionexplorer.AurionHolder;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 3/22/2016.
 */
public class BirthdayHolder extends AurionHolder<BirthdayInfo> {

    private final TextView name;

    public BirthdayHolder(View itemView) {
        super(itemView);
        this.name = (TextView) itemView.findViewById(R.id.birthday_item_name);
    }

    @Override
    public void bind(BirthdayInfo info) {
        this.name.setText(info.getName());
    }
}
