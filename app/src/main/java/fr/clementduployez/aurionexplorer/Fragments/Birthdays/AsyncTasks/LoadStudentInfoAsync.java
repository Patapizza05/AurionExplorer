package fr.clementduployez.aurionexplorer.Fragments.Birthdays.AsyncTasks;

import android.os.AsyncTask;

import java.util.List;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.StudentsResponse;
import fr.clementduployez.aurionexplorer.Model.StudentInfo;
import fr.clementduployez.aurionexplorer.Utils.Callback;

/**
 * Created by cdupl on 11/22/2016.
 */

public class LoadStudentInfoAsync extends AsyncTask<String, Void, StudentInfo> {

    private final Callback<StudentInfo> callback;
    private final String lastName;
    private final String firstName;

    public LoadStudentInfoAsync(Callback<StudentInfo> callback, String lastName, String firstName) {
        this.callback = callback;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    protected StudentInfo doInBackground(String... strings) {
        StudentsResponse response = AurionApi.getInstance().students(lastName, firstName, null);
        if (response != null && response.getData() != null) {
            return response.getData().get(0);
        }
        return null;
    }

    @Override
    protected void onPostExecute(StudentInfo data) {
        callback.run(data);
    }
}
