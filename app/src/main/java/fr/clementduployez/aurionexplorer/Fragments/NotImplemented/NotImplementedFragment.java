package fr.clementduployez.aurionexplorer.Fragments.NotImplemented;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/12/2016.
 */
public class NotImplementedFragment extends Fragment {
    private View rootView;

    public static NotImplementedFragment newInstance() {
        final NotImplementedFragment notImplementedFragment = new NotImplementedFragment();
        return notImplementedFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_absences, container, false);
        ((AppCompatActivity) (this.getActivity())).getSupportActionBar().setSubtitle(null);
        return rootView;
    }
}
