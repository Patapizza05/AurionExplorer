package fr.clementduployez.aurionexplorer.Anniversaires;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Utils.CasBrowser;

/**
 * Created by cdupl on 3/22/2016.
 */
public class LoadBirthdaysAsync extends AsyncTask<Void,BirthdayList,BirthdayList> {

private final BirthdayFragment birthdayFragment;

public LoadBirthdaysAsync(BirthdayFragment birthdayFragment) {
        this.birthdayFragment = birthdayFragment;
        }

@Override
protected BirthdayList doInBackground(Void... params) {
        Connection.Response response = CasBrowser.connectToBirthday();

        BirthdayList data = null;
        if (response != null && response.statusCode() == 200) {
            try {
                data = parse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (data == null) {
            data = new BirthdayList();
        }

        return data;

}


    private BirthdayList parse(Connection.Response response) throws IOException {
        BirthdayList data = new BirthdayList();
        List<BirthdayInfo> daily = new ArrayList<>();
        List<BirthdayInfo> monthly = new ArrayList<>();
        data.setDailyBirthdays(daily);
        data.setMonthlyBirthdays(monthly);

        Document document = response.parse();

        Elements divs = document.getElementsByClass("text-center");

        int dailyIndex = divs.size() == 2 ? 0 : -1;
        int monthlyIndex = divs.size() == 2 ? 1 : 0;

        if (dailyIndex != -1) {
            String[] names = divs.get(dailyIndex).ownText().split(",");
            for (String name : names) {
                daily.add(new BirthdayInfo(name.trim()));
            }
        }

        if (monthlyIndex != -1) {
            String[] names = divs.get(monthlyIndex).ownText().split(",");
            for (String name : names) {
                monthly.add(new BirthdayInfo(name.trim()));
            }
        }

        return data;
    }



@Override
protected void onPostExecute(BirthdayList data) {
        super.onPostExecute(data);
        this.birthdayFragment.onAsyncResult(data);
    }
}