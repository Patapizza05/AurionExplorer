package fr.clementduployez.aurionexplorer;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        new AsyncTask<Void,Void,Void>() {

            private Connection.Response response;
            private Elements elements;

            @Override
            protected Void doInBackground(Void... params) {
                response = connect();
                Map<String,String> cookies = response.cookies();

                try {
                    elements = response.parse().getElementsByTag("input");
                    HashMap<String, String> map = new HashMap<String, String>();
                    for (Element element : elements) {
                        map.put(element.attr("name"),element.attr("value"));
                    }
                    map.put("username","FIXME");
                    map.put("password","FIXME");

                    if (response.url().toString().startsWith("https://cas.isen.fr/login"))
                    {
                        Connection.Response res2 = Jsoup.connect(response.url().toString())
                                .data(map)
                                .method(Connection.Method.POST)
                                .cookies(cookies)
                                .execute();
                        this.response = res2;
                    }
                } catch (IOException e) {
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                log(response.url().toString());
                log(response.statusMessage());
                log(String.valueOf(response.statusCode()));
                log(response.body());
                /*for (Element element : elements) {
                    log(element.toString());
                }*/

            }
        }.execute();
    }

    private Connection.Response connect() {
        Connection.Response result = null;
        try {
            result = Jsoup.connect("https://aurion-lille.isen.fr").followRedirects(true).execute();
            //log(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void log(String text) {
        this.text.setText(this.text.getText()+"\n"+text+"\n");
    }
}
