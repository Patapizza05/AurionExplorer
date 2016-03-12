package fr.clementduployez.aurionexplorer;

import android.app.Fragment;

import java.util.List;

/**
 * Created by cdupl on 3/12/2016.
 */
public abstract class AurionPageFragment<T> extends Fragment {

    public abstract void initViews();
    public abstract void initAdapter();
    public abstract void initData();

    public abstract void showProgressBar();
    public abstract void hideProgressBar();

    public abstract void onRefreshAsync();
    public abstract void onAsyncResult(List<T> data);

    public abstract void setAdapter(List<T> data);

}

/*
AsyncTask
Adapter
 */
