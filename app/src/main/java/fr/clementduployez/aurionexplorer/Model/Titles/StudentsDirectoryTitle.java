package fr.clementduployez.aurionexplorer.Model.Titles;

import android.app.Fragment;

import fr.clementduployez.aurionexplorer.Fragments.Directory.Students.StudentsDirectoryFragment;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by Clement on 12/02/2017.
 */

public class StudentsDirectoryTitle extends Title {

    private StudentsDirectoryFragment fragment = null;

    public StudentsDirectoryTitle() {
        super(Settings.Titles.STUDENTS_DIRECTORY, R.drawable.ic_group_red_500_18dp);
    }

    @Override
    public Fragment getFragment() {
        if (fragment == null) {
            fragment = new StudentsDirectoryFragment();
        }
        return fragment;
    }
}
