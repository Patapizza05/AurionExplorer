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
import fr.clementduployez.aurionexplorer.Api.Responses.EmptyPlanningResponse;
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

    @Url("https://aurion-lille.isen.fr/")
    @HttpMethod(Connection.Method.GET)
    @ContentType
    IndexResponse index();

    @Url("https://cas.isen.fr/login")
    @HttpMethod(Connection.Method.GET)
    @ContentType
    LoginFormResponse loginForm();

    @Url("https://cas.isen.fr/login")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    LoginResponse relogin(LoginFormResponse loginFormResponse, String redirectUrl);

    @Url("https://cas.isen.fr/login")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    LoginResponse login(String username, String password);

    @Url("https://cas.isen.fr/login")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    LoginResponse login(LoginFormResponse loginFormResponse, String username, String password, String redirectUrl);

    @Url(Settings.Api.MAIN_MENU_PAGE_URL)
    @Referrer(Settings.Api.AURION_URL)
    @Title("Mon planning")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    EmptyPlanningResponse planning();

    @Url("https://aurion-lille.isen.fr/faces/Planning.xhtml")
    @Referrer("https://aurion-lille.isen.fr")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    PlanningResponse planning(Date beginDate, Date endDate);

    @Url(Settings.Api.MAIN_MENU_PAGE_URL)
    @Title("Mes notes")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    GradesResponse grades();

    @Url("https://aurion-lille.isen.fr/faces/LearnerNotationListPage.xhtml")
    @Referrer(Settings.Api.AURION_URL)
    @Title("Mes notes")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    GradesResponse grades(GradesResponse gradesResponse, int page);

    @Url(Settings.Api.MAIN_MENU_PAGE_URL)
    @Title("Mes conférences")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    ConferencesResponse conferences(int page);

    @Url("https://cas.isen.fr/home/annuaire/anniversaries.html")
    @HttpMethod(Connection.Method.GET)
    @Referrer("https://cas.isen.fr/home/annuaire/anniversaries.html")
    @ContentType
    BirthdaysResponse birthdays();

    @Url("https://cas.isen.fr/home/annuaire/staff.html")
    @HttpMethod(Connection.Method.GET)
    @ContentType
    void staffForm();

    @Url("https://cas.isen.fr/home/annuaire/infos-staff.html")
    @Referrer("https://cas.isen.fr/home/annuaire/staff.html")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void staff(String status, String dataNom, String dataPrenom, String dataCode);

    @Url("https://cas.isen.fr/home/annuaire/infos-students.html")
    @Referrer("https://cas.isen.fr/home/annuaire/students.html")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    StudentsResponse students(String dataNom, String dataPrenom, String dataGroupe);

}
