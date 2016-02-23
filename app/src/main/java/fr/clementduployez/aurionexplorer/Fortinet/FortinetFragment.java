package fr.clementduployez.aurionexplorer.Fortinet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import fr.clementduployez.aurionexplorer.Informer;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/23/2016.
 */
public class FortinetFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private Button fortinetButton;
    private LinearLayout loadingLayout;
    private FortinetAuthenticationAsync fortinetAuthenticateAsync;

    public static FortinetFragment newInstance() {
        final FortinetFragment fortinetFragment = new FortinetFragment();
        return fortinetFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fortinet, container, false);

        loadingLayout = (LinearLayout) rootView.findViewById(R.id.loadingLayout);
        fortinetButton = (Button) rootView.findViewById(R.id.fortinetButton);

        fortinetButton.setOnClickListener(this);

        return rootView;
    }

    public void sendForm() {
        fortinetAuthenticateAsync = new FortinetAuthenticationAsync(this);
        fortinetAuthenticateAsync.execute();
        showProgressBar();
    }

    public void updateSubtitle(boolean result) {
        String text = "Authentifié";
        if (!result) {
            text = "Non "+text;
        }
        ((AppCompatActivity) (this.getActivity())).getSupportActionBar().setSubtitle(text);
    }

    public void onAsyncResult(Boolean result) {
        fortinetAuthenticateAsync = null;
        hideProgressBar();
        if (result == null) {
            showAlreadyLayout();
        }
        else if (result) {
            showSuccessLayout();
        }
        else {
            showErrorLayout();
        }
    }

    public void showSuccessLayout() {
        Informer.inform("Success");
    }

    public void showAlreadyLayout() {
        Informer.inform("Already authenticated");
    }

    public void showErrorLayout() {
        Informer.inform("Error");
    }

    public void showProgressBar() {

    }

    public void hideProgressBar() {

    }

    @Override
    public void onClick(View v) {
        sendForm();
    }
}
