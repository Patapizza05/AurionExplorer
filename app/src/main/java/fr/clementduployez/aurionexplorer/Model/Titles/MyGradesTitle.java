package fr.clementduployez.aurionexplorer.Model.Titles;

import android.app.Fragment;

import fr.clementduployez.aurionexplorer.Fragments.Grades.GradesFragment;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by Clement on 12/02/2017.
 */

public class MyGradesTitle extends Title {

    private GradesFragment fragment = null;

    public MyGradesTitle() {
        super(Settings.Titles.MY_GRADES, R.drawable.ic_school_red_500_18dp);
    }

    @Override
    public Fragment getFragment() {
        if (fragment == null) {
            fragment = GradesFragment.newInstance();
        }
        return fragment;
    }
}
