package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by cdupl on 11/21/2016.
 */

public class LoginResponse extends AurionResponse {
    private String displayName;


    public LoginResponse(Connection.Response loginResponse) {
        super(loginResponse);
        tryInitDisplayName(loginResponse);
    }

    private void tryInitDisplayName(Connection.Response loginResponse) {
        try {
            Document document = loginResponse.parse();
            this.displayName = document.getElementsByClass("login").get(0).html().replaceAll("&nbsp;", "").trim();
        } catch (IOException | IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public String getDisplayName() {
        return displayName;
    }
}
