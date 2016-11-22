package fr.clementduployez.aurionexplorer.Fragments.Birthdays.AsyncTasks;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.BirthdaysResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.StudentsResponse;
import fr.clementduployez.aurionexplorer.Fragments.Birthdays.BirthdayFragment;
import fr.clementduployez.aurionexplorer.Model.BirthdayList;
import fr.clementduployez.aurionexplorer.Model.BirthdayInfo;
import fr.clementduployez.aurionexplorer.Utils.OldApi.CasBrowser;

/**
 * Created by cdupl on 3/22/2016.
 */
public class LoadBirthdaysAsync extends AsyncTask<Void,BirthdayList,BirthdayList> {

    private final BirthdayFragment birthdayFragment;

    public LoadBirthdaysAsync(BirthdayFragment birthdayFragment) {
        this.birthdayFragment = birthdayFragment;
    }

    @Override
    protected BirthdayList doInBackground(Void... params) {
        BirthdaysResponse response = AurionApi.getInstance().birthdays();
        return response != null ? response.getBirthdayList() : new BirthdayList();
    }

    @Override
    protected void onPostExecute(BirthdayList data) {
        super.onPostExecute(data);
        this.birthdayFragment.onAsyncResult(data);
    }
}