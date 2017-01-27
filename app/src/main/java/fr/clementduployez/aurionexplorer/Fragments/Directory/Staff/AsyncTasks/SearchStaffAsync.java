package fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.Responses.StaffResponse;
import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.StaffDirectoryFragment;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;
import fr.clementduployez.aurionexplorer.Model.StaffInfo;
import fr.clementduployez.aurionexplorer.Utils.OldApi.CasBrowser;

/**
 * Created by cdupl on 2/21/2016.
 */
public class SearchStaffAsync extends AsyncTask<Void,Void,List<StaffInfo>> {
    private final String lastName;
    private final String firstName;
    private final String code;
    private StaffDirectoryFragment staffDirectoryFragment;

    public SearchStaffAsync(StaffDirectoryFragment staffDirectoryFragment, String lastName, String firstName, String code) {
        this.staffDirectoryFragment = staffDirectoryFragment;
        this.lastName = lastName;
        this.firstName = firstName;
        this.code = code;
    }

    @Override
    protected List<StaffInfo> doInBackground(Void... params) {
        StaffResponse response = new AurionApi().staff("Y", this.lastName, this.firstName, this.code);
        if (response == null || response.getData() == null) {
            return new ArrayList<>();
        }
        return response.getData();
    }

    @Override
    protected void onPostExecute(List<StaffInfo> staffData) {
        super.onPostExecute(staffData);

        if (staffData != null && !staffData.isEmpty()) {
            Informer.getInstance().inform(AurionApi.Messages.STAFF_SUCCESS);
        } else {
            Informer.getInstance().inform(AurionApi.Messages.CONNECTION_FAIL);
        }

        this.staffDirectoryFragment.onAsyncResult(staffData);
    }
}
