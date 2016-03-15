package fr.clementduployez.aurionexplorer.Anniversaires;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 3/15/2016.
 */
public class BirthdayFragment extends Fragment {

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BirthdayPagerAdapter pagerAdapter;

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
        return rootView;
    }



    private void initViewPagerAndTabs() {

        tabLayout.addTab(tabLayout.newTab().setText("Aujourd'hui"));
        tabLayout.addTab(tabLayout.newTab().setText("Ce mois-ci"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        Log.i("Manager",""+this.getFragmentManager());
        Log.i("CManager",""+this.getChildFragmentManager());

        pagerAdapter = new BirthdayPagerAdapter(this);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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

}
