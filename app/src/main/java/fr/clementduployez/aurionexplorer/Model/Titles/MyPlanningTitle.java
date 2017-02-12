package fr.clementduployez.aurionexplorer.Model.Titles;

import android.app.Fragment;

import fr.clementduployez.aurionexplorer.Fragments.Grades.GradesFragment;
import fr.clementduployez.aurionexplorer.Fragments.Planning.CalendarFragment;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by Clement on 12/02/2017.
 */

public class MyPlanningTitle extends Title {

    private CalendarFragment fragment = null;

    public MyPlanningTitle() {
        super(Settings.Titles.MY_PLANNING, R.drawable.ic_today_red_500_18dp);
    }

    @Override
    public Fragment getFragment() {
        if (fragment == null) {
            fragment = CalendarFragment.newInstance();
        }
        return fragment;
    }

}
