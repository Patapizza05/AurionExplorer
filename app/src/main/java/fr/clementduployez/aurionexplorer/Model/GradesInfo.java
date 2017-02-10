package fr.clementduployez.aurionexplorer.Model;

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

    private static String COMMON_TITLE_SPECIAL = "evaluation du module d'";
    private static String COMMON_TITLE_SPECIAL_ = "evaluation du module de base";
    private static String COMMON_TITLE = "evaluation du module";
    private static String COMMON_TITLE_SHORT = "evaluation du";


    @Column
    @PrimaryKey
    private int id;

    @Column
    private String title;
    private String shortTitle = null;

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

    public String getShortenedTitle() {
        if (shortTitle != null) return shortTitle;

        shortTitle = title;
        String lowered = title.toLowerCase();
        if (lowered.startsWith(COMMON_TITLE_SPECIAL)) {
            shortTitle = title.substring(COMMON_TITLE_SPECIAL.length(), title.length()).trim();
        } else if (lowered.startsWith(COMMON_TITLE_SPECIAL_)) {
            shortTitle = title.substring(COMMON_TITLE_SPECIAL_.length(), title.length()).trim();
        } else if (lowered.startsWith(COMMON_TITLE)) {
            shortTitle = title.substring(COMMON_TITLE.length(), title.length()).trim();
        } else if (lowered.startsWith(COMMON_TITLE_SHORT)) {
            shortTitle = title.substring(COMMON_TITLE_SHORT.length(), title.length()).trim();
        }
        return shortTitle;
    }
}
