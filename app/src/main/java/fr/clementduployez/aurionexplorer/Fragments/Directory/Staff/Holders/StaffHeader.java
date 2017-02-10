package fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import fr.clementduployez.aurionexplorer.Fragments.Directory.Staff.StaffDirectoryFragment;
import fr.clementduployez.aurionexplorer.Fragments.DirectoryFragment;
import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/21/2016.
 */
public class StaffHeader extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final LinearLayout loadingLayout;
    private final LinearLayout formLayout;
    private DirectoryFragment fragment;
    private Button button;
    private EditText lastName;
    private EditText firstName;
    private EditText code;

    public StaffHeader(View itemView, DirectoryFragment fragment) {
        super(itemView);
        this.fragment = fragment;

        button = (Button) itemView.findViewById(R.id.staff_directory_confirm_button);
        lastName = (EditText) itemView.findViewById(R.id.staff_directory_nom);
        firstName = (EditText) itemView.findViewById(R.id.staff_directory_prenom);
        code = (EditText) itemView.findViewById(R.id.staff_directory_code);
        loadingLayout = (LinearLayout) itemView.findViewById(R.id.loadingLayout);
        formLayout = (LinearLayout) itemView.findViewById(R.id.formLayout);

        button.setOnClickListener(this);
    }

    public void bind(boolean isStaff) {
        if (isStaff) {
            this.code.setHint("Code");
        }
        else {
            this.code.setHint("Groupe");
        }
    }

    @Override
    public void onClick(View v) {
        fragment.sendForm(this.lastName.getText().toString(), this.firstName.getText().toString(), this.code.getText().toString());
    }

    public void showProgressBar() {
        formLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        formLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }
}
