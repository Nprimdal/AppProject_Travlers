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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Reference: Demo from lecture

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "AUTH";
    // UI widgets
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtCreateAccount;
    private LoginViewModel loginVM;

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginVM = new ViewModelProvider(this).get(LoginViewModel.class);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { login(); }
        });

        txtCreateAccount = findViewById(R.id.txtCreateAccount);
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goToCreateNewAccount(); }
        });

    }


    private void login() {
    String email = edtEmail.getText().toString();
    String password = edtPassword.getText().toString();
    if (email==null || email.length()<1||password==null||password.length()<1){
        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        return;
    }
    loginVM.Login(email, password, this);
    loginVM.userLoggedIn().observe(this, new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if (aBoolean){
                Log.d(TAG, "Login successful");
                goToSecurePart();
            }
            else {
                Log.d(TAG, "Login failed: ");
                Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
            }

        }
    });
}

    private void goToCreateNewAccount() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void goToSecurePart(){
        Intent i = new Intent(this, TripActivity.class);
        startActivity(i);
        finish();
    }


}