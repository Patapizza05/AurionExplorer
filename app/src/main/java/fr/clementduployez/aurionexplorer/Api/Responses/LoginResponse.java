package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by cdupl on 11/21/2016.
 */

public class LoginResponse extends AurionResponse {

    private String displayName;

    public LoginResponse(Connection.Response loginResponse) throws IOException {
        super(loginResponse);
        tryInitDisplayName(getDocument());
    }

    private void tryInitDisplayName(Document document) {
        try {
            this.displayName = document.getElementsByClass("login").get(0).html().replaceAll("&nbsp;", "").trim();
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public String getDisplayName() {
        return displayName;
    }

}
