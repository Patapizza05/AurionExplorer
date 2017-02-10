package fr.clementduployez.aurionexplorer.Fragments.Birthdays.AsyncTasks;

import android.os.AsyncTask;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.BirthdaysResponse;
import fr.clementduployez.aurionexplorer.Fragments.Birthdays.BirthdayFragment;
import fr.clementduployez.aurionexplorer.Model.BirthdayList;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;

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
        if (data == null || data.isEmpty()) {
            Informer.getInstance().inform(AurionApi.Messages.CONNECTION_FAIL);
        }
        this.birthdayFragment.onAsyncResult(data);
    }
}