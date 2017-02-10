package fr.clementduployez.aurionexplorer.Fragments.Directory.Students.Holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.AurionExplorerApplication;
import fr.clementduployez.aurionexplorer.Model.StaffInfo;
import fr.clementduployez.aurionexplorer.Model.StudentInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by Clement on 10/02/2017.
 */

public class StudentHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final LinearLayout weightSumContainer;
    private final ImageView image;
    private final View birthdayContainer;
    private final TextView birthday;
    private final View emailContainer;
    private final TextView email;
    private final View promoContainer;
    private final TextView promo;
    private final View specializationContainer;
    private final TextView specialization;

    private static int imagePadding = -1;

    public StudentHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.directory_student_title);
        weightSumContainer = (LinearLayout) itemView.findViewById(R.id.directory_student_weight_container);
        image = (ImageView) itemView.findViewById(R.id.directory_student_image);
        birthdayContainer = itemView.findViewById(R.id.directory_student_birthday_container);
        birthday =  (TextView) itemView.findViewById(R.id.directory_student_birthday);
        emailContainer = itemView.findViewById(R.id.directory_student_email_container);
        email =  (TextView) itemView.findViewById(R.id.directory_student_email);
        promoContainer = itemView.findViewById(R.id.directory_student_promo_container);
        promo =  (TextView) itemView.findViewById(R.id.directory_student_promo);
        specializationContainer = itemView.findViewById(R.id.directory_student_specialization_container);
        specialization =  (TextView) itemView.findViewById(R.id.directory_student_specialization);

        if (imagePadding == -1) imagePadding = this.image.getPaddingTop();
    }

    public void bind(StudentInfo studentInfo) {
        title.setText(studentInfo.getName());
        display(studentInfo.getBirthday(), birthdayContainer, birthday);
        display(studentInfo.getEmail(), emailContainer, email);
        display(studentInfo.getPromo(), promoContainer, promo);
        display(studentInfo.getSpecialization(), specializationContainer, specialization);
        setImageUrl(studentInfo.getImageUrl());
    }

    private void display(String text, View container, TextView textView) {
        if (text != null) {
            container.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
        else {
            container.setVisibility(View.GONE);
        }
    }

    private void setImageUrl(String url) {
        Context ctx = AurionExplorerApplication.getContext();

        if (url != null && url.startsWith("http")) {
            Picasso.with(ctx)
                    .load(url)
                    .into(this.image);
            this.image.setVisibility(View.VISIBLE);
            this.weightSumContainer.setWeightSum(7f);
        }
        else {
            this.image.setVisibility(View.GONE);
            this.weightSumContainer.setWeightSum(6f);
        }


    }
}
