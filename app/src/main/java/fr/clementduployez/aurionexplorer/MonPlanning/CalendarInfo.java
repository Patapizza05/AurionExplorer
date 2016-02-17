package fr.clementduployez.aurionexplorer.MonPlanning;

import android.widget.TextView;

/**
 * Created by cdupl on 2/17/2016.
 */
public class CalendarInfo {

    private final String beginHour;
    private final String endHour;
    private final String lessonType;
    private final String lessonTitle;
    private final String lessonRoom;
    private final String lessonId;
    private final String day;
    private final String teacher;

    public CalendarInfo(String day, String beginHour, String endHour, String lessonType, String lessonTitle, String lessonRoom, String lessonId, String teacher) {
        this.day = day;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.lessonType = lessonType;
        this.lessonTitle = lessonTitle;
        this.lessonRoom = lessonRoom;
        this.lessonId = lessonId;
        this.teacher = teacher;
    }

    public CalendarInfo(String date, String sentence) {
        /*
        Sentence : 17:30 - 19:15 - Cours - - Module d'Ouverture Nature of Sound - M1 - VAN HALTEREN - B305
         */
        this.day = date;

        String words[] = sentence.split("-");
        this.beginHour = words[0].trim();
        this.endHour = words[1].trim();
        this.lessonType = words[2].trim();
        this.lessonTitle = words[4].trim();
        this.lessonId = words[5].trim();
        this.teacher = words[6].trim();
        this.lessonRoom = words[7].trim();

    }

    public String getBeginHour() {
        return beginHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public String getLessonRoom() {
        return lessonRoom;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getDay() {
        return day;
    }

    public String getTeacher() {
        return teacher;
    }
}
