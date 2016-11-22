package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by cdupl on 11/22/2016.
 */

public abstract class AbstractResponse {
    private Connection.Response response;

    private Document document;

    public AbstractResponse(Connection.Response response) {
        this.response = response;
    }

    public Connection.Response getResponse() {
        return response;
    }

    public Document getDocument() throws IOException {
        if (document == null) document = response.parse();
        return document;
    }
}
