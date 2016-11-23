package fr.clementduployez.aurionexplorer.Fragments.Birthdays.AsyncTasks;

import android.os.AsyncTask;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.StudentsResponse;
import fr.clementduployez.aurionexplorer.Exceptions.InvalidNameException;
import fr.clementduployez.aurionexplorer.Model.StudentInfo;
import fr.clementduployez.aurionexplorer.Utils.Callback;

/**
 * Created by cdupl on 11/22/2016.
 */

public class LoadStudentInfoAsync extends AsyncTask<String, Void, StudentInfo> {

    private final Callback<StudentInfo> callback;
    private String lastName;
    private String firstName;

    public LoadStudentInfoAsync(String name, Callback<StudentInfo> callback) throws InvalidNameException {
        this.callback = callback;
        if (!initName_firstLast(name) && !initName_lastFirst(name)) {
            throw new InvalidNameException(name);
        }
    }

    private boolean initName_firstLast(String name) {
        Pattern r = Pattern.compile("[^a-z]+$");
        Matcher m = r.matcher(name);
        if (m.find()) {
            this.lastName = m.group(0).trim();
            this.firstName = name.replace(this.lastName, "").trim();
            return true;
        }
        else {
            return false;
        }
    }

    private boolean initName_lastFirst(String name) {
        Pattern r = Pattern.compile("^[^a-z]+");
        Matcher m = r.matcher(name);
        if (m.find()) {
            this.lastName = m.group(0).trim();
            this.firstName = name.replace(lastName, "").trim();
            return true;
        }

        return false;
    }

    public LoadStudentInfoAsync(String lastName, String firstName, Callback<StudentInfo> callback) {
        this.callback = callback;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    protected StudentInfo doInBackground(String... strings) {
        StudentsResponse response = AurionApi.getInstance().students(lastName, firstName, null);
        if (response != null && response.getData() != null && response.getData().size() > 0) {
            return response.getData().get(0);
        }
        return null;
    }

    @Override
    protected void onPostExecute(StudentInfo data) {
        callback.run(data);
    }
}
