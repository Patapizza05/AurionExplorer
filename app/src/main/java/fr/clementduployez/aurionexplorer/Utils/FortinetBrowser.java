package fr.clementduployez.aurionexplorer.Utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import fr.clementduployez.aurionexplorer.Informer;

/**
 * Created by cdupl on 2/23/2016.
 */
public class FortinetBrowser {

    private static final String EXAMPLE_URL = "http://example.com";

    private static Connection.Response tryConnectToExampleSite() {
        Informer.inform("Connexion à "+EXAMPLE_URL);
        try {
            Connection.Response example = Jsoup.connect(EXAMPLE_URL)
                    .followRedirects(true)
                    .userAgent(AurionBrowser.USER_AGENT)
                    .timeout(Settings.CONNECTION_TIMEOUT)
                    .execute();
            return example;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean connectToFortinet() {
        Connection.Response example = tryConnectToExampleSite();

        if (example != null && example.url().toString().contains("fgauth")) {
            Map<String, String> data = JSoupUtils.getHiddenInputData(example);
            data.put("username",UserData.getUsername());
            data.put("password",UserData.getPassword());
            Informer.inform("Envoi du formulaire d'authentification");
            try {
                //Certificate error !
                Connection.Response fortinet = Jsoup.connect(example.url().toString())
                        .followRedirects(true)
                        .userAgent(AurionBrowser.USER_AGENT)
                        .timeout(Settings.CONNECTION_TIMEOUT)
                        .execute();
                if (fortinet.statusCode() == 200) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Informer.inform("L'authentification a échoué");
                return false;
            }
        }
        else {
            Informer.inform("Vous êtes peut-être déjà authentifié");
            return null;
        }

        return false;
    }
}
