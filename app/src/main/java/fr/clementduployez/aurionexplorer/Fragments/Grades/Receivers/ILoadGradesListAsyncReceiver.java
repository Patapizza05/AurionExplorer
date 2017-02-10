package fr.clementduployez.aurionexplorer.Fragments.Grades.Receivers;

import java.util.List;

import fr.clementduployez.aurionexplorer.Model.GradesInfo;

/**
 * Created by cdupl on 4/6/2016.
 */
public interface ILoadGradesListAsyncReceiver {
    void onAsyncResult(List<GradesInfo> data);
    void onAsyncProgress(List<GradesInfo> data);
}
