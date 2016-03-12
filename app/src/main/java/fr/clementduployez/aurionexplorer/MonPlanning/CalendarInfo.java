package fr.clementduployez.aurionexplorer.MonPlanning;

import com.yarolegovich.wellsql.core.Identifiable;
import com.yarolegovich.wellsql.core.annotation.Column;
import com.yarolegovich.wellsql.core.annotation.PrimaryKey;
import com.yarolegovich.wellsql.core.annotation.RawConstraints;
import com.yarolegovich.wellsql.core.annotation.Table;

/**
 * Created by cdupl on 2/17/2016.
 */

@Table
@RawConstraints({"UNIQUE (DAY, BEGIN_HOUR)"})
public class CalendarInfo implements Identifiable {

    @Column
    @PrimaryKey
    private int id;

    @Column
    private String beginHour;

    @Column
    private String endHour;

    @Column
    private String lessonType;

    @Column
    private String lessonTitle;

    @Column
    private String lessonRoom;

    @Column
    private String lessonId;

    @Column
    private String day;

    @Column
    private String teacher;

    public CalendarInfo() {}

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
        this.day = date.substring(0, 1).toUpperCase() + date.substring(1);

        String words[] = sentence.split("-");
        this.beginHour = words[0].trim();
        this.endHour = words[1].trim();
        this.lessonType = words[2].trim();
        this.lessonTitle = words[4].trim();
        this.lessonId = words[5].trim();
        this.teacher = words[6].trim();
        this.lessonRoom = words[7].trim();

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(String beginHour) {
        this.beginHour = beginHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public String getLessonTitle() {
        if (lessonTitle == null || lessonTitle.isEmpty())
        {
            return getLessonType();
        }
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getLessonRoom() {
        return lessonRoom;
    }

    public void setLessonRoom(String lessonRoom) {
        this.lessonRoom = lessonRoom;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
