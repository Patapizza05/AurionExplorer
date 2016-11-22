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

import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.StaffDirectoryFragment;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;
import fr.clementduployez.aurionexplorer.Model.StaffInfo;
import fr.clementduployez.aurionexplorer.Utils.OldApi.CasBrowser;

/**
 * Created by cdupl on 2/21/2016.
 */
public class SearchStaffAsync extends AsyncTask<Void,Void,ArrayList<StaffInfo>> {
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
    protected ArrayList<StaffInfo> doInBackground(Void... params) {
        Connection.Response response = CasBrowser.connectToStaffDirectory(lastName,firstName,code);
        ArrayList<StaffInfo> staffData = null;
        if (response != null && response.statusCode() == 200) {
            Log.i("Staff","OK !");
            try {
                staffData = parseStaffInfo(response);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (staffData == null) {
            staffData = new ArrayList<>();
        }
        return staffData;
    }

    private ArrayList<StaffInfo> parseStaffInfo(Connection.Response response) throws IOException {
        ArrayList<StaffInfo> staffData = new ArrayList<>();
        Document document = response.parse();
        Elements elements = document.body().children();

        if (elements == null || elements.size() <= 0) {
            return staffData;
        }

        for (Element e : elements) {
            try {
            String name = e.getElementsByTag("h3").get(0).html().trim();
            Log.i("StaffInfo","Name : "+name);

            Elements p = e.getElementsByTag("p");
            String code = p.get(0).html().split(":")[1].trim();
            Log.i("StaffInfo","Code : "+code);
            String email = p.get(1).getElementsByTag("a").get(0).html().trim();
            Log.i("StaffInfo","Email : "+email);
            String phone = p.get(2).text().split(":")[1].trim();
            Log.i("StaffInfo","Phone : "+phone);
            String office = p.get(3).html().split(":")[1].trim();
            Log.i("StaffInfo", "Office : " + office);
            ArrayList<String> lessons = new ArrayList<>();

            try {
                String lessonsString = p.get(5).html();
                if (!lessonsString.isEmpty()) {
                    if (lessonsString.contains("<br>")) {
                        lessons.addAll(Arrays.asList(lessonsString.split("<br>")));
                    }
                    else {
                        lessons.add(lessonsString);
                    }
                }
            } catch(IndexOutOfBoundsException ex) {
                //Nothing to add
            }

            staffData.add(new StaffInfo(name,code,email,phone,office,lessons));
            }
            catch (Exception ex) {
                //Parsing error or nothing to add
            }
        }
        return staffData;
    }

    @Override
    protected void onPostExecute(ArrayList<StaffInfo> staffData) {
        super.onPostExecute(staffData);
        Informer.inform("Récupération des informations du staff terminée.");
        this.staffDirectoryFragment.onAsyncResult(staffData);
    }
}
