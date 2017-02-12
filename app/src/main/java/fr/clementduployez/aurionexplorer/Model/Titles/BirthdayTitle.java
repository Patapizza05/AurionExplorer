package fr.clementduployez.aurionexplorer.Model.Titles;

import android.app.Fragment;

import fr.clementduployez.aurionexplorer.Fragments.Birthdays.BirthdayFragment;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by Clement on 12/02/2017.
 */

public class BirthdayTitle extends Title {

    private BirthdayFragment fragment;

    public BirthdayTitle() {
        super(Settings.Titles.BIRTHDAYS, R.drawable.ic_cake_red_500_18dp);
    }

    @Override
    public Fragment getFragment() {
        return BirthdayFragment.newInstance();
    }
}
