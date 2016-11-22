package fr.clementduployez.aurionexplorer;

import android.app.Application;
import android.content.Context;

import com.yarolegovich.wellsql.WellSql;

import fr.clementduployez.aurionexplorer.Utils.SQL.MySQLWellConfig;

/**
 * Created by cdupl on 2/14/2016.
 */
public class AurionExplorerApplication extends Application {

    public static final int APP_VERSION = 1;

    private static Context sContext;

    public void onCreate(){
        super.onCreate();

        // Keep a reference to the application context
        sContext = getApplicationContext();
        WellSql.init(new MySQLWellConfig(sContext));
    }

    // Used to access Context anywhere within the app
    public static Context getContext() {
        return sContext;
    }
}
