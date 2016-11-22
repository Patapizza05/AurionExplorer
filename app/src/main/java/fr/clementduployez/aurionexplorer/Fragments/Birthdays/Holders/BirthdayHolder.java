package fr.clementduployez.aurionexplorer.Fragments.Birthdays.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.clementduployez.aurionexplorer.Fragments.Birthdays.Adapters.BirthdaysAdapter;
import fr.clementduployez.aurionexplorer.Ui.Holders.AurionHolder;
import fr.clementduployez.aurionexplorer.Model.BirthdayInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 3/22/2016.
 */
public class BirthdayHolder extends AurionHolder<BirthdayInfo> implements View.OnClickListener {

    private final TextView name;
    private final BirthdaysAdapter birthdaysAdapter;

    private int position;

    public BirthdayHolder(View itemView, BirthdaysAdapter birthdaysAdapter) {
        super(itemView);
        this.name = (TextView) itemView.findViewById(R.id.birthday_item_name);
        itemView.setOnClickListener(this);
        this.birthdaysAdapter = birthdaysAdapter;
    }

    public void bind(BirthdayInfo info, int position) {
        this.name.setText(info.getName());
        this.position = position;
        //TODO : Picasso + retrieve student image
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void bind(BirthdayInfo info) {
        bind(info, -1);
    }
}
