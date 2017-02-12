package fr.clementduployez.aurionexplorer.Settings;

import android.app.AlarmManager;

/**
 * Created by cdupl on 2/21/2016.
 */
public class Settings {

    public static boolean LITE = false;
    public static final long GRADES_UPDATE_INTERVAL = AlarmManager.INTERVAL_HOUR;

    public static class Api {
        public static final int CONNECTION_TIMEOUT = 100000; //ms
        public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36";
        public static final String AURION_URL = "https://aurion-lille.isen.fr";
        public static final String LOGIN_REDIRECT_URL = AURION_URL + "/j_spring_cas_security_check";
        public static final String MAIN_MENU_PAGE_URL = AURION_URL + "/faces/MainMenuPage.xhtml";
        public static final String CONTENT_TYPE_KEY = "Content-Type";
        public static final String CAS_URL = "https://cas.isen.fr";
        public static final String LOGIN_URL = CAS_URL + "/login";
        public static final String PLANNING_URL = AURION_URL + "/faces/Planning.xhtml";
        public static final String GRADES_URL = AURION_URL + "/faces/LearnerNotationListPage.xhtml";
        public static final String BIRTHDAY_URL = CAS_URL + "/home/annuaire/anniversaries.html";
        public static final String STAFF_URL = CAS_URL + "/home/annuaire/staff.html";
        public static final String STAFF_POST_URL = CAS_URL + "/home/annuaire/infos-staff.html";
        public static final String STUDENT_POST_URL = CAS_URL + "/home/annuaire/infos-students.html";
        public static final String STUDENT_URL = CAS_URL + "/home/annuaire/students.html";

        public static final String TITLE_CONFERENCES = "Mes conférences";
        public static final String TITLE_GRADES = "Mes notes";
        public static final String TITLE_PLANNING = "Mon planning";
    }

    public static class Planning {
        public static final int DAY_OFFSET = 14;
        public static final int MAX_DAYS = 31;
    }

    public static class Titles {
        public static final String DELIMITER = "---";
        public static final String MY_GRADES = "Mes Notes";
        public static final String MY_ABSENCES = "Mes Absences";
        public static final String MY_PLANNING = "Mon Planning";
        public static final String STAFF_DIRECTORY = "Annuaire du Staff";
        public static final String STUDENTS_DIRECTORY = "Annuaire des Étudiants";
        public static final String MY_CONFERENCES = "Mes Conférences";
        public static final String BIRTHDAYS = "Anniversaires";
    }


    public class IntentActions {
        public static final String GRADES = "intent_launch_grades";
        public static final String PLANNING = "intent_launch_planning";
        public static final String STAFF_DIRECTORY = "intent_launch_staff_form";
        public static final String STUDENTS_DIRECTORY = "intent_launch_students_form";

    }

}
