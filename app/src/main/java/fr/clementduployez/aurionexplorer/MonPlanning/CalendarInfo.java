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

    public CalendarInfo(String day, String beginHour, String endHour, String lessonType, String lessonTitle, String lessonRoom, String lessonId) {
        this.day = day;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.lessonType = lessonType;
        this.lessonTitle = lessonTitle;
        this.lessonRoom = lessonRoom;
        this.lessonId = lessonId;
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
}
