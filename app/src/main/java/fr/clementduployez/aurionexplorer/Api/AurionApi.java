package fr.clementduployez.aurionexplorer.Api;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import fr.clementduployez.aurionexplorer.Api.Annotations.AurionAnnotations;
import fr.clementduployez.aurionexplorer.Api.Responses.IndexResponse;
import fr.clementduployez.aurionexplorer.Informer;
import fr.clementduployez.aurionexplorer.Utils.AurionCookies;
import fr.clementduployez.aurionexplorer.Utils.Settings;

/**
 * Created by cdupl on 11/21/2016.
 */

public class AurionApi implements IAurionApi {

    private Connection.Response jsoupConnect(AurionAnnotations annotations) {
        try {
            Connection connection = Jsoup.connect(annotations.getUrl())
                    .timeout(Settings.CONNECTION_TIMEOUT)
                    .userAgent(Settings.USER_AGENT)
                    .followRedirects(true)
                    .header("Content-Type", annotations.getContentType())
                    .cookies(AurionCookies.get());

            String referrer = annotations.getReferrer();
            if (referrer != null) {
                connection = connection.referrer(referrer);
            }

            return connection.execute();

        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean isLoggedIn(Connection.Response response) {
        return !response.url().toString().startsWith("https://cas.isen.fr/login");
    }

    @Override
    public IndexResponse Index() {
        Informer.inform(Messages.INDEX_START);
        AurionAnnotations annotations = AurionAnnotations.getInstance(this, "Index");
        Connection.Response result = jsoupConnect(annotations);
        if (result != null) {
            AurionCookies.addAll(result.cookies());

            if (!isLoggedIn(result)) {
                //Exception
                return null;
            }
            return new IndexResponse(result);
        }
        else {
            Informer.inform(Messages.INDEX_ERROR);
        }
        return null;
    }

    @Override
    public void LoginForm() {

    }

    @Override
    public void Login(String username, String password) {

    }

    @Override
    public void Planning() {

    }

    @Override
    public void Grades(int page) {

    }

    @Override
    public void Conferences(int page) {

    }

    @Override
    public void Birthdays() {

    }

    @Override
    public void StaffForm() {

    }

    @Override
    public void Staff(String status, String dataNom, String dataPrenom, String dataCode) {

    }

    private class Messages {
        public static final String INDEX_START = "Chargement de la page d'accueil en cours";
        public static final String INDEX_ERROR = "Erreur pendant le chargement de la page d'accueil";

    }
}
