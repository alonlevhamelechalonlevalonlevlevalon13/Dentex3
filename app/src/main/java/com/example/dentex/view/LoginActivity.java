package com.example.dentex.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dentex.utils.FBAuthHelper;
import com.example.dentex.utils.FBUserHelper;
import com.example.dentex.model.User;
import com.example.dentex.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements FBAuthHelper.FBReply, FBUserHelper.FBReply {
    private EditText EtEmail;
    private EditText EtPassword;
    private Button BtnLogIn;
    private Button BtnSignUp;
    private FBAuthHelper fbAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fbAuthHelper = new FBAuthHelper(this, this);
        if (fbAuthHelper.isLoggedIn()) {
            if(FBAuthHelper.getCurrentUser().getEmail().equals("manager123@gmail.com"))
                startActivity(new Intent(this, ActivityDoctorAppointments.class));
            else
                startActivity(new Intent(this, PatientActivity.class));
        }
        EtEmail = findViewById(R.id.ETE);
        EtPassword = findViewById(R.id.ETP);
        BtnSignUp = findViewById(R.id.BtnS);
        BtnLogIn = findViewById(R.id.BtnL);
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        BtnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmailValidity(EtEmail.getText().toString()) && checkPasswordValidity(EtPassword.getText().toString())) {
                    fbAuthHelper.login(EtEmail.getText().toString(), EtPassword.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "error logging in", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private boolean checkPasswordValidity(String password) {
        if (password.length() >= 6) {
            // Password is valid
            return true;
        } else {
            // Password is invalid, show an error message
            EtPassword.setError("Password must be 6 characters or longer");
            return false;
        }
    }

    private boolean checkEmailValidity(String email) {
        if (EtEmail != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Email is valid
            return true;
        } else {
            // Email is invalid, show an error message
            EtEmail.setError("Invalid email address");
            return false;
        }
    }

    @Override
    public void createUserSuccess(FirebaseUser user) {
        startActivity(new Intent(this, PatientActivity.class));
    }

    @Override
    public void loginSuccess(FirebaseUser user) {
        if(user.getEmail().equals("manager123@gmail.com"))
            startActivity(new Intent(this, ActivityDoctorAppointments.class));
        else
            startActivity(new Intent(this, PatientActivity.class));
    }

    @Override
    public void getAllSuccess(ArrayList<User> users) {
    }

    @Override
    public void getOneSuccess(User user) {
    }

    @Override
    public void addUserSuccess(String id) {
    }
}