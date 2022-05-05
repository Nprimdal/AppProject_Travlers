package dk.au.mad22spring.group19.appproject_travlers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Reference: https://www.youtube.com/watch?v=0gNPX52o_7I

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "AUTH";
    //Ui widgets
    private EditText edtFullName;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnCreateNewAccount;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String USER = "user";
    private User user;
    private RegisterViewModel registerVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerVM = new ViewModelProvider(this).get(RegisterViewModel.class);

        edtFullName = findViewById(R.id.edtCreateNewName);
        edtEmail = findViewById(R.id.edtCreateNewEmail);
        edtPassword = findViewById(R.id.edtCreateNewPassword);
        btnCreateNewAccount = findViewById(R.id.btnCreateNewAccount);
        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if(email==null||email.length()<1||password==null||password.length()<1){
                    Toast.makeText(RegisterActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    return;
                }
                String fullName = edtFullName.getText().toString();
                user = new User(email, fullName);
                createNewAccount(password);
            }
        });

        database = FirebaseDatabase.getInstance("https://appproject-travlers-default-rtdb.firebaseio.com");
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();
    }

    private void createNewAccount(String password) {

        registerVM.createNewAccount(user, password,this);
        registerVM.userCreated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    Log.d(TAG, "Account created");
                    Toast.makeText(RegisterActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                    goToLogin();
                }
                else {
                    Log.d(TAG, "Could not create account");
                    Toast.makeText(RegisterActivity.this, "FAILED to create account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*mAuth.createUserWithEmailAndPassword(email, password)       //call to create a new user and set callbacks
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.d(TAG, "Account created");
                            Toast.makeText(RegisterActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);
                            //goToLogin();
                        } else {
                            Log.d(TAG, "Could not create account", task.getException());
                            Toast.makeText(RegisterActivity.this, "FAILED to create account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }

    private void goToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void updateUI(FirebaseUser currentUser) {
        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).child(mAuth.getUid()).setValue(user);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}