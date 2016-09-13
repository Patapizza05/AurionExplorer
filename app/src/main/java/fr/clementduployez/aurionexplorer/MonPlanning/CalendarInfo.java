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

    @Column
    private String description;

    public CalendarInfo() {
    }

    String getValueAtIndex(String[] words, int i) {
        try {
            return words[i].trim();
        }
        catch(Exception ex) {
            return "";
        }
    }

    public CalendarInfo(String date, String sentence) {
        /*
        Sentence : 17:30 - 19:15 - Cours - - Module d'Ouverture Nature of Sound - M1 - VAN HALTEREN - B305
         */
        this.day = date.substring(0, 1).toUpperCase() + date.substring(1);

        String words[] = sentence.split("-");
        
        this.beginHour = getValueAtIndex(words, 0);
        this.endHour = getValueAtIndex(words, 1);
        this.lessonType = getValueAtIndex(words, 2);
        this.description = getValueAtIndex(words, 3);
        this.lessonTitle = getValueAtIndex(words, 4);
        this.lessonId = getValueAtIndex(words, 5);
        this.teacher = getValueAtIndex(words, 6);
        this.lessonRoom = getValueAtIndex(words, 7);
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
        if (lessonTitle == null || lessonTitle.isEmpty()) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
