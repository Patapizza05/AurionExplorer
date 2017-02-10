package fr.clementduployez.aurionexplorer.Utils.OldApi;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;

import fr.clementduployez.aurionexplorer.Api.Cookies.AurionCookies;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;
import fr.clementduployez.aurionexplorer.Settings.Settings;

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

    private static Connection.Response connectToStaffFormPage() {
        Informer.getInstance().inform("Connexion à la page de l'annuaire du staff");
        try {
            Connection.Response response = Jsoup.connect("https://cas.isen.fr/home/annuaire/staff.html")
                    .userAgent(AurionBrowser.USER_AGENT)
                    .timeout(Settings.Api.CONNECTION_TIMEOUT)
                    .followRedirects(true)
                    .header("Content-Type", AurionBrowser.CONTENT_TYPE)
                    .cookies(AurionCookies.get())
                    .execute();
            AurionCookies.addAll(response.cookies());
            Informer.getInstance().inform("Fin du chargement de la page de l'annuaire du staff");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection.Response connectToStaffDirectory(String lastName, String firstName, String code) {
        Informer.getInstance().inform("Envoi du formulaire de recherche dans l'annuaire");
        login();
        if (connectToStaffFormPage() != null)
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
                        .timeout(Settings.Api.CONNECTION_TIMEOUT)
                        .referrer("https://cas.isen.fr/home/annuaire/staff.html")
                        .header("Content-Type", AurionBrowser.CONTENT_TYPE)
                        .cookies(AurionCookies.get())
                        .data(data)
                        .method(Connection.Method.POST)
                        .execute();
                Informer.getInstance().inform("Fin de la recherche dans l'annuaire");
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Informer.getInstance().inform("Erreur...");
        return null;
    }

    /*public static Connection.Response connectToStudentDirectory() {

    }*/

    public static Connection.Response connectToBirthday() {
        Informer.getInstance().inform("Chargement des anniversaires");
        login();


        try {
            Connection.Response response = Jsoup.connect("https://cas.isen.fr/home/annuaire/anniversaries.html")
                    .userAgent(AurionBrowser.USER_AGENT)
                    .followRedirects(true)
                    .timeout(Settings.Api.CONNECTION_TIMEOUT)
                    .referrer("https://cas.isen.fr/home/annuaire/anniversaries.html")
                    .header("Content-Type", AurionBrowser.CONTENT_TYPE)
                    .cookies(AurionCookies.get())
                    .method(Connection.Method.POST)
                    .execute();
            Informer.getInstance().inform("Chargement effectué");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Informer.getInstance().inform("Erreur...");
        return null;
    }


}
