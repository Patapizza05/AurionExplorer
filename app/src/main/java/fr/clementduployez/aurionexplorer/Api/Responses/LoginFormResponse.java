package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;

/**
 * Created by cdupl on 11/21/2016.
 */

public class LoginFormResponse extends AurionResponse {

    public static final String USERNAME_INPUT_KEY = "username";
    public static final String PASSWORD_INPUT_KEY = "password";
    public static final String REDIRECT_KEY = "service";

    public LoginFormResponse(Connection.Response loginFormResponse)
    {
        super(loginFormResponse);
    }
}
