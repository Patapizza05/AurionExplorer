package fr.clementduployez.aurionexplorer.Api.Responses;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public List<StudentInfo> parseStudents(Document document) {

        List<StudentInfo> students = new LinkedList<>();

        Elements containers = document.getElementsByClass("span2");

        for (Element studentContainer: containers) {
            String imageUrl = studentContainer.getElementsByClass("img-polaroid").get(0).attr("src");
            if (imageUrl != null && imageUrl.startsWith("/")) {
                imageUrl = Settings.Api.CAS_URL + imageUrl;
            }
            String name = studentContainer.getElementsByTag("h3").get(0).ownText().trim();
            students.add(new StudentInfo(name, imageUrl));
        }

        return students;
    }

    public List<StudentInfo> getData() {
        return data;
    }

}
