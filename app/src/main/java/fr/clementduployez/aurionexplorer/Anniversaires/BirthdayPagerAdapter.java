package fr.clementduployez.aurionexplorer.Anniversaires;

import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.NotImplemented.NotImplementedFragment;

/**
 * Created by cdupl on 3/15/2016.
 */
public class BirthdayPagerAdapter extends FragmentPagerAdapter {

    private BirthdayList birthdayList = null;
    private BirthdayPeriodFragment monthlyFragment = null;
    private BirthdayPeriodFragment dailyFragment = null;

    private static final int NB_TABS = 2;
    private static final int DAILY = 0;
    private static final int MONTHLY = 1;

    private BirthdayFragment mBirthdayFragment;
    private boolean isShowProgressBar = false;

    public BirthdayPagerAdapter(BirthdayFragment mBirthdayFragment) {
        super(mBirthdayFragment.getChildFragmentManager());
        this.mBirthdayFragment = mBirthdayFragment;
    }

    @Override
    public Fragment getItem(int i) {

        if (birthdayList == null) {
            birthdayList = new BirthdayList();
            birthdayList.setDailyBirthdays(new ArrayList<BirthdayInfo>());
            birthdayList.setMonthlyBirthdays(new ArrayList<BirthdayInfo>());
        }

        switch(i)
        {
            case DAILY:
                if (dailyFragment == null) {
                    dailyFragment = BirthdayPeriodFragment.newInstance(this.mBirthdayFragment, birthdayList.getDailyBirthdays());
                }
                dailyFragment.setForeground();
                if (isShowProgressBar) {
                    showProgressBar();
                }
                return dailyFragment;
            case MONTHLY:
                if (monthlyFragment == null) {
                    monthlyFragment = BirthdayPeriodFragment.newInstance(this.mBirthdayFragment, birthdayList.getMonthlyBirthdays());
                }
                monthlyFragment.setForeground();
                if (isShowProgressBar) {
                    showProgressBar();
                }
                return monthlyFragment;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NB_TABS;
    }

    public void setAdapters(BirthdayList data) {
        birthdayList = data;
        if (dailyFragment != null) {
            dailyFragment.update(birthdayList.getDailyBirthdays());
        }
        if (monthlyFragment != null) {
            monthlyFragment.update(birthdayList.getMonthlyBirthdays());
        }

    }

    public void showProgressBar() {
        if (monthlyFragment != null) {
            monthlyFragment.showProgressBar();
        }
        if (dailyFragment != null) {
            dailyFragment.showProgressBar();
        }
        this.isShowProgressBar = true;
    }

    public void hideProgressBar() {
        if (monthlyFragment != null) {
            monthlyFragment.hideProgressBar();
        }

        if (dailyFragment != null) {
            dailyFragment.hideProgressBar();
        }
        this.isShowProgressBar = false;
    }
}
