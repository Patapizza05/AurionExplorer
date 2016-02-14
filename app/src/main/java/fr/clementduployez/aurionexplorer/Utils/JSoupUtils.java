package fr.clementduployez.aurionexplorer.Utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by cdupl on 2/13/2016.
 */
public class JSoupUtils {
    public static Elements getInputs(Connection.Response loginPageResponse) {
        try {
            return loginPageResponse.parse().getElementsByTag("input");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> getHiddenInputData(Connection.Response loginPageResponse) {
        return getHiddenInputData(getInputs(loginPageResponse));
    }

    public static Map<String,String> getHiddenInputData(Elements inputs) {
        HashMap<String, String> data = new HashMap<>();

        for (Element input : inputs) {
            if (input.attr("type").toLowerCase().equals("hidden")) {
                String name = input.attr("name");
                String value = input.attr("value");
                if (value == null) {
                    value = "";
                }
                data.put(name,value);
            }
        }
        return data;
    }

    public static Element getLinkWithTitle(String title, Document document) {
        Elements marks = document.getElementsByTag("a");
        for (Element e : marks) {
            if (e.html().toLowerCase().contains(title.toLowerCase()))
            {
                return e;
            }
        }
        return null;
    }

    public static void addLinkValueToData(String title, Document aurionDocument, Map<String,String> data) {
        addLinkValueToData(getLinkWithTitle(title,aurionDocument),data);
    }

    public static void addLinkValueToData(Element link, Map<String, String> data) {
        if (link == null) {
            return;
        }
        JSONObject jObject = getJSONObjectFromOnClickLinkScript(link.attr("onclick"));
        Iterator<?> keys = jObject.keys();
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            try {
                String value = jObject.getString(key);
                data.put(key,value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private static JSONObject getJSONObjectFromOnClickLinkScript(String script) {
        script = script.substring(script.indexOf("{"),script.indexOf("}")+1).replaceAll("\\\\","").replaceAll("\'","\"");
        try {
            return new JSONObject(script);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

}
