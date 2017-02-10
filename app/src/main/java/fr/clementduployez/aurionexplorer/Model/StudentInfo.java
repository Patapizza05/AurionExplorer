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

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPromo() {
        return promo;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public boolean hasImageUrl() {
        return imageUrl != null;
    }

    public boolean hasBirthday() {
        return birthday != null;
    }

    public boolean hasEmail() {
        return email != null;
    }

    public boolean hasPromo() {
        return promo != null;
    }

    public boolean hasName() {
        return name != null;
    }

    public boolean hasSpecialization() {
        return specialization != null;
    }
}
