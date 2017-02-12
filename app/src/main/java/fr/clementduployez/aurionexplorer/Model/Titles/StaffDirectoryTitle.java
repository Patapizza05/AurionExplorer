package fr.clementduployez.aurionexplorer.Model.Titles;

import android.app.Fragment;

import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.StaffDirectoryFragment;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by Clement on 12/02/2017.
 */

public class StaffDirectoryTitle extends Title {

    private StaffDirectoryFragment fragment = null;

    public StaffDirectoryTitle() {
        super(Settings.Titles.STAFF_DIRECTORY, R.drawable.ic_work_red_500_18dp);
    }

    @Override
    public Fragment getFragment() {
        if (fragment == null) {
            fragment = new StaffDirectoryFragment();
        }
        return fragment;
    }
}