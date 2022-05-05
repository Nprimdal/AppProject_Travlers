package dk.au.mad22spring.group19.appproject_travlers;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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


public class SettingsFragment extends Fragment {

    private TripViewModel tripViewModel;
    private TripModel trip;
    private TextView txtUserName, txtUserPassword;
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


        //user = mAuth.getCurrentUser();


        //Set up view model
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        //Set up views
        edtComfirmPassword = (EditText) view.findViewById(R.id.edtConfirmPassword);
        edtOldPassword = (EditText) view.findViewById(R.id.edtOldPassword);
        edtNewPassword = (EditText) view.findViewById(R.id.edtNewPassword);


        //Set up save button
        fabSave = (FloatingActionButton) view.findViewById(R.id.fabSaveSettings);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });

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
            //if(edtOldPassword.getText().toString() == user.){
                if(newPassword.equals(confirmPassword)){
                    tripViewModel.updatePasswordDB(edtNewPassword.getText().toString());
                }
                else{
                    Toast.makeText(getContext(), "New Password and Confirm Password are not matching", Toast.LENGTH_SHORT).show();
                }
            /*else{
                Toast.makeText(getContext(), "Current Password is incorrect", Toast.LENGTH_SHORT).show();
            }*/
        }
        else{
            Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
        }

    }
}

