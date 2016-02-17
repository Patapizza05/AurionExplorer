package fr.clementduployez.aurionexplorer.Utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by cdupl on 2/17/2016.
 */
public class CasBrowser {

    private static boolean login() {
        Connection.Response pageResponse = AurionBrowser.homePage();
        if (pageResponse != null) {
            String url = pageResponse.url().toString();
            if (url.startsWith(AurionBrowser.LOGIN_URL))
            {
                return AurionBrowser.login(pageResponse) != null;
            }
        }
        return false;
    }

    public static Connection.Response connectToStaffDirectory(String lastName, String firstName, String code) {
        if (login())
        {
            HashMap<String,String> data = new HashMap<>();
            data.put("statut","Y");
            data.put("dataNom",lastName);
            data.put("dataPrenom",firstName);
            data.put("dataCode",code);
            try {
                Connection.Response response = Jsoup.connect("https://cas.isen.fr/home/annuaire/infos-staff.html")
                        .userAgent(AurionBrowser.USER_AGENT)
                        .followRedirects(true)
                        .header("Content-Type", AurionBrowser.CONTENT_TYPE)
                        .cookies(AurionCookies.cookies)
                        .data(data)
                        .execute();

                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*public static Connection.Response connectToStudentDirectory() {

    }

    public static Connection.Response connectToBirthday()*/


}
