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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements FBAuthHelper.FBReply, FBUserHelper.FBReply{

    private FirebaseAuth mAuth;
    private static final String TAG = "alon";
    private String email = "alonlevalon@gmail.com";
    private String password = "alon0312";
    private EditText EtEmail;
    private EditText EtPassword;
    private EditText EtConfirm;
    private EditText EtName;
    private Button BtnSignUp;
    private FBAuthHelper fbAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fbAuthHelper = new FBAuthHelper(this, this);
        //fsUserHelper = new FBUserHelper( this);
        EtEmail = findViewById(R.id.ETE2);
        EtPassword = findViewById(R.id.ETP3);
        EtName = findViewById(R.id.ETN);
        EtConfirm = findViewById(R.id.ETP4);
        BtnSignUp = findViewById(R.id.btnS);
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkEmailValidity(EtEmail.getText().toString()) && checkPasswordValidity(EtPassword.getText().toString(), EtConfirm.getText().toString()) && EtName.getText().toString()!=null){
                    fbAuthHelper.createUser(EtEmail.getText().toString(), EtPassword.getText().toString());
                }
                else Toast.makeText(RegisterActivity.this, "error creating user", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    private boolean checkPasswordValidity(String password, String confirm) {
        if (password.length() >= 6 && password.equals(confirm)) {
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
        User user1 = new User(EtName.getText().toString());
        FBUserHelper fbUserHelper = new FBUserHelper(this);
        fbUserHelper.add(user1);
    }

    @Override
    public void loginSuccess(FirebaseUser user) {
        startActivity(new Intent(this, PatientActivity.class));
    }
    public void createUserFail(){
        Toast.makeText(RegisterActivity.this,"error creating user", Toast.LENGTH_LONG).show();
    }

    @Override
    public void getAllSuccess(ArrayList<User> users) {

    }

    @Override
    public void getOneSuccess(User user) {

    }

    @Override
    public void addUserSuccess(String id) {
        startActivity(new Intent(this, PatientActivity.class));
    }
}