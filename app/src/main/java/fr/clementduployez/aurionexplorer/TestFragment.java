package fr.clementduployez.aurionexplorer;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Utils.AurionBrowser;

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
        rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
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
            /*private Elements elements;*/

            @Override
            protected Void doInBackground(Void... params) {
                /*try {
                    response = connect();
                    Map<String,String> cookies = response.cookies();
                    response = connect2(response, cookies);
                    cookies.putAll(response.cookies());
                    this.response = connect3(response,cookies);
                    this.elements = this.response.parse().getElementsByTag("table");
                    //this.response = annuaireConnect(cookies);

                }
                catch (Exception e) {
                    e.printStackTrace();
                }*/

                this.response = AurionBrowser.connectToPage("Mes notes");


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                log(response.url().toString());
                log(String.valueOf(response.statusCode()));
                /*if (elements != null)
                    for (Element element : elements) {
                        log(element.toString());
                    }*/
                log("-------------------");
                log(response.body());


            }
        }.execute();
    }

    private Connection.Response connect() {
        Connection.Response result = null;
        try {
            result = Jsoup.connect("https://aurion-lille.isen.fr")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36")
                    .followRedirects(true)
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Connection.Response connect2(Connection.Response response, Map<String,String> cookies) throws IOException {
        Elements elements = response.parse().getElementsByTag("input");
        HashMap<String, String> map = new HashMap<String, String>();
        for (Element element : elements) {
            map.put(element.attr("name"),element.attr("value"));
        }

        Log.i("Res1", "Done");

        map.put("username", "p58095");
        map.put("password", "QL.cbgyX");
        if (response.url().toString().startsWith("https://cas.isen.fr/login"))
        {
            Connection.Response res2 = Jsoup.connect(response.url().toString())
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36")
                    .data(map)
                    .method(Connection.Method.POST)
                    .cookies(cookies)
                    .execute();



            return res2;
        }
        return null;

    }

    private Connection.Response annuaireConnect(Map<String, String> cookies) {
        //https://cas.isen.fr/home/annuaire/staff.html
        try {
            Connection.Response res3 = Jsoup.connect("https://cas.isen.fr/home/annuaire/staff.html")
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36")
                    .cookies(cookies)
                    .followRedirects(true)
                    .execute();

            return res3;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection.Response connect3(Connection.Response res2, Map<String,String> cookies) throws IOException {
        Document doc = res2.parse();
        Elements elements = doc.getElementsByTag("input"); //Marks = AJAX Requests...
        HashMap<String, String> map = new HashMap<>();
        for (Element element : elements) {

            if (element.attr("value") != null && !element.attr("value").isEmpty()) {
                map.put(element.attr("name"), element.attr("value"));
            }
        }

        Elements marks = doc.getElementsByTag("a");
        Log.i("Marks",String.valueOf(marks.size()));
        Element mark = null;
        for (Element e : marks) {
            Log.i("MarkElements",e.html());
            if (e.toString().contains("Mes notes"))
            {
                mark = e;
            }
        }

        if (mark != null) {
            String script = mark.attr("onclick");
            script = script.substring(script.indexOf("{"),script.indexOf("}")+1).replaceAll("\\\\","").replaceAll("\'","\"");
            Log.i("MarkScript", script);
            try {
                JSONObject jsonObject = new JSONObject(script);
                Iterator<?> keys = jsonObject.keys();
                while( keys.hasNext() ) {
                    String key = (String)keys.next();
                    String value = jsonObject.getString(key);
                    map.put(key,value);
                }
            }
            catch (Exception e) {

            }

        }
        else {
            Log.i("Mark","null");
        }

        Log.i("Res2", "Done");

        if (res2.statusCode() == 200) {
            Log.i("Res3", "Starting");

            Connection conn3 = Jsoup.connect("https://aurion-lille.isen.fr/faces/MainMenuPage.xhtml") //
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36")
                    .referrer("https://aurion-lille.isen.fr/")
                    .cookies(cookies)
                    .data(map) //Les hidden inputs
                    .data("form:largeurDivCenter","1660")
                    .method(Connection.Method.POST);

            Connection.Response res3 = conn3.execute();

            Log.i("Res3", "Done");
            return res3;
        }

        return null;
    }


    public void log(String text) {
        this.text.setText(this.text.getText()+"\n"+text+"\n");
    }
}
