package fr.clementduployez.aurionexplorer;

import android.app.Fragment;

import java.util.List;

/**
 * Created by cdupl on 3/12/2016.
 */
public abstract class AurionPageFragment<Info> extends Fragment {

    public abstract void initViews();
    public abstract void initAdapter();
    public abstract void initData();

    public abstract void showProgressBar();
    public abstract void hideProgressBar();

    public abstract void onRefreshAsync();
    public abstract void onAsyncResult(List<Info> data);

    public abstract void setAdapter(List<Info> data);

}

/*
AsyncTask
Adapter
 */
