package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Api.Utils.JSoupUtils;

/**
 * Created by cdupl on 11/21/2016.
 */

public abstract class AurionResponse extends AbstractResponse {
    private Map<String, String> hiddenInputData;



    public AurionResponse(Connection.Response response) {
        super(response);
        this.hiddenInputData = JSoupUtils.getHiddenInputData(response);
    }

    public Map<String, String> getHiddenInputData() {
        return hiddenInputData;
    }


}
