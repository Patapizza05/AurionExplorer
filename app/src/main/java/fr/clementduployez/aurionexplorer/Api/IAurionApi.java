package fr.clementduployez.aurionexplorer.Api;

import org.jsoup.Connection;

import fr.clementduployez.aurionexplorer.Api.Annotations.ContentType;
import fr.clementduployez.aurionexplorer.Api.Annotations.HttpMethod;
import fr.clementduployez.aurionexplorer.Api.Annotations.Referrer;
import fr.clementduployez.aurionexplorer.Api.Annotations.Url;
import fr.clementduployez.aurionexplorer.Api.Responses.IndexResponse;

/**
 * Created by cdupl on 11/21/2016.
 */

public interface IAurionApi {

    @Url("https://aurion-lille.isen.fr/")
    @HttpMethod(Connection.Method.GET)
    @ContentType
    IndexResponse Index();

    @Url("https://cas.isen.fr/login")
    @HttpMethod(Connection.Method.GET)
    @ContentType
    void LoginForm();

    @Url("https://cas.isen.fr/login")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void Login(String username, String password);

    @Url("https://aurion-lille.isen.fr/faces/Planning.xhtml")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void Planning();

    @Url("https://aurion-lille.isen.fr/faces/LearnerNotationListPage.xhtml")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void Grades(int page);

    @Url("https://aurion-lille.isen.fr/faces/ChoixDonnee.xhtml")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void Conferences(int page);

    @Url("https://cas.isen.fr/home/annuaire/anniversaries.html")
    @HttpMethod(Connection.Method.POST)
    @Referrer("https://cas.isen.fr/home/annuaire/anniversaries.html")
    @ContentType
    void Birthdays();

    @Url("https://cas.isen.fr/home/annuaire/staff.html")
    @HttpMethod(Connection.Method.GET)
    @ContentType
    void StaffForm();

    @Url("https://cas.isen.fr/home/annuaire/infos-staff.html")
    @Referrer("https://cas.isen.fr/home/annuaire/staff.html")
    @HttpMethod(Connection.Method.POST)
    @ContentType
    void Staff(String status, String dataNom, String dataPrenom, String dataCode);

}
