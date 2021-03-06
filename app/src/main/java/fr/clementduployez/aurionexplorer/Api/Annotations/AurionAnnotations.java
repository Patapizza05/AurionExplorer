package fr.clementduployez.aurionexplorer.Api.Annotations;

import org.jsoup.Connection;

import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.HashMap;

import fr.clementduployez.aurionexplorer.Api.AurionApi;
import fr.clementduployez.aurionexplorer.Api.IAurionApi;

/**
 * Created by cdupl on 11/21/2016.
 */

public class AurionAnnotations {

    private static HashMap<Method, AurionAnnotations> dictionary = new HashMap<>();

    private String url;
    private String referrer;
    private String contentType;
    private Connection.Method httpMethod;
    private String title;

    public static AurionAnnotations getInstance(String method, Class[] parameters) {
        try {
            return getInstance(IAurionApi.class.getMethod(method, parameters));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AurionAnnotations getInstance(Method method) {
        if (dictionary.containsKey(method)) {
            return dictionary.get(method);
        }
        else {
            AurionAnnotations a = new AurionAnnotations(method);
            dictionary.put(method, a);
            return a;
        }
    }

    public AurionAnnotations(Method method) {
        initUrl(method);
        initReferrer(method);
        initContentType(method);
        initHttpMethod(method);
        initTitle(method);
    }

    private void initUrl(Method method) {
        try {
            this.url = method.getAnnotation(Url.class).value();
        }
        catch(Exception ex) {
            //Not used
            ex.printStackTrace();
        }
    }

    private void initReferrer(Method method) {
        try {
            this.referrer = method.getAnnotation(Referrer.class).value();
        }
        catch(Exception ex) {
            //
        }
    }

    private void initContentType(Method method) {
        try {
            this.contentType = method.getAnnotation(ContentType.class).value();
        }
        catch(Exception ex) {
            //
        }
    }

    private void initHttpMethod(Method method) {
        try {
            this.httpMethod = method.getAnnotation(HttpMethod.class).value();
        }
        catch(Exception ex) {
            //
        }
    }

    private void initTitle(Method method) {
        try {
            this.title = method.getAnnotation(Title.class).value();
        }
        catch(Exception ex) {
            //
        }
    }

    public Connection.Method getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getContentType() {
        return contentType;
    }

    public String getTitle() {
        return title;
    }
}
