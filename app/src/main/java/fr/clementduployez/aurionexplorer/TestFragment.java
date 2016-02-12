package fr.clementduployez.aurionexplorer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;

/**
 * Created by cdupl on 2/12/2016.
 */
public class TestFragment extends Fragment {
    private View rootView;
    private TextView text;

    public static TestFragment newInstance() {
        final TestFragment testFragment= new TestFragment();
        return testFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        this.text = (TextView) rootView.findViewById(R.id.textView2);
        this.text.setText("");
        log("log");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        connect();
    }

    private void connect() {

    }


    public void log(String text) {
        this.text.setText(this.text.getText()+"\n"+text+"\n");
    }
}
