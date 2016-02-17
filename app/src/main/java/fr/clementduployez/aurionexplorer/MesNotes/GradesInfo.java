package fr.clementduployez.aurionexplorer.MesNotes;

/**
 * Created by cdupl on 2/14/2016.
 */
public class GradesInfo {
    private String title;
    private String id;
    private String value;
    private String date;

    public GradesInfo(String title, String id, String value, String date) {
        this.title = title;
        this.id = id;
        this.value = value;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }
}
