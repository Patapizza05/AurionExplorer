package fr.clementduployez.aurionexplorer;

import android.app.Application;
import android.content.Context;

/**
 * Created by cdupl on 2/14/2016.
 */
public class AurionExplorerApplication extends Application {

    private static Context sContext;

    public void onCreate(){
        super.onCreate();

        // Keep a reference to the application context
        sContext = getApplicationContext();
    }

    // Used to access Context anywhere within the app
    public static Context getContext() {
        return sContext;
    }
}
