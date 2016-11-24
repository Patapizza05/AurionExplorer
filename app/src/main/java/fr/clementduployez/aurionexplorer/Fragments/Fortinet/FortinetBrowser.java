package fr.clementduployez.aurionexplorer.Fragments.Fortinet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Api.Utils.JSoupUtils;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;
import fr.clementduployez.aurionexplorer.Settings.Settings;
import fr.clementduployez.aurionexplorer.Settings.UserData;
import fr.clementduployez.aurionexplorer.Utils.OldApi.AurionBrowser;

/**
 * Created by cdupl on 2/23/2016.
 */
public class FortinetBrowser {

    private static final String EXAMPLE_URL = "http://example.com";

    private static Connection.Response tryConnectToExampleSite() {
        Informer.getInstance().inform("Connexion à "+EXAMPLE_URL);
        try {
            Connection.Response example = Jsoup.connect(EXAMPLE_URL)
                    .followRedirects(true)
                    .userAgent(AurionBrowser.USER_AGENT)
                    .timeout(Settings.Api.CONNECTION_TIMEOUT)
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
            data.put("username", UserData.getUsername());
            data.put("password",UserData.getPassword());
            Informer.getInstance().inform("Envoi du formulaire d'authentification");
            try {
                //Certificate error !
                Connection.Response fortinet = Jsoup.connect(example.url().toString())
                        .followRedirects(true)
                        .userAgent(AurionBrowser.USER_AGENT)
                        .timeout(Settings.Api.CONNECTION_TIMEOUT)
                        .execute();
                if (fortinet.statusCode() == 200) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Informer.getInstance().inform("L'authentification a échoué");
                return false;
            }
        }
        else {
            Informer.getInstance().inform("Vous êtes peut-être déjà authentifié");
            return null;
        }

        return false;
    }
}
