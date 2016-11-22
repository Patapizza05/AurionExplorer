package fr.clementduployez.aurionexplorer.Api;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Api.Annotations.AurionAnnotations;
import fr.clementduployez.aurionexplorer.Api.Responses.ConferencesResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.EmptyPlanningResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.GradesResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.IndexResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.LoginFormResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.LoginResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.PlanningResponse;
import fr.clementduployez.aurionexplorer.Utils.Inform.Informer;
import fr.clementduployez.aurionexplorer.Api.Cookies.AurionCookies;
import fr.clementduployez.aurionexplorer.Settings.Settings;
import fr.clementduployez.aurionexplorer.Settings.UserData;

/**
 * Created by cdupl on 11/21/2016.
 */

public class AurionApi implements IAurionApi {

    private static AurionApi instance = new AurionApi();

    public static AurionApi getInstance() {
        return instance;
    }

    @Override
    public IndexResponse index() {
        return index(true);
    }

    private IndexResponse index(boolean isFirstTry) {
        Informer.inform(Messages.INDEX_START);
        AurionAnnotations annotations = AurionAnnotations.getInstance("index", new Class[] {});
        Connection.Response result = jsoupConnect(annotations);

        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());

            if (!isLoggedIn(result)) {
                LoginFormResponse loginFormResponse = new LoginFormResponse(result);
                if (isFirstTry) {
                    relogin(loginFormResponse);
                    return index(false);
                }
            }
            return new IndexResponse(result);
        }
        else {
            Informer.inform(Messages.INDEX_ERROR);
        }
        return null;
    }

    @Override
    public LoginFormResponse loginForm() {

        AurionAnnotations annotations = AurionAnnotations.getInstance("loginForm", new Class[] {});
        Connection.Response result = jsoupConnect(annotations);

        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());
            return new LoginFormResponse(result);
        }
        return null;
    }

    @Override
    public LoginResponse relogin(LoginFormResponse loginFormResponse) {
        return login(loginFormResponse, UserData.getUsername(), UserData.getPassword());
    }

    @Override
    public LoginResponse login(String username, String password) {
        return login(loginForm(), username, password);
    }

    @Override
    public LoginResponse login(LoginFormResponse loginFormResponse, String username, String password) {
        AurionAnnotations annotations = AurionAnnotations.getInstance("login", new Class[] {LoginFormResponse.class, String.class, String.class });

        Map<String, String> data = loginFormResponse.getHiddenInputData();
        data.put(LoginFormResponse.USERNAME_INPUT_KEY, username);
        data.put(LoginFormResponse.PASSWORD_INPUT_KEY, password);
        data.put(LoginFormResponse.REDIRECT_KEY, Settings.Api.LOGIN_REDIRECT_URL);

        Connection.Response result = jsoupConnect(annotations, data);

        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies()); //Keep user logged in
            return new LoginResponse(result);
        }

        //In case of error, clear cookies to make sure user is disconnected
        AurionCookies.clear();
        return null;
    }

    @Override
    public EmptyPlanningResponse planning() {
        AurionAnnotations annotations = AurionAnnotations.getInstance("planning", new Class[] {});

        IndexResponse indexResponse = index();

        Informer.inform("Connexion à la page \""+ annotations.getTitle() +"\" en cours");

        Map<String, String> data = new HashMap<>();
        data.putAll(indexResponse.getHiddenInputData());
        try {
            data.putAll(PlanningResponse.prepareRequest(indexResponse, annotations.getTitle()));
        }
        catch(IOException ex) {
            //Can't parse document
            return null;
        }

        Connection.Response result = jsoupConnect(annotations, data);
        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());

            return new EmptyPlanningResponse(result);
        }

        return null;
    }

    @Override
    public PlanningResponse planning(Date beginDate, Date endDate) {
        AurionAnnotations annotations = AurionAnnotations.getInstance("planning", new Class[] { Date.class, Date.class });

        EmptyPlanningResponse emptyPlanningResponse = planning(); //homePage --> planning

        Map<String, String> data = PlanningResponse.prepareRequest(beginDate, endDate);
        data.putAll(emptyPlanningResponse.getHiddenInputData());

        Connection.Response result = jsoupConnect(annotations, data);
        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());
            try {
                return new PlanningResponse(result);
            } catch (IOException e) {
                //
            }
        }
        return null;
    }

    @Override
    public GradesResponse grades() {
        AurionAnnotations annotations = AurionAnnotations.getInstance("grades", new Class[] { });

        IndexResponse indexResponse = index();

        Informer.inform("Connexion à la page \""+ annotations.getTitle() +"\"");

        Map<String, String> data = new HashMap<>();
        data.putAll(indexResponse.getHiddenInputData());
        try {
            data.putAll(GradesResponse.prepareRequest(indexResponse, annotations.getTitle()));
        }
        catch(IOException ex) {
            return null;
        }

        Connection.Response result = jsoupConnect(annotations, data);
        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());

            try {
                return new GradesResponse(result, 0);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public GradesResponse grades(GradesResponse gradesResponse, int page) {

        if (gradesResponse.getPage() == page) return gradesResponse;

        AurionAnnotations annotations = AurionAnnotations.getInstance("grades", new Class[] {  GradesResponse.class, int.class });

        Informer.inform("Connexion à la page \""+ annotations.getTitle() +"\" (page " + (page + 1) + ")");

        Map<String, String> data = new HashMap<>();
        data.putAll(gradesResponse.getHiddenInputData());
        data.putAll(GradesResponse.prepareRequest(page));

        Connection.Response result = jsoupConnect(annotations, data);
        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());

            try {
                return new GradesResponse(result, page);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ConferencesResponse conferences(int page) {
        AurionAnnotations annotations = AurionAnnotations.getInstance("conferences", new Class[] {int.class});

        IndexResponse indexResponse = index();

        Informer.inform("Connexion à la page \""+ annotations.getTitle() +"\" en cours");

        Map<String, String> data = new HashMap<>();
        data.putAll(indexResponse.getHiddenInputData());
        try {
            data.putAll(ConferencesResponse.prepareRequest(indexResponse, annotations.getTitle()));
        }
        catch(IOException ex) {
            //Can't parse document
            return null;
        }

        Connection.Response result = jsoupConnect(annotations, data);
        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());

            try {
                return new ConferencesResponse(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void birthdays() {

    }

    @Override
    public void staffForm() {

    }

    @Override
    public void staff(String status, String dataNom, String dataPrenom, String dataCode) {

    }

    private Connection.Response jsoupConnect(AurionAnnotations annotations) {
        return jsoupConnect(annotations, null);
    }

    private Connection.Response jsoupConnect(AurionAnnotations annotations, Map<String, String> data) {
        try {
            Connection connection = Jsoup.connect(annotations.getUrl())
                    .header(Settings.Api.CONTENT_TYPE_KEY, annotations.getContentType())
                    .timeout(Settings.Api.CONNECTION_TIMEOUT)
                    .userAgent(Settings.Api.USER_AGENT)
                    .method(annotations.getHttpMethod())
                    .cookies(AurionCookies.get());

            if (data != null) {
                connection = connection.data(data);
            }

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
        return !response.url().toString().startsWith(Settings.Api.LOGIN_URL);
    }

    private class Messages {
        static final String INDEX_START = "Chargement de la page d'accueil en cours";
        static final String INDEX_ERROR = "Erreur pendant le chargement de la page d'accueil";

        static final String LOGIN_START = "Identification en cours";

    }
}
