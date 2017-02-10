package fr.clementduployez.aurionexplorer.Model;

import java.util.Date;

/**
 * Created by cdupl on 11/22/2016.
 */

public class StudentInfo {
    private String imageUrl;
    private String birthday;
    private String email;
    private String promo;
    private String name;
    private String specialization;

    public StudentInfo(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public StudentInfo(String name, String imageUrl, String birthday, String email, String promo, String specialization) {
        this(name, imageUrl);
        this.birthday = birthday;
        this.email = email;
        this.promo = promo;
        this.specialization = specialization;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
