package fr.clementduployez.aurionexplorer.Login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

import fr.clementduployez.aurionexplorer.Informer;
import fr.clementduployez.aurionexplorer.MainActivity;
import fr.clementduployez.aurionexplorer.R;
import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;
import fr.clementduployez.aurionexplorer.Utils.AurionCookies;
import fr.clementduployez.aurionexplorer.Utils.UserData;

/**
 * Created by cdupl on 2/12/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText username;
    private EditText password;
    private CheckBox stayLoggedIn;
    private Button confirmButton;

    private boolean wait = false;
    private View loginLayout;
    private View loadingLayout;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.container = (LinearLayout) findViewById(R.id.login_container);
        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);
        this.stayLoggedIn = (CheckBox) findViewById(R.id.stayLoggedIn_checkbox);
        this.confirmButton = (Button) findViewById(R.id.confirm_button);
        Informer.rootView = container;
        loginLayout = findViewById(R.id.loginLayout);
        loadingLayout = findViewById(R.id.loadingLayout);

        this.confirmButton.setOnClickListener(this);

        checkStoredCredentials();
    }

    private void checkStoredCredentials() {
        String username = UserData.getUsername();
        String password = UserData.getPassword();
        if (username != null && password != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        else {
            AurionCookies.clear();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (!wait && v == confirmButton) {
            String id = this.username.getText().toString();
            String pwd = this.password.getText().toString();
            UserData.setStayLoggedIn(this.stayLoggedIn.isChecked());
            this.connect(id,pwd);
        }
    }

    public void setWait(boolean wait) {
        this.wait = wait;
        if (wait) {
            this.loginLayout.setVisibility(View.GONE);
            this.loadingLayout.setVisibility(View.VISIBLE);
        }
        else {
            this.loadingLayout.setVisibility(View.GONE);
            this.loginLayout.setVisibility(View.VISIBLE);
        }
    }

    public void acceptLogin(String username, String password) {
        Log.i("Login", "Credentials accepted");

        UserData.saveUsername(username);
        UserData.savePassword(password);

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void rejectLogin() {
        Informer.inform("Erreur de connexion");
        Log.i("Login","Wrong credentials");
        AurionCookies.clear();
        setWait(false);
    }

    private void connect(String username, String password) {
        Log.i("Login","trying to connect...");
        setWait(true);

        new AsyncTask<String,Void,Boolean>() {

            private String password;
            private String username;

            @Override
            protected Boolean doInBackground(String... params) {
                wait = true;
                this.username = params[0];
                this.password = params[1];
                Connection.Response response = AurionBrowser.login(params[0],params[1]);
                if (response == null) {
                    return false;
                }

                boolean result = !response.url().toString().startsWith(AurionBrowser.LOGIN_URL);
                if (result) {
                    Document document = null;
                    try {
                        document = response.parse();
                        UserData.saveName(document.getElementsByClass("login").get(0).html().replaceAll("&nbsp;", "").trim());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(Boolean isCorrect) {
                super.onPostExecute(isCorrect);
                if (isCorrect) {
                    acceptLogin(this.username, this.password);
                }
                else {
                    rejectLogin();
                }

            }
        }.execute(username,password);
    }
}
