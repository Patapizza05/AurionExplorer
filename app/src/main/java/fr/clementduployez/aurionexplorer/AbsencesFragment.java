package fr.clementduployez.aurionexplorer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cdupl on 2/12/2016.
 */
public class AbsencesFragment extends Fragment {
    private View rootView;

    public static AbsencesFragment newInstance() {
        final AbsencesFragment absencesFragment = new AbsencesFragment();
        return absencesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.absences_fragment, container, false);
        return rootView;
    }
}
