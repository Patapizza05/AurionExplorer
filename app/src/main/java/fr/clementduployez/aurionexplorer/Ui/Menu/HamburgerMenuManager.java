package fr.clementduployez.aurionexplorer.Ui.Menu;

import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.Activities.MainActivity;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Settings.Settings;
import fr.clementduployez.aurionexplorer.Settings.UserData;

/**
 * Created by cdupl on 2/12/2016.
 */
public class HamburgerMenuManager {

    private static final int DEFAULT_SELECTED_ITEM = 3; //My planning

    private final MainActivity activity;
    private final AccountHeader header;
    private final Drawer drawer;

    private String name = UserData.getName();
    private String id = UserData.getUsername();

    public static final int MY_GRADES_INDEX = 1;
    public static final int MY_PLANNING_INDEX = 3;
    public static final int MY_CONFERENCES_INDEX = 5;
    public static final int STUDENTS_DIRECTORY_INDEX = 7;
    public static final int STAFF_DIRECTORY_INDEX = 8;
    public static final int BIRTHDAYS_INDEX = 9;

    private String[] titles =
            {
                    Settings.Titles.MY_GRADES,
                    Settings.Titles.MY_ABSENCES,
                    Settings.Titles.MY_PLANNING,
                    Settings.Titles.DELIMITER, // ---
                    Settings.Titles.MY_CONFERENCES,
                    Settings.Titles.DELIMITER, // ---
                    Settings.Titles.STUDENTS_DIRECTORY,
                    Settings.Titles.STAFF_DIRECTORY,
                    Settings.Titles.BIRTHDAYS
            };

    private Integer[] titlesImages =
            {R.drawable.ic_school_red_500_18dp,
                    R.drawable.ic_alarm_off_red_500_18dp,
                    R.drawable.ic_today_red_500_18dp,
                    null,
                    R.drawable.ic_record_voice_over_red_500_18dp,
                    null,
                    R.drawable.ic_group_red_500_18dp,
                    R.drawable.ic_work_red_500_18dp,
                    R.drawable.ic_cake_red_500_18dp,
            };

    private ArrayList<IDrawerItem> items = new ArrayList<>(3);

    public HamburgerMenuManager(MainActivity activity, Integer selectItem) {
        this.activity = activity;
        initDrawerItems();
        this.header = initAccountHeader();
        this.drawer = initDrawer(this.header, selectItem);
    }

    private void initDrawerItems() {
        for (int i = 0; i < titles.length; i++) {
            if (titles[i] != null) {
                if (!Settings.Titles.DELIMITER.equals(titles[i])) {
                    if (titlesImages[i] != null) {
                        items.add(new PrimaryDrawerItem().withName(titles[i]).withIcon(titlesImages[i]));
                    } else {
                        items.add(new PrimaryDrawerItem().withName(titles[i]));
                    }
                } else {
                    items.add(new DividerDrawerItem());
                }
            }
        }
    }

    private AccountHeader initAccountHeader() {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this.activity)
                //.withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(name)
                                .withEmail(id)
                                .withIcon(R.drawable.user)
                )
                .withSelectionListEnabled(false)
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .withCompactStyle(true)
                .withHeaderBackground(R.color.colorPrimary)
                .withTextColorRes(R.color.white)
                .build();

        return headerResult;
    }

    private Drawer initDrawer(AccountHeader header, Integer selectItem) {
        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this.activity)
                .withRootView(R.id.drawer_container)
                .withToolbar(activity.getToolbar())
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(header)
                .withCloseOnClick(true)
                .addDrawerItems(items.toArray(new IDrawerItem[items.size()]))
                .withSelectedItemByPosition(selectItem != null ? selectItem : DEFAULT_SELECTED_ITEM)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem item) {
                        drawer.closeDrawer();
                        activity.openFragmentWithName(getSelectedItemTitle());
                        //Do something
                        return true;
                    }
                })
                .build();

        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        return result;
    }

    public String getSelectedItemTitle() {
        return this.titles[this.drawer.getCurrentSelectedPosition() - 1];
    }
}
