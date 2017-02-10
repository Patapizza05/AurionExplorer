package fr.clementduployez.aurionexplorer.Api.Exceptions;

import org.jsoup.Connection;

/**
 * Created by cdupl on 11/21/2016.
 */

public class NotLoggedInException extends Exception {

    private final Connection.Response loginForm;

    public NotLoggedInException(Connection.Response loginForm) {
        this.loginForm = loginForm;
    }

    public Connection.Response getLoginForm() {
        return loginForm;
    }

}
