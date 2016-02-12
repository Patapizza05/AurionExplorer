package fr.clementduployez.aurionexplorer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Created by cdupl on 2/12/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText username;
    private EditText password;
    private CheckBox stayLoggedIn;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);
        this.stayLoggedIn = (CheckBox) findViewById(R.id.stayLoggedIn_checkbox);
        this.confirmButton = (Button) findViewById(R.id.confirm_button);

        this.confirmButton.setOnClickListener(this);
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onClick(View v) {
        if (v == confirmButton) {
            String id = this.username.getText().toString();
            String pwd = this.password.getText().toString();

            //this.connect(id,pwd);

            if (this.stayLoggedIn.isChecked()) {
                //Save data
            }
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    private boolean connect(String username, String password) {
        return false;
    }
}
