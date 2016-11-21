package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Utils.JSoupUtils;

/**
 * Created by cdupl on 11/21/2016.
 */

public class IndexResponse extends AurionResponse {
    public IndexResponse(Connection.Response indexResponse) {
        super(indexResponse);
    }
}
