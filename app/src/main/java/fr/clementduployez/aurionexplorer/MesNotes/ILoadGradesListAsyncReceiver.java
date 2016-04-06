package fr.clementduployez.aurionexplorer.MesNotes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdupl on 4/6/2016.
 */
public interface ILoadGradesListAsyncReceiver {
    void onAsyncResult(List<GradesInfo> data);
    void onAsyncProgress(List<GradesInfo> data);
}
