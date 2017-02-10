package fr.clementduployez.aurionexplorer.Utils.OldApi;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;
import fr.clementduployez.aurionexplorer.Api.Cookies.AurionCookies;
import fr.clementduployez.aurionexplorer.Api.Utils.JSoupUtils;
import fr.clementduployez.aurionexplorer.Settings.Settings;
import fr.clementduployez.aurionexplorer.Settings.UserData;

/**
 * Created by cdupl on 2/13/2016.
 */
public class AurionBrowser {

    public static final String AURION_URL = "https://aurion-lille.isen.fr";
    public static final String MAIN_MENU_PAGE_URL = "https://aurion-lille.isen.fr/faces/MainMenuPage.xhtml";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36";
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String LOGIN_URL = "https://cas.isen.fr/login";
    public static final String USERNAME_INPUT_NAME = "username";
    public static final String PASSWORD_INPUT_NAME = "password";

    protected static Connection.Response homePage() {
        Informer.getInstance().inform("Chargement de la page d'accueil en cours");
        Connection.Response result = null;
        try {
            result = Jsoup.connect(AURION_URL)
                    .timeout(Settings.Api.CONNECTION_TIMEOUT)
                    .userAgent(USER_AGENT)
                    .followRedirects(true)
                    .header("Content-Type", CONTENT_TYPE)
                    .cookies(AurionCookies.get())
                    .execute();
            AurionCookies.addAll(result.cookies());
        } catch (IOException e) {
            Informer.getInstance().inform("Erreur pendant le chargement de la page d'accueil");
            e.printStackTrace();
        }

        return result;
    }

    public static Connection.Response login() {
        return login(UserData.getUsername(),UserData.getPassword());
    }

    public static Connection.Response login(String username, String password) {
        return login(homePage(),username,password);
    }

    public static Connection.Response login(Connection.Response loginPageResponse) {
        return login(loginPageResponse,UserData.getUsername(),UserData.getPassword());
    }

    private static Connection.Response login(Connection.Response loginPageResponse,String username, String password) {
        if (loginPageResponse == null) {
            return null;
        }

        Informer.getInstance().inform("Identification en cours");

        if (!loginPageResponse.url().toString().startsWith(LOGIN_URL))
        {

            return null;
        }

        Connection.Response result;
        Map<String, String> data = JSoupUtils.getHiddenInputData(loginPageResponse);

        data.put(USERNAME_INPUT_NAME, username);
        data.put(PASSWORD_INPUT_NAME, password);

        try {
            result = Jsoup.connect(loginPageResponse.url().toString())
                    .header("Content-Type", CONTENT_TYPE)
                    .timeout(Settings.Api.CONNECTION_TIMEOUT)
                    .userAgent(USER_AGENT)
                    .data(data)
                    .method(Connection.Method.POST)
                    .cookies(AurionCookies.get())
                    .execute();

            AurionCookies.addAll(result.cookies());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Informer.getInstance().inform("Erreur pendant la phase d'identification");
        }
        return null;
    }

    public static Connection.Response connectToPage(String title) {
        return connectToPage(title, (HashMap<String, String>)null);
    }

    public static Connection.Response connectToPage(String title, HashMap<String, String> data) {
        Connection.Response pageResponse = homePage();
        if (pageResponse != null) {
            String url = pageResponse.url().toString();
            if (url.startsWith(LOGIN_URL))
            {
                return connectToPage(title,login(pageResponse), data);
            }
            return connectToPage(title,pageResponse, data);
        }
        return null;
    }

    private static Connection.Response connectToPage(String title, Connection.Response loggedInPageResponse, HashMap<String, String> customData)
    {
        Document aurionDocument;
        try {
            aurionDocument = loggedInPageResponse.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Informer.getInstance().inform("Connexion à la page \""+title+"\" en cours");

        Map<String,String> data = JSoupUtils.getHiddenInputData(loggedInPageResponse);
        if (customData != null) {
            data.putAll(customData);
        }

        JSoupUtils.addValueToData(title, aurionDocument, data);

        try {
            Connection.Response result = Jsoup.connect(MAIN_MENU_PAGE_URL)
                    .header("Content-Type", CONTENT_TYPE)
                    .timeout(Settings.Api.CONNECTION_TIMEOUT)
                    .userAgent(USER_AGENT)
                    .referrer(AURION_URL)
                    .cookies(AurionCookies.get())
                    .data(data)
                    .method(Connection.Method.POST)
                    .execute();

            AurionCookies.addAll(result.cookies());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Informer.getInstance().inform("Erreur pendant la connexion à la page \""+title+"\"");
        }
        return null;
    }

    public static Connection.Response connectToNextPage(Connection.Response previousPage, HashMap<String, String> customData) {

        Informer.getInstance().inform("Chargement de la page suivante");

        Log.i("Last url:",previousPage.url().toString());

        Map<String,String> data = JSoupUtils.getHiddenInputData(previousPage);
        if (customData != null) {
            data.putAll(customData);
        }

        data.put("javax.faces.source","form:haut");
        data.put("javax.faces.partial.event","rich:datascroller:onscroll");
        data.put("javax.faces.partial.execute","form:haut");
        data.put("javax.faces.partial.render","");
        data.put("form:haut:page","next"); //Next page
        data.put("org.richfaces.ajax.component","form:haut");
        data.put("form:haut","form:haut");
        data.put("rfExt","null");
        data.put("AJAX:EVENT_COUNT","1");
        data.put("javax.faces.partial.ajax","true");

        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }

        try {
            Connection.Response result = Jsoup.connect(previousPage.url().toString())
                    .header("Content-Type", CONTENT_TYPE)
                    .timeout(Settings.Api.CONNECTION_TIMEOUT)
                    .userAgent(USER_AGENT)
                    .referrer(AURION_URL)
                    .cookies(AurionCookies.get())
                    .data(data)
                    .method(Connection.Method.POST)
                    .execute();

            AurionCookies.addAll(result.cookies());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Informer.getInstance().inform("Erreur pendant le chargement de la page suivante");
        }        return null;
    }
}
