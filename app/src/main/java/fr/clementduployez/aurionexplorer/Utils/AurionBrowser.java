package fr.clementduployez.aurionexplorer.Utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Informer;

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
        Informer.inform("Chargement de la page d'accueil en cours");
        Connection.Response result = null;
        try {
            result = Jsoup.connect(AURION_URL)
                    .userAgent(USER_AGENT)
                    .followRedirects(true)
                    .header("Content-Type", CONTENT_TYPE)
                    .cookies(AurionCookies.cookies)
                    .execute();
            AurionCookies.cookies.putAll(result.cookies());
        } catch (IOException e) {
            Informer.inform("Erreur pendant le chargement de la page d'accueil");
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

        Informer.inform("Identification en cours");

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
                    .timeout(10000)
                    .userAgent(USER_AGENT)
                    .data(data)
                    .method(Connection.Method.POST)
                    .cookies(AurionCookies.cookies)
                    .execute();

            AurionCookies.cookies.putAll(result.cookies());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Informer.inform("Erreur pendant la phase d'identification");
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Informer.inform("Connexion à la page \""+title+"\" en cours");

        Map<String,String> data = JSoupUtils.getHiddenInputData(loggedInPageResponse);
        if (customData != null) {
            data.putAll(customData);
        }

        JSoupUtils.addLinkValueToData(title, aurionDocument, data);

        try {
            Connection.Response result = Jsoup.connect(MAIN_MENU_PAGE_URL) //
                    .header("Content-Type", CONTENT_TYPE)
                    .userAgent(USER_AGENT)
                    .referrer(AURION_URL)
                    .cookies(AurionCookies.cookies)
                    .data(data)
                    .method(Connection.Method.POST)
                    .execute();

            AurionCookies.cookies.putAll(result.cookies());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Informer.inform("Erreur pendant la connexion à la page \""+title+"\"");
        }
        return null;
    }
}