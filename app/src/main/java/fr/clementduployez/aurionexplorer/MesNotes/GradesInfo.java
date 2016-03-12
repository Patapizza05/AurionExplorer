package fr.clementduployez.aurionexplorer.MesNotes;

import com.yarolegovich.wellsql.core.Identifiable;
import com.yarolegovich.wellsql.core.annotation.Column;
import com.yarolegovich.wellsql.core.annotation.PrimaryKey;
import com.yarolegovich.wellsql.core.annotation.Table;
import com.yarolegovich.wellsql.core.annotation.Unique;

/**
 * Created by cdupl on 2/14/2016.
 */

@Table
public class GradesInfo implements Identifiable {

    @Column
    @PrimaryKey
    private int id;

    @Column
    private String title;

    @Column @Unique
    private String gradeId;

    @Column
    private String value;

    @Column
    private String date;

    public GradesInfo() {}

    public GradesInfo(String title, String gradeId, String value, String date) {
        this.title = title;
        this.gradeId = gradeId;
        this.value = value;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getGradeId() {
        return gradeId;
    }

    public String getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}
