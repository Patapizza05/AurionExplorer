package fr.clementduployez.aurionexplorer.Api;

import org.jsoup.Connection;

import java.util.Date;

import fr.clementduployez.aurionexplorer.Api.Annotations.ContentType;
import fr.clementduployez.aurionexplorer.Api.Annotations.HttpMethod;
import fr.clementduployez.aurionexplorer.Api.Annotations.Referrer;
import fr.clementduployez.aurionexplorer.Api.Annotations.Title;
import fr.clementduployez.aurionexplorer.Api.Annotations.Url;
import fr.clementduployez.aurionexplorer.Api.Responses.BirthdaysResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.ConferencesResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.PlanningFormResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.GradesResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.IndexResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.LoginFormResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.LoginResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.PlanningResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.StudentsResponse;
import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by cdupl on 11/21/2016.
 */

public interface IAurionApi {

    @Url(Settings.Api.AURION_URL + "/")
    @HttpMethod(Connection.Method.GET)
    @ContentType
    IndexResponse index();

    @Url(Settings.Api.LOGIN_URL)
    @HttpMethod(Connection.Method.GET)
    @ContentType
    LoginFormResponse loginForm();

    @Url(Settings.Api.LOGIN_URL)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    LoginResponse relogin(LoginFormResponse loginFormResponse, String redirectUrl);

    @Url(Settings.Api.LOGIN_URL)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    LoginResponse login(String username, String password);

    @Url(Settings.Api.LOGIN_URL)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    LoginResponse login(LoginFormResponse loginFormResponse, String username, String password, String redirectUrl);

    @Url(Settings.Api.MAIN_MENU_PAGE_URL)
    @Referrer(Settings.Api.AURION_URL)
    @Title(Settings.Api.TITLE_PLANNING)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    PlanningFormResponse planningForm();

    @Url(Settings.Api.PLANNING_URL)
    @Referrer(Settings.Api.AURION_URL)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    PlanningResponse planning(Date beginDate, Date endDate);

    @Url(Settings.Api.MAIN_MENU_PAGE_URL)
    @Title(Settings.Api.TITLE_GRADES)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    GradesResponse grades();

    @Url(Settings.Api.GRADES_URL)
    @Referrer(Settings.Api.AURION_URL)
    @Title(Settings.Api.TITLE_GRADES)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    GradesResponse grades(GradesResponse gradesResponse, int page);

    @Url(Settings.Api.MAIN_MENU_PAGE_URL)
    @Title(Settings.Api.TITLE_CONFERENCES)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    ConferencesResponse conferences(int page);

    @Url(Settings.Api.BIRTHDAY_URL)
    @HttpMethod(Connection.Method.GET)
    @Referrer(Settings.Api.BIRTHDAY_URL)
    @ContentType
    BirthdaysResponse birthdays();

    @Url(Settings.Api.STAFF_URL)
    @HttpMethod(Connection.Method.GET)
    @ContentType
    void staffForm();

    @Url(Settings.Api.STAFF_POST_URL)
    @Referrer(Settings.Api.STAFF_URL)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void staff(String status, String dataNom, String dataPrenom, String dataCode);

    @Url(Settings.Api.STUDENT_POST_URL)
    @Referrer(Settings.Api.STUDENT_URL)
    @HttpMethod(Connection.Method.POST)
    @ContentType
    StudentsResponse students(String dataNom, String dataPrenom, String dataGroupe);

}
