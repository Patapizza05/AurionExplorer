package fr.clementduployez.aurionexplorer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import fr.clementduployez.aurionexplorer.Annuaire.Staff.StaffDirectoryFragment;
import fr.clementduployez.aurionexplorer.Login.LoginActivity;
import fr.clementduployez.aurionexplorer.MesAbsences.AbsencesFragment;
import fr.clementduployez.aurionexplorer.MesConferences.ConferencesFragment;
import fr.clementduployez.aurionexplorer.MesNotes.GradesFragment;
import fr.clementduployez.aurionexplorer.MonPlanning.CalendarFragment;
import fr.clementduployez.aurionexplorer.Utils.UserData;

//Drawer Builder : http://android-arsenal.com/details/1/1526

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private HamburgerMenuManager hamburgerMenuManager;
    private FrameLayout container;
    private Fragment currentFragment;
    private RelativeLayout rootView;

    private GradesFragment mGradesFragment;
    private CalendarFragment mCalendarFragment;
    private StaffDirectoryFragment mStaffDirectoryFragment;
    private AbsencesFragment mNotImplementedFragment;
    private ConferencesFragment mConferencesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = (RelativeLayout) findViewById(R.id.mainActivityContainer);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        container = (FrameLayout)findViewById(R.id.frame_container);

        Informer.rootView = this.rootView;

        this.setSupportActionBar(toolbar);
        this.hamburgerMenuManager = new HamburgerMenuManager(this);

        openFragmentWithName(this.hamburgerMenuManager.getSelectedItemTitle());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_disconnect) {
            UserData.clear();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private void openFragment(Fragment fragment) {
        if (this.currentFragment == fragment)
        {
            return;
        }

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment != null) {
            if (this.currentFragment != null) {
                transaction.remove(this.currentFragment);
            }
            transaction.add(R.id.frame_container, fragment);
            this.currentFragment = fragment;
            transaction.commit();
        }
    }


    public void openFragmentWithName(String selectedItemTitle) {
        switch(selectedItemTitle) {
            case "Mes Notes":
                if (mGradesFragment == null) {
                    mGradesFragment = GradesFragment.newInstance();
                }
                openFragment(mGradesFragment);
                break;
            case "Mon Planning":
                if (mCalendarFragment == null) {
                    mCalendarFragment = CalendarFragment.newInstance();
                }
                openFragment(mCalendarFragment);
                break;
            case "Annuaire du Staff":
                if (mStaffDirectoryFragment == null) {
                    mStaffDirectoryFragment = StaffDirectoryFragment.newInstance();
                }
                openFragment(mStaffDirectoryFragment);
                break;
            case "Mes Conférences":
                if (mConferencesFragment == null) {
                    mConferencesFragment = ConferencesFragment.newInstance();
                }
                openFragment(mConferencesFragment);
                break;
            default:
                if (mNotImplementedFragment == null) {
                    mNotImplementedFragment = AbsencesFragment.newInstance();
                }
                openFragment(mNotImplementedFragment);
                break;
        }
    }
}
