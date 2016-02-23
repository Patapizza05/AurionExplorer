package fr.clementduployez.aurionexplorer.Fortinet;

import android.os.AsyncTask;

import org.jsoup.Connection;

import fr.clementduployez.aurionexplorer.Utils.FortinetBrowser;

/**
 * Created by cdupl on 2/23/2016.
 */
public class FortinetAuthenticationAsync extends AsyncTask<Void,Void,Boolean> {

    private final FortinetFragment fortinetFragment;

    public FortinetAuthenticationAsync(FortinetFragment fortinetFragment) {
        this.fortinetFragment = fortinetFragment;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return FortinetBrowser.connectToFortinet();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        this.fortinetFragment.onAsyncResult(aBoolean);
    }
}
