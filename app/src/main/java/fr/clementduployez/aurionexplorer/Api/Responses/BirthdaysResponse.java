package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Model.BirthdayInfo;
import fr.clementduployez.aurionexplorer.Model.BirthdayList;

/**
 * Created by cdupl on 11/22/2016.
 */

public class BirthdaysResponse extends AbstractResponse {

    private BirthdayList birthdayList;

    public BirthdaysResponse(Connection.Response response) throws IOException {
        super(response);
        this.birthdayList = parseBirthdays(getDocument());
    }

    private BirthdayList parseBirthdays(Document document) {

        BirthdayList data = new BirthdayList();
        List<BirthdayInfo> daily = new ArrayList<>();
        List<BirthdayInfo> monthly = new ArrayList<>();
        data.setDailyBirthdays(daily);
        data.setMonthlyBirthdays(monthly);

        Elements divs = document.getElementsByClass("text-center");

        int dailyIndex = divs.size() == 2 ? 0 : -1; //If only one div, no birthday today (-1 : error)
        int monthlyIndex = divs.size() == 2 ? 1 : 0; //If only one div, only monthly birthdays (on cell 0), otherwise, it's on cell 2.

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


    public BirthdayList getBirthdayList() {
        return birthdayList;
    }
}
