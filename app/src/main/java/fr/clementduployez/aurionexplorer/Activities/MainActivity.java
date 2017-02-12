package fr.clementduployez.aurionexplorer.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = (ViewGroup) findViewById(R.id.mainActivityContainer);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        container = (ViewGroup)findViewById(R.id.frame_container);

        Informer.getInstance().initInformer(container);

        this.setSupportActionBar(toolbar);

        this.hamburgerMenuManager = new HamburgerMenuManager(this, getIntent());
    }



    protected void onResume() {
        super.onResume();
        openFragment(this.hamburgerMenuManager.getSelectedFragment());
    }

    private void startGradesUpdaterService(boolean isChecked) {
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

    public void onSelectedFragment(Fragment fragment) {
        if (fragment != null) {
            openFragment(fragment);
        }
        else {
            openFragment(NotImplementedFragment.newInstance());
        }
    }

    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }
}
