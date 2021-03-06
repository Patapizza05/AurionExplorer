package fr.clementduployez.aurionexplorer.Api.Annotations;

import org.jsoup.Connection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by cdupl on 11/21/2016.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface HttpMethod {
    Connection.Method value() default Connection.Method.GET;
}
