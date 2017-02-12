package fr.clementduployez.aurionexplorer.Model.Titles;

import android.app.Fragment;

import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import fr.clementduployez.aurionexplorer.Fragments.NotImplemented.NotImplementedFragment;

/**
 * Created by Clement on 12/02/2017.
 */

public abstract class Title {

    private final boolean delimiter;
    private String title;
    private Integer resourceIcon;

    public Title(String title, int resourceIcon) {
        this.title = title;
        this.resourceIcon = resourceIcon;
        this.delimiter = false;
    }

    public Title() {
        //delimiter;
        this.title = null;
        this.resourceIcon = null;
        this.delimiter = true;
    }

    public boolean isDelimiter() {
        return delimiter;
    }

    public IDrawerItem createItem() {
        if (!isDelimiter()) {
            return new PrimaryDrawerItem().withName(title).withIcon(resourceIcon);
        }
        return new DividerDrawerItem();
    }

    public Fragment getFragment() {
        return NotImplementedFragment.newInstance();
    }
}
