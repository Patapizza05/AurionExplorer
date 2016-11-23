package fr.clementduployez.aurionexplorer.Exceptions;

import android.util.Log;

/**
 * Created by cdupl on 11/23/2016.
 */

public class InvalidNameException extends Exception {

    private final String name;

    public InvalidNameException(String name) {
        super();
        this.name = name;
    }

    @Override
    public void printStackTrace() {
        Log.e("InvalidNaeException", name + " can't be parsed.");
        super.printStackTrace();
    }
}
