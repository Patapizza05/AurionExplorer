package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Utils.JSoupUtils;

/**
 * Created by cdupl on 11/21/2016.
 */

public abstract class AurionResponse {
    private Map<String, String> hiddenInputData;

    private Connection.Response response;

    private Document document;

    public AurionResponse(Connection.Response response) {
        this.hiddenInputData = JSoupUtils.getHiddenInputData(response);
        this.response = response;
    }

    public Map<String, String> getHiddenInputData() {
        return hiddenInputData;
    }

    public Document getDocument() throws IOException {
        if (document == null) document = response.parse();
        return document;
    }
}
