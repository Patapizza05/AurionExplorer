package fr.clementduployez.aurionexplorer.Fragments.Birthdays.Holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;
import fr.clementduployez.aurionexplorer.Exceptions.InvalidNameException;
import fr.clementduployez.aurionexplorer.Fragments.Birthdays.AsyncTasks.LoadStudentInfoAsync;
import fr.clementduployez.aurionexplorer.Model.StudentInfo;
import fr.clementduployez.aurionexplorer.Ui.Holders.AurionHolder;
import fr.clementduployez.aurionexplorer.Model.BirthdayInfo;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.Callback;

/**
 * Created by cdupl on 3/22/2016.
 */
public class BirthdayHolder extends AurionHolder<BirthdayInfo> implements View.OnClickListener {

    private final View container;
    private final TextView name;
    private final ImageView image;
    private BirthdayInfo birthdayInfo;

    private static int imagePadding = -1;
    private static int textPadding = -1;

    public BirthdayHolder(View itemView) {
        super(itemView);
        this.container = itemView.findViewById(R.id.birthday_item_container);
        this.name = (TextView) itemView.findViewById(R.id.birthday_item_name);
        this.image = (ImageView) itemView.findViewById(R.id.birthday_item_image);
        if (imagePadding == -1) imagePadding = this.image.getPaddingTop();
        if (textPadding == -1) textPadding = this.name.getPaddingTop();
        this.container.setOnClickListener(this);
    }

    @Override
    public void bind(BirthdayInfo info) {
        this.birthdayInfo = info;
        this.name.setText(info.getName());
        this.setImageUrl(info.getImageUrl());
        //TODO : Picasso + retrieve student image
    }

    private void setImageUrl(String url) {
        Context ctx = AurionExplorerApplication.getContext();

        if (url != null && url.startsWith("http")) {
            Picasso.with(ctx)
                    .load(url)
                    .into(this.image);
            this.image.setPadding(0,0,0,0);
            this.name.setPadding(textPadding, textPadding*2, textPadding, textPadding*2);
        }
        else {
            Picasso.with(ctx)
                    .load(R.drawable.ic_cake_red_500_18dp)
                    .into(this.image);
            this.image.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
            this.name.setPadding(textPadding, textPadding, textPadding, textPadding);
        }


    }

    @Override
    public void onClick(View view) {
        final BirthdayInfo info = this.birthdayInfo;
        if (info.getImageUrl() != null) return;

        try {
            new LoadStudentInfoAsync(this.birthdayInfo.getName(), new Callback<StudentInfo>() {

                @Override
                public void run(StudentInfo data) {
                    if (data == null) {
                        setImageUrl(null);
                        return;
                    }

                    if (info == birthdayInfo) {
                        setImageUrl(data.getImageUrl());
                    }
                }
            }).execute();
        } catch (InvalidNameException e) {
            e.printStackTrace();
        }
    }

}
