package fr.clementduployez.aurionexplorer.Utils;

import java.util.List;

import fr.clementduployez.aurionexplorer.Model.CalendarInfo;

/**
 * Created by cdupl on 11/22/2016.
 */

public interface Callback<T>  {
    void run(T data);
}
