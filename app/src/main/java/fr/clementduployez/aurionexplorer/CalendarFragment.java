package fr.clementduployez.aurionexplorer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * Created by cdupl on 2/12/2016.
 */
public class CalendarFragment extends Fragment {
    private View rootView;

    public static CalendarFragment newInstance() {
        final CalendarFragment calendarFragment= new CalendarFragment();
        return calendarFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        return rootView;
    }
}
