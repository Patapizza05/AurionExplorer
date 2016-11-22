package fr.clementduployez.aurionexplorer.Model;

import java.util.ArrayList;

/**
 * Created by cdupl on 2/17/2016.
 */
public class StaffInfo {

    private final String name;
    private final String code;
    private final String email;
    private final String phone;
    private final String office;
    private final ArrayList<String> lessons;

    public StaffInfo(String name, String code, String email, String phone, String office, ArrayList<String> lessons) {
        this.name = name;
        this.code = code;
        this.email = email;
        this.phone = phone;
        this.office = office;
        this.lessons = lessons;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getOffice() {
        return office;
    }

    public ArrayList<String> getLessons() {
        return lessons;
    }
}
