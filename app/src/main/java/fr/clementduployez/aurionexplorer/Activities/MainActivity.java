package fr.clementduployez.aurionexplorer.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import fr.clementduployez.aurionexplorer.Fragments.Birthdays.BirthdayFragment;
import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.StaffDirectoryFragment;
import fr.clementduployez.aurionexplorer.Fragments.Directory.Students.StudentsDirectoryFragment;
import fr.clementduployez.aurionexplorer.Fragments.Fortinet.FortinetFragment;
import fr.clementduployez.aurionexplorer.Ui.Menu.HamburgerMenuManager;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;
import fr.clementduployez.aurionexplorer.Fragments.Grades.Receivers.GradesAlarmReceiver;
import fr.clementduployez.aurionexplorer.Fragments.NotImplemented.NotImplementedFragment;
import fr.clementduployez.aurionexplorer.Fragments.Conferences.ConferencesFragment;
import fr.clementduployez.aurionexplorer.Fragments.Grades.GradesFragment;
import fr.clementduployez.aurionexplorer.Fragments.Planning.CalendarFragment;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.SQL.SQLUtils;
import fr.clementduployez.aurionexplorer.Settings.Settings;
import fr.clementduployez.aurionexplorer.Settings.UserData;

//Drawer Builder : http://android-arsenal.com/details/1/1526

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    private Toolbar toolbar;
    private HamburgerMenuManager hamburgerMenuManager;
    private ViewGroup container;
    private ViewGroup rootView;

    private GradesFragment mGradesFragment;
    private CalendarFragment mCalendarFragment;
    private StaffDirectoryFragment mStaffDirectoryFragment;
    private NotImplementedFragment mNotImplementedFragment;
    private ConferencesFragment mConferencesFragment;
    private FortinetFragment mFortinetFragment;
    private BirthdayFragment mBirthdayFragment;
    private StudentsDirectoryFragment mStudentsDirectoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = (ViewGroup) findViewById(R.id.mainActivityContainer);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        container = (ViewGroup)findViewById(R.id.frame_container);

        Informer.getInstance().initInformer(container);

        this.setSupportActionBar(toolbar);
        this.hamburgerMenuManager = new HamburgerMenuManager(this);
    }

    protected void onResume() {
        super.onResume();
        openFragmentWithName(this.hamburgerMenuManager.getSelectedItemTitle());
    }

    private void startGradesUpdaterService(boolean isChecked) {
        /*Intent startIntent = new Intent(MainActivity.this, GradesUpdaterService.class);
        startService(startIntent);*/
        //saveData("runService", true);

        if (!Settings.LITE && isChecked) {
            GradesAlarmReceiver.startAlarm(getApplicationContext());
        }
        else {
            GradesAlarmReceiver.stopAlarm(getApplicationContext());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_refresh_grades_service);
        if (Settings.LITE) {
            item.setVisible(false);
        }
        else {
            item.setChecked(UserData.isRefreshGradesService());
            startGradesUpdaterService(item.isChecked());
        }
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
            SQLUtils.clear();
            UserData.clear();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_refresh_grades_service) {
            item.setChecked(!item.isChecked());
            UserData.saveRefreshGradesService(item.isChecked());
            startGradesUpdaterService(item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private void openFragment(Fragment fragment) {
        if (getCurrentFragment() == fragment) return;
        final FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment, FRAGMENT_TAG);
        transaction.commit();
    }


    public void openFragmentWithName(String selectedItemTitle) {
        switch(selectedItemTitle) {
            case Settings.Titles.MY_GRADES:
                if (mGradesFragment == null) {
                    mGradesFragment = GradesFragment.newInstance();
                }
                openFragment(mGradesFragment);
                break;
            case Settings.Titles.MY_PLANNING:
                if (mCalendarFragment == null) {
                    mCalendarFragment = CalendarFragment.newInstance();
                }
                openFragment(mCalendarFragment);
                break;
            case Settings.Titles.STAFF_DIRECTORY:
                if (mStaffDirectoryFragment == null) {
                    mStaffDirectoryFragment = StaffDirectoryFragment.newInstance();
                }
                openFragment(mStaffDirectoryFragment);
                break;
            case Settings.Titles.MY_CONFERENCES:
                if (mConferencesFragment == null) {
                    mConferencesFragment = ConferencesFragment.newInstance();
                }
                openFragment(mConferencesFragment);
                break;
            /*case "Fortinet":
                if (mFortinetFragment == null) {
                    mFortinetFragment = FortinetFragment.newInstance();
                }
                openFragment(mFortinetFragment);
                break;*/
            case Settings.Titles.BIRTHDAYS:
                //if (mBirthdayFragment == null) { //FIXME : Crashes on second visit
                    mBirthdayFragment = BirthdayFragment.newInstance();
                //}
                openFragment(mBirthdayFragment);
                break;
            case Settings.Titles.STUDENTS_DIRECTORY:
                if (mStudentsDirectoryFragment == null) {
                    mStudentsDirectoryFragment = StudentsDirectoryFragment.newInstance();
                }
                openFragment(mStudentsDirectoryFragment);
                break;
            default:
                if (mNotImplementedFragment == null) {
                    mNotImplementedFragment = NotImplementedFragment.newInstance();
                }
                openFragment(mNotImplementedFragment);
                break;
        }
    }

    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }
}
