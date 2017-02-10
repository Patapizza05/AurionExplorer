package fr.clementduployez.aurionexplorer.Model;

/**
 * Created by cdupl on 11/22/2016.
 */

public class StudentInfo {
    private String imageUrl;
    private String birthday;
    private String email;
    private String promo;
    private String name;

    public StudentInfo(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
