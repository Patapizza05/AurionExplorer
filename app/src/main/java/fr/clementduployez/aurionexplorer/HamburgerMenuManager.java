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

/**
 * Created by cdupl on 2/12/2016.
 */
public class HamburgerMenuManager {

    private final MainActivity activity;
    private final AccountHeader header;
    private final Drawer drawer;

    private String name = "Clément Duployez";
    private String id = "p58095";

    private String[] titles = {"Mes Notes", "Mes Absences", "Mon Planning",null,"Annuaire",null,"Fortinet"};
    private ArrayList<IDrawerItem> items = new ArrayList<>(3);

    public HamburgerMenuManager(MainActivity activity) {
        this.activity = activity;
        initDrawerItems();
        this.header = initAccountHeader();
        this.drawer = initDrawer(this.header);
    }

    private void initDrawerItems() {
        for(String title : titles) {
            if (title != null) {
                items.add(new PrimaryDrawerItem().withName(title));
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
                .withSelectedItemByPosition(0)
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
