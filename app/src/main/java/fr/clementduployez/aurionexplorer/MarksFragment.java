package fr.clementduployez.aurionexplorer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cdupl on 2/12/2016.
 */
public class MarksFragment extends Fragment {

    private View rootView;

    public static MarksFragment newInstance() {
        final MarksFragment marksFragment = new MarksFragment();
        //final Bundle arguments = new Bundle();
        //arguments.putParcelable(tweetKey, tweet);
        //tweetFragment.setArguments(arguments);
        return marksFragment;
    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            tweetClickListener = (TweetClickListener) activity;
        } catch (Exception ex) {
            //@users and #hashtags won't be clickable
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.marks_fragment, container, false);
        //Tweet tweet = getArguments().getParcelable(tweetKey);

        return rootView;
    }

}