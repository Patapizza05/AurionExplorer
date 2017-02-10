package fr.clementduployez.aurionexplorer.Fragments.Directory.Students.AsyncTasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.StudentsResponse;
import fr.clementduployez.aurionexplorer.Fragments.Directory.Students.StudentsDirectoryFragment;
import fr.clementduployez.aurionexplorer.Model.StudentInfo;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;

/**
 * Created by Clement on 10/02/2017.
 */

public class SearchStudentsAsync extends AsyncTask<Void, Void, List<StudentInfo>> {
    private final String lastName;
    private final String firstName;
    private final String group;
    private StudentsDirectoryFragment studentsDirectoryFragment;

    public SearchStudentsAsync(StudentsDirectoryFragment studentsDirectoryFragment, String lastName, String firstName, String group) {
        this.studentsDirectoryFragment = studentsDirectoryFragment;
        this.lastName = lastName;
        this.firstName = firstName;
        this.group = group;
    }

    @Override
    protected List<StudentInfo> doInBackground(Void... params) {
        StudentsResponse response = new AurionApi().students(this.lastName, this.firstName, this.group);
        if (response == null || response.getData() == null) {
            return new ArrayList<>();
        }
        return response.getData();
    }

    @Override
    protected void onPostExecute(List<StudentInfo> staffData) {
        super.onPostExecute(staffData);

        if (staffData != null && !staffData.isEmpty()) {
            Informer.getInstance().inform(AurionApi.Messages.STAFF_SUCCESS);
        } else {
            Informer.getInstance().inform(AurionApi.Messages.CONNECTION_FAIL);
        }

        this.studentsDirectoryFragment.onAsyncResult(staffData);
    }
}
