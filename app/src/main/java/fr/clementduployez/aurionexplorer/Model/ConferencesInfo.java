package fr.clementduployez.aurionexplorer.Model;

/**
 * Created by cdupl on 2/22/2016.
 */
public class ConferencesInfo {

    private final String date;
    private final String title;

    public ConferencesInfo(String title, String date) {
        this.date = date;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
