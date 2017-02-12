package fr.clementduployez.aurionexplorer.Model.Titles;

import android.app.Fragment;

import fr.clementduployez.aurionexplorer.Fragments.Conferences.ConferencesFragment;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by Clement on 12/02/2017.
 */

public class MyConferencesTitle extends Title {

    ConferencesFragment fragment = null;

    public MyConferencesTitle() {
        super(Settings.Titles.MY_CONFERENCES, R.drawable.ic_record_voice_over_red_500_18dp);
    }

    @Override
    public Fragment getFragment() {
        if (fragment == null) {
            fragment = ConferencesFragment.newInstance();
        }
        return fragment;
    }
}
