package fr.clementduployez.aurionexplorer.Anniversaires;

import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;

import fr.clementduployez.aurionexplorer.NotImplemented.NotImplementedFragment;

/**
 * Created by cdupl on 3/15/2016.
 */
public class BirthdayPagerAdapter extends FragmentPagerAdapter {


    private static final int NB_TABS = 2;
    private static final int DAILY = 0;
    private static final int MONTHLY = 1;

    private BirthdayFragment mBirthdayFragment;

    public BirthdayPagerAdapter(BirthdayFragment mBirthdayFragment) {
        super(mBirthdayFragment.getFragmentManager());
        this.mBirthdayFragment = mBirthdayFragment;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case DAILY:
                return NotImplementedFragment.newInstance();
            case MONTHLY:
                return NotImplementedFragment.newInstance();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NB_TABS;
    }
}
