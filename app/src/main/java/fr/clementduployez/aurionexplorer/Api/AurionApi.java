package fr.clementduployez.aurionexplorer.Api;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Api.Annotations.AurionAnnotations;
import fr.clementduployez.aurionexplorer.Api.Responses.BirthdaysResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.ConferencesResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.PlanningFormResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.GradesResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.IndexResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.LoginFormResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.LoginResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.PlanningResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.StudentsResponse;
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
        Informer.getInstance().inform(Messages.INDEX_START);
        AurionAnnotations annotations = AurionAnnotations.getInstance("index", new Class[] {});
        Connection.Response result = jsoupConnect(annotations);

        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());

            if (!isLoggedIn(result)) {
                LoginFormResponse loginFormResponse = new LoginFormResponse(result);
                if (isFirstTry) {
                    relogin(loginFormResponse, null);
                    return index(false);
                }
            }
            return new IndexResponse(result);
        }
        else {
            Informer.getInstance().inform(Messages.INDEX_ERROR);
        }
        return null;
    }

    @Override
    public LoginFormResponse loginForm() {

        AurionAnnotations annotations = AurionAnnotations.getInstance("loginForm", new Class[] {});

        Informer.getInstance().inform(Messages.LOGIN_FORM_START);

        Connection.Response result = jsoupConnect(annotations);

        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());
            return new LoginFormResponse(result);
        }
        return null;
    }

    @Override
    public LoginResponse relogin(LoginFormResponse loginFormResponse, String redirectUrl) {
        return login(loginFormResponse, UserData.getUsername(), UserData.getPassword(), redirectUrl);
    }

    @Override
    public LoginResponse login(String username, String password) {
        return login(loginForm(), username, password, null);
    }

    @Override
    public LoginResponse login(LoginFormResponse loginFormResponse, String username, String password, String redirectUrl) {
        AurionAnnotations annotations = AurionAnnotations.getInstance("login", new Class[] {LoginFormResponse.class, String.class, String.class, String.class });

        Informer.getInstance().inform(Messages.LOGIN_START);

        Map<String, String> data = loginFormResponse.getHiddenInputData();
        data.put(LoginFormResponse.USERNAME_INPUT_KEY, username);
        data.put(LoginFormResponse.PASSWORD_INPUT_KEY, password);

        if (redirectUrl == null) redirectUrl = Settings.Api.LOGIN_REDIRECT_URL;
        data.put(LoginFormResponse.REDIRECT_KEY, redirectUrl);

        Connection.Response result = jsoupConnect(annotations, data);

        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies()); //Keep user logged in
            try {
                return new LoginResponse(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Informer.getInstance().getInstance().inform(Messages.LOGIN_ERROR);
        //In case of error, clear cookies to make sure user is disconnected
        AurionCookies.clear();
        return null;
    }

    @Override
    public PlanningFormResponse planningForm() {
        AurionAnnotations annotations = AurionAnnotations.getInstance("planningForm", new Class[] {});

        IndexResponse indexResponse = index();

        Informer.getInstance().inform(Messages.LOADING_PAGE_FORMAT, annotations.getTitle());

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

            return new PlanningFormResponse(result);
        }

        return null;
    }

    @Override
    public PlanningResponse planning(Date beginDate, Date endDate) {
        AurionAnnotations annotations = AurionAnnotations.getInstance("planning", new Class[] { Date.class, Date.class });

        PlanningFormResponse planningFormResponse = planningForm(); //homePage --> planningForm

        Map<String, String> data = PlanningResponse.prepareRequest(beginDate, endDate);
        data.putAll(planningFormResponse.getHiddenInputData());

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

        Informer.getInstance().inform(Messages.LOADING_PAGE_FORMAT, annotations.getTitle());

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

        Informer.getInstance().inform(Messages.LOADING_PAGE_2_FORMAT, annotations.getTitle(), page + 1);

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

        Informer.getInstance().inform(Messages.LOADING_PAGE_FORMAT, annotations.getTitle());

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
    public BirthdaysResponse birthdays() {
        return birthdays(true);
    }

    private BirthdaysResponse birthdays(boolean isFirstTry) {
        AurionAnnotations annotations = AurionAnnotations.getInstance("birthdays", new Class[] {});

        Connection.Response result = jsoupConnect(annotations);

        Informer.getInstance().inform(Messages.BIRTHDAY_START);

        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());

            if (!isLoggedIn(result)) {
                LoginFormResponse loginFormResponse = new LoginFormResponse(result);
                if (isFirstTry) {
                    LoginResponse loginResponse = relogin(loginFormResponse, annotations.getUrl());
                    try {
                        return new BirthdaysResponse(loginResponse.getResponse());
                    }
                    catch(IOException ex) {
                        return birthdays(false);
                    }
                }
            }
            try {
                return new BirthdaysResponse(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Informer.getInstance().inform(Messages.BIRTHDAY_ERROR);
        return null;
    }

    @Override
    public void staffForm() {

    }

    @Override
    public void staff(String status, String dataNom, String dataPrenom, String dataCode) {

    }

    @Override
    public StudentsResponse students(String dataNom, String dataPrenom, String dataGroupe) {
        return students(dataNom, dataPrenom, dataGroupe, true);
    }

    private StudentsResponse students(String dataNom, String dataPrenom, String dataGroupe, boolean isFirstTry) {
        if (dataPrenom == null) dataPrenom = "";
        if (dataNom == null) dataNom = "";
        if (dataGroupe == null) dataGroupe = "%";
        if (dataPrenom == "" && dataNom == "" && dataGroupe == "%") return null;

        //status is always 'Y'
        AurionAnnotations annotations = AurionAnnotations.getInstance("students", new Class[] { String.class, String.class, String.class });

        Informer.getInstance().inform(Messages.STUDENT_LOADING);

        Map<String, String> data = new HashMap<>();
        data.put("status", "Y");
        data.put("dataNom", dataNom);
        data.put("dataPrenom", dataPrenom);
        data.put("dataGroupe", dataGroupe);

        Connection.Response result = jsoupConnect(annotations, data);

        if (result != null && result.statusCode() == 200) {
            AurionCookies.addAll(result.cookies());

            if (!isLoggedIn(result)) {
                LoginFormResponse loginFormResponse = new LoginFormResponse(result);
                if (isFirstTry) {
                    relogin(loginFormResponse, null);
                    return students(dataNom,dataPrenom,dataGroupe,false);
                }
            }
            try {
                return new StudentsResponse(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Informer.getInstance().inform(Messages.STUDENT_ERROR);
        return null;
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

    public class Messages {
        static final String INDEX_START = "Chargement de la page d'accueil en cours";
        static final String INDEX_ERROR = "Erreur pendant le chargement de la page d'accueil";

        static final String LOGIN_START = "Identification en cours";
        static final String LOGIN_ERROR = "Erreur d'identification";
        static final String LOGIN_FORM_START = "Chargement de la page d'identification";
        static final String LOADING_PAGE_FORMAT = "Chargement de la page {0}";
        static final String LOADING_PAGE_2_FORMAT = "Chargement de  la page {0} (Page {1})";
        static final String BIRTHDAY_START = "Chargement de la page d'anniversaires";
        static final String STUDENT_LOADING = "Récupération des informations sur l'étudiant(e)";
        static final String STUDENT_ERROR = "Aucune information trouvée sur l'étudiant(e)";
        static final String BIRTHDAY_ERROR = "Connexion échouée";

        public static final String PLANNING_SUCCESS = "Récupération du calendrier terminée";
        public static final String GRADES_SUCCESS = "Récupération des notes terminée";
    }
}
