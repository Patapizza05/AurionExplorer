package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.MesConferences.ConferencesInfo;

/**
 * Created by cdupl on 11/21/2016.
 */

public class EmptyPlanningResponse extends AurionResponse {

    public EmptyPlanningResponse(Connection.Response response) {
        super(response);
    }


}
