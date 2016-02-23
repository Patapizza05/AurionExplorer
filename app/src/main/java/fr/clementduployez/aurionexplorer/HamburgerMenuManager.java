package fr.clementduployez.aurionexplorer;

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

import fr.clementduployez.aurionexplorer.Utils.UserData;

/**
 * Created by cdupl on 2/12/2016.
 */
public class HamburgerMenuManager {

    private final MainActivity activity;
    private final AccountHeader header;
    private final Drawer drawer;

    private String name = UserData.getName();
    private String id = UserData.getUsername();

    private String[] titles = {"Mes Notes", "Mes Absences", "Mon Planning",null,"Mes Conférences", null,"Annuaire des Étudiants","Annuaire du Staff","Anniversaire"};
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

    public HamburgerMenuManager(MainActivity activity) {
        this.activity = activity;
        initDrawerItems();
        this.header = initAccountHeader();
        this.drawer = initDrawer(this.header);
    }

    private void initDrawerItems() {
        for (int i = 0; i < titles.length; i++) {
            if (titles[i] != null) {
                if (titlesImages[i] != null) {
                    items.add(new PrimaryDrawerItem().withName(titles[i]).withIcon(titlesImages[i]));
                }
                else {
                    items.add(new PrimaryDrawerItem().withName(titles[i]));
                }
            }
            else {
                items.add(new DividerDrawerItem());
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
                .build();

        headerResult.setBackgroundRes(R.color.colorPrimary);
        return headerResult;
    }

    private Drawer initDrawer(AccountHeader header) {
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
                .withSelectedItemByPosition(3)
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
        return this.titles[this.drawer.getCurrentSelectedPosition()-1];
    }
}
