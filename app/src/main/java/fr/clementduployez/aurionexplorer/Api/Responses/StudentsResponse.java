package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import fr.clementduployez.aurionexplorer.Model.StudentInfo;
import fr.clementduployez.aurionexplorer.Settings.Settings;

/**
 * Created by cdupl on 11/22/2016.
 */

public class StudentsResponse extends AbstractResponse {

    private final List<StudentInfo> data;

    public StudentsResponse(Connection.Response response) throws IOException {
        super(response);
        this.data = parseStudents(getDocument());
    }

    private List<StudentInfo> parseStudents(Document document) {

        List<StudentInfo> students = new LinkedList<>();

        Elements containers = document.getElementsByClass("span2");

        for (Element studentContainer: containers) {
            String imageUrl = parseImageUrl(studentContainer);
            String name = parseName(studentContainer);

            String birthday = null;
            String email = null;
            String promo = null;
            String specialization = null;

            for(Element p : studentContainer.getElementsByTag("p")) {
                if (p.ownText().startsWith("Date")) //de naissance
                {
                    birthday = parseDetail(p);
                }
                else if (p.ownText().startsWith("Majeure")) {
                    specialization = parseDetail(p);
                }
                else if (p.ownText().startsWith("Courriel")) {
                    email = parseEmail(p);
                }
                else if (p.ownText().startsWith("Promotion")) {
                    promo = parseDetail(p);
                }
            }


            students.add(new StudentInfo(name, imageUrl, birthday, email, promo, specialization));
        }

        return students;
    }

    private String parseImageUrl(Element studentContainer) {
        try {
            String imageUrl = studentContainer.getElementsByClass("img-polaroid").get(0).attr("src");
            if (imageUrl != null && imageUrl.startsWith("/")) {
                imageUrl = Settings.Api.CAS_URL + imageUrl;
            }
            return imageUrl;
        }
        catch(Exception ex) {
            return null;
        }
    }

    private String parseName(Element studentContainer) {
        try {
            return studentContainer.getElementsByTag("h3").get(0).ownText().trim();
        }
        catch(Exception ex) {
            return null;
        }
    }

    private String parseDetail(Element p) {
        try {
            String ownText = p.ownText();
            return p.ownText().split(":")[1].trim();
        }
        catch(Exception ex) {
            return null;
        }
    }

    private String parseEmail(Element p) {
        try {
            return p.getElementsByTag("a").get(0).ownText().trim();
        }
        catch(Exception ex) {
            return null;
        }
    }




    public List<StudentInfo> getData() {
        return data;
    }

}
