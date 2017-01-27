package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.clementduployez.aurionexplorer.Model.StaffInfo;

/**
 * Created by Clement on 27/01/2017.
 */

public class StaffResponse extends AbstractResponse {

    private List<StaffInfo> data = null;

    public StaffResponse(Connection.Response response) throws IOException {
        super(response);
        this.data = parseStaff(this.getDocument());

    }

    private List<StaffInfo> parseStaff(Document document) {
        List<StaffInfo> staffData = new ArrayList<>();
        Elements elements = document.body().children();

        if (elements == null || elements.size() <= 0) {
            return staffData;
        }

        for (Element e : elements) {
            try {
                String name = e.getElementsByTag("h3").get(0).html().trim();

                Elements p = e.getElementsByTag("p");
                String code = p.get(0).html().split(":")[1].trim();
                String email = p.get(1).getElementsByTag("a").get(0).html().trim();
                String phone = p.get(2).text().split(":")[1].trim();
                String office = p.get(3).html().split(":")[1].trim();
                ArrayList<String> lessons = new ArrayList<>();
                try {
                    String lessonsString = p.get(5).html();
                    if (!lessonsString.isEmpty()) {
                        if (lessonsString.contains("<br>")) {
                            lessons.addAll(Arrays.asList(lessonsString.split("<br>")));
                        } else {
                            lessons.add(lessonsString);
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    //Nothing to add
                }

                staffData.add(new StaffInfo(name, code, email, phone, office, lessons));

            } catch (Exception ex) {
                //Parsing error or nothing to add
            }
        }
        return staffData;
    }

    public List<StaffInfo> getData() {
        return data;
    }

}
