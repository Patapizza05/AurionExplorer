package fr.clementduployez.aurionexplorer.Fragments;

import android.app.Fragment;

/**
 * Created by Clement on 10/02/2017.
 */

public abstract class DirectoryFragment extends Fragment {

    public abstract  void sendForm(String lastName, String firstName, String code);
}
