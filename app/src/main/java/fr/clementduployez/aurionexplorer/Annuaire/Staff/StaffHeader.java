package fr.clementduployez.aurionexplorer.Annuaire.Staff;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.clementduployez.aurionexplorer.R;

/**
 * Created by cdupl on 2/21/2016.
 */
public class StaffHeader extends RecyclerView.ViewHolder implements View.OnClickListener {

    private StaffDirectoryFragment fragment;
    private Button button;
    private EditText lastName;
    private EditText firstName;
    private EditText code;

    public StaffHeader(View itemView, StaffDirectoryFragment fragment) {
        super(itemView);
        this.fragment = fragment;

        button = (Button) itemView.findViewById(R.id.staff_directory_confirm_button);
        lastName = (EditText) itemView.findViewById(R.id.staff_directory_nom);
        firstName = (EditText) itemView.findViewById(R.id.staff_directory_prenom);
        code = (EditText) itemView.findViewById(R.id.staff_directory_code);

        button.setOnClickListener(this);
    }

    public void bind() {

    }

    @Override
    public void onClick(View v) {
        fragment.sendForm(this.lastName.getText().toString(),this.firstName.getText().toString(),this.code.getText().toString());
    }
}
