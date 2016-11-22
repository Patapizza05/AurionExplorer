package fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.Model.StaffInfo;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/17/2016.
 */
public class StaffHolder extends RecyclerView.ViewHolder {
    private final ExpandableTextView lessons;
    private final TextView code;
    private final TextView email;
    private final TextView phone;
    private final TextView office;
    private final TextView title;

    public StaffHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.staff_directory_title);
        code = (TextView) itemView.findViewById(R.id.staff_directory_code);
        email = (TextView) itemView.findViewById(R.id.staff_directory_email);
        phone = (TextView) itemView.findViewById(R.id.staff_directory_phone);
        office = (TextView) itemView.findViewById(R.id.staff_directory_office);
        lessons = (ExpandableTextView) itemView.findViewById(R.id.staff_directory_lessons);
    }

    public void bind(StaffInfo staffInfo) {

        title.setText(staffInfo.getName());
        code.setText(staffInfo.getCode());
        email.setText(staffInfo.getEmail());
        phone.setText(staffInfo.getPhone());
        office.setText(staffInfo.getOffice());
        String lessonText = "";

        ArrayList<String> staffInfoLessons = staffInfo.getLessons();
        int size = staffInfoLessons.size();
        for (int i = 0; i < size-1; i++)
        {
            lessonText += staffInfoLessons.get(i) + "\n";
        }
        if (size > 0) {
            lessonText += staffInfoLessons.get(staffInfoLessons.size()-1);
        }

        lessons.setText(lessonText);
    }
}
