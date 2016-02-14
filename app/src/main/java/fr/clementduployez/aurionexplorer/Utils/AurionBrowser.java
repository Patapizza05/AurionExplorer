package fr.clementduployez.aurionexplorer.Utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

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

    private static Connection.Response homePage() {
        Connection.Response result = null;
        try {
            result = Jsoup.connect(AURION_URL)
                    .userAgent(USER_AGENT)
                    .followRedirects(true)
                    .header("Content-Type", CONTENT_TYPE)
                    .cookies(AurionCookies.cookies)
                    .execute();
            AurionCookies.cookies = result.cookies();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static Connection.Response login() {
        return login(homePage());
    }

    private static Connection.Response login(Connection.Response loginPageResponse) {
        if (loginPageResponse == null) {
            return null;
        }

        if (!loginPageResponse.url().toString().startsWith(LOGIN_URL))
        {
            return null;
        }

        Connection.Response result = null;
        Map<String, String> data = JSoupUtils.getHiddenInputData(loginPageResponse);

        data.put(USERNAME_INPUT_NAME, "user");
        data.put(PASSWORD_INPUT_NAME, "pwd");

        try {
            result = Jsoup.connect(loginPageResponse.url().toString())
                    .header("Content-Type", CONTENT_TYPE)
                    .userAgent(USER_AGENT)
                    .data(data)
                    .method(Connection.Method.POST)
                    .cookies(AurionCookies.cookies)
                    .execute();

            AurionCookies.cookies.putAll(result.cookies());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection.Response connectToPage(String title) {
        Connection.Response pageResponse = homePage();
        String url = pageResponse.url().toString();
        if (url.startsWith(LOGIN_URL))
        {
            return connectToPage(title,login(pageResponse));
        }
        return connectToPage(title,pageResponse);
    }

    private static Connection.Response connectToPage(String title, Connection.Response loggedInPageResponse)
    {
        Document aurionDocument = null;
        try {
            aurionDocument = loggedInPageResponse.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Map<String,String> data = JSoupUtils.getHiddenInputData(loggedInPageResponse);
        JSoupUtils.addLinkValueToData(title, aurionDocument, data);

        try {
            Connection.Response result = Jsoup.connect(MAIN_MENU_PAGE_URL) //
                    .header("Content-Type", CONTENT_TYPE)
                    .userAgent(USER_AGENT)
                    .referrer(AURION_URL)
                    .cookies(AurionCookies.cookies)
                    .data(data)
                    //.data("form:largeurDivCenter","1660")
                    .method(Connection.Method.POST)
                    .execute();

            AurionCookies.cookies.putAll(result.cookies());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
