package fr.clementduployez.aurionexplorer;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
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
                    this.elements = null;
                    Log.i("Res1", "Done");

                    map.put("username","user");
                    map.put("password", "pass");
                    if (response.url().toString().startsWith("https://cas.isen.fr/login"))
                    {
                        Connection.Response res2 = Jsoup.connect(response.url().toString())
                                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                                .data(map)
                                .method(Connection.Method.POST)
                                .cookies(cookies)
                                .execute();
                        this.response = res2;
                        cookies.putAll(res2.cookies());
                        Log.i("Res2", "Done");
                        if (res2.statusCode() == 200)
                        {
                            Log.i("Res3","Starting");
                            Connection.Response res3 = Jsoup.connect("https://aurion-lille.isen.fr/faces/LearnerNotationListPage.xhtml")
                                    .header("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
                                    .cookies(cookies)
                                    .execute();

                            this.response = res3;

                            elements = res3.parse().getElementsByTag("script"); //Marks = AJAX Requests...
                            Log.i("Res3", "Done");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                log(response.url().toString());
                log(response.statusMessage());
                log(String.valueOf(response.statusCode()));
                if (elements != null)
                    for (Element element : elements) {
                        for(DataNode node : element.dataNodes())
                        {
                            log(node.getWholeData());
                        }
                    }
                //log(response.body());


            }
        }.execute();
    }

    private Connection.Response connect() {
        Connection.Response result = null;
        try {
            result = Jsoup.connect("https://aurion-lille.isen.fr")
                    .followRedirects(true)
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .execute();
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
