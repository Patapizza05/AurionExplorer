package fr.clementduployez.aurionexplorer.Ui.Menu;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import fr.clementduployez.aurionexplorer.Activities.MainActivity;
import fr.clementduployez.aurionexplorer.Model.Titles.BirthdayTitle;
import fr.clementduployez.aurionexplorer.Model.Titles.Delimiter;
import fr.clementduployez.aurionexplorer.Model.Titles.MyAbsencesTitle;
import fr.clementduployez.aurionexplorer.Model.Titles.MyConferencesTitle;
import fr.clementduployez.aurionexplorer.Model.Titles.MyGradesTitle;
import fr.clementduployez.aurionexplorer.Model.Titles.MyPlanningTitle;
import fr.clementduployez.aurionexplorer.Model.Titles.StaffDirectoryTitle;
import fr.clementduployez.aurionexplorer.Model.Titles.StudentsDirectoryTitle;
import fr.clementduployez.aurionexplorer.Model.Titles.Title;
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

    private Title[] titles = {
            new MyGradesTitle(), //1
            new MyAbsencesTitle(), //2
            new MyPlanningTitle(), //3
            new Delimiter(), //4
            new MyConferencesTitle(), //5
            new Delimiter(), //6
            new StudentsDirectoryTitle(), //7
            new StaffDirectoryTitle(), //8
            new BirthdayTitle() //9
    };

    private ArrayList<IDrawerItem> items = new ArrayList<>(3);

    public HamburgerMenuManager(MainActivity activity, Intent intent) {
        this.activity = activity;
        initDrawerItems();
        this.header = initAccountHeader();
        this.drawer = initDrawer(this.header, getItemToSelect(intent));
    }

    // Nougat shortcuts
    private Integer getItemToSelect(Intent intent) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            switch(action) {
                case Settings.IntentActions.GRADES:
                    return HamburgerMenuManager.MY_GRADES_INDEX;
                case Settings.IntentActions.PLANNING:
                    return HamburgerMenuManager.MY_PLANNING_INDEX;
                case Settings.IntentActions.STAFF_DIRECTORY:
                    return HamburgerMenuManager.STAFF_DIRECTORY_INDEX;
                case Settings.IntentActions.STUDENTS_DIRECTORY:
                    return HamburgerMenuManager.STUDENTS_DIRECTORY_INDEX;
                default:
                    break;
            }
        }
        return null;
    }

    private void initDrawerItems() {
        for (Title title : titles) {
            if (title != null) {
                items.add(title.createItem());
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
                        activity.onSelectedFragment(getSelectedFragment());
                        return true;
                    }
                })
                .build();

        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        return result;
    }


    public Fragment getSelectedFragment() {
        return this.titles[this.drawer.getCurrentSelectedPosition() - 1].getFragment();
    }
}
