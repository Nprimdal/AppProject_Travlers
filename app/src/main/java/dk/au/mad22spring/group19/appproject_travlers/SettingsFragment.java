package dk.au.mad22spring.group19.appproject_travlers;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class SettingsFragment extends Fragment {

    private TripViewModel tripViewModel;
    private Button btnLogout;
    //FirebaseUser user;
    private FloatingActionButton fabSave;
    //private FirebaseAuth mAuth;
    private EditText edtOldPassword, edtNewPassword, edtComfirmPassword;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragtment_settings, container, false);

        //Set up view model
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        //Set up views
        edtComfirmPassword = (EditText) view.findViewById(R.id.edtConfirmPassword);
        edtOldPassword = (EditText) view.findViewById(R.id.edtOldPassword);
        edtNewPassword = (EditText) view.findViewById(R.id.edtNewPassword);

        //Set up save button
        fabSave = (FloatingActionButton) view.findViewById(R.id.fabSaveSettings);
        fabSave.setEnabled(false);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });

        viewsEdited();

        //Set up logout button
        btnLogout = view.findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        return view;
    }

    private void logout() {
        tripViewModel.logout();
        getActivity().finish();
        gotoLogin();
    }

    private void gotoLogin() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
    }

    private void saveSettings() {

        String newPassword = edtNewPassword.getText().toString();
        String confirmPassword = edtComfirmPassword.getText().toString();

        if(!edtOldPassword.getText().toString().isEmpty() && !edtNewPassword.getText().toString().isEmpty() && !edtComfirmPassword.getText().toString().isEmpty()){
            if(newPassword.equals(confirmPassword)){
                tripViewModel.updatePasswordDB(edtNewPassword.getText().toString());
                }
            else{
                Toast.makeText(getContext(), "New Password and Confirm Password are not matching", Toast.LENGTH_SHORT).show();
                }
        }
        else{
            Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewsEdited() {
        ArrayList<EditText> editTexts = new ArrayList();
        editTexts.add(edtNewPassword);
        editTexts.add(edtComfirmPassword);

        for (EditText editText: editTexts){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.toString().trim().length() == 0){
                        fabSave.setEnabled(false);
                    }
                    else{
                        fabSave.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}

