package fr.clementduployez.aurionexplorer.Fragments.Birthdays;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.clementduployez.aurionexplorer.Fragments.Abstract.AurionPageFragment;
import fr.clementduployez.aurionexplorer.Fragments.Birthdays.Adapters.BirthdayPagerAdapter;
import fr.clementduployez.aurionexplorer.Fragments.Birthdays.AsyncTasks.LoadBirthdaysAsync;
import fr.clementduployez.aurionexplorer.Model.BirthdayList;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 3/15/2016.
 */
public class BirthdayFragment extends AurionPageFragment<BirthdayList> implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BirthdayPagerAdapter pagerAdapter;
    private LoadBirthdaysAsync loadBirthdaysAsync;
    private boolean isFirstTime = true;

    private static boolean isFirstLoad = true;

    public static BirthdayFragment newInstance() {
        final BirthdayFragment birthdayFragment = new BirthdayFragment();
        return birthdayFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_birthdays, container, false);
        tabLayout = (TabLayout)rootView.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        initViewPagerAndTabs();
        if (isFirstTime) {
            onRefreshAsync();
            isFirstTime = false;
        }
        return rootView;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initData() {

    }



    private void initViewPagerAndTabs() {

        tabLayout.addTab(tabLayout.newTab().setText("Aujourd'hui"));
        tabLayout.addTab(tabLayout.newTab().setText("Ce mois-ci"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);




        if (pagerAdapter == null)
        {
            pagerAdapter = new BirthdayPagerAdapter(this);
        }


        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdapter.getItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    public BirthdayPagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }



    @Override
    public void showProgressBar() {
        pagerAdapter.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        pagerAdapter.hideProgressBar();
    }

    @Override
    public void onRefreshAsync() {
        if (loadBirthdaysAsync == null) {
            loadBirthdaysAsync = new LoadBirthdaysAsync(this);
            loadBirthdaysAsync.execute();
            showProgressBar();
        } else {
            hideProgressBar();
        }
    }

    @Override
    public void onAsyncResult(List<BirthdayList> data) {
        onAsyncResult(data.get(0));
    }

    public void onAsyncResult(BirthdayList data) {
        setAdapter(data);
        hideProgressBar();
        loadBirthdaysAsync = null;
    }

    @Override
    public void setAdapter(List<BirthdayList> data) {
        setAdapter(data.get(0));
    }

    public void setAdapter(BirthdayList data) {
        pagerAdapter.setAdapters(data);
    }

    @Override
    public void onRefresh() {
        onRefreshAsync();
    }
}
