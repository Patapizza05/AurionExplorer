package fr.clementduployez.aurionexplorer.Api;

import org.jsoup.Connection;

import fr.clementduployez.aurionexplorer.Api.Annotations.ContentType;
import fr.clementduployez.aurionexplorer.Api.Annotations.HttpMethod;
import fr.clementduployez.aurionexplorer.Api.Annotations.Referrer;
import fr.clementduployez.aurionexplorer.Api.Annotations.Url;
import fr.clementduployez.aurionexplorer.Api.Responses.IndexResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.LoginFormResponse;
import fr.clementduployez.aurionexplorer.Api.Responses.LoginResponse;

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
    LoginResponse login(LoginFormResponse loginFormResponse, String username, String password);

    @Url("https://aurion-lille.isen.fr/faces/planning.xhtml")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void planning();

    @Url("https://aurion-lille.isen.fr/faces/LearnerNotationListPage.xhtml")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void grades(int page);

    @Url("https://aurion-lille.isen.fr/faces/ChoixDonnee.xhtml")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void conferences(int page);

    @Url("https://cas.isen.fr/home/annuaire/anniversaries.html")
    @HttpMethod(Connection.Method.POST)
    @Referrer("https://cas.isen.fr/home/annuaire/anniversaries.html")
    @ContentType
    void birthdays();

    @Url("https://cas.isen.fr/home/annuaire/staff.html")
    @HttpMethod(Connection.Method.GET)
    @ContentType
    void staffForm();

    @Url("https://cas.isen.fr/home/annuaire/infos-staff.html")
    @Referrer("https://cas.isen.fr/home/annuaire/staff.html")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void staff(String status, String dataNom, String dataPrenom, String dataCode);

}
