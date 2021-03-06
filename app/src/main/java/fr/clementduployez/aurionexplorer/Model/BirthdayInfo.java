package fr.clementduployez.aurionexplorer.Model;

import com.yarolegovich.wellsql.core.Identifiable;
import com.yarolegovich.wellsql.core.annotation.Column;
import com.yarolegovich.wellsql.core.annotation.PrimaryKey;
import com.yarolegovich.wellsql.core.annotation.Table;

/**
 * Created by cdupl on 3/22/2016.
 */
@Table
public class BirthdayInfo implements Identifiable {

    @Column
    @PrimaryKey
    private int id;

    @Column
    private String name;

    private String imageUrl;

    public BirthdayInfo() {

    }

    public BirthdayInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
