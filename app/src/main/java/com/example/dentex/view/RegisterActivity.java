package com.example.dentex.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dentex.FireBase.FBAuthHelper;
import com.example.dentex.FireBase.FBUserHelper;
import com.example.dentex.FireBase.User;
import com.example.dentex.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements FBAuthHelper.FBReply, FBUserHelper.FBReply{

    private FirebaseAuth mAuth;
    private static final String TAG = "alon";
    private String email = "alonlevalon@gmail.com";
    private String pass = "alon0312";
    private EditText EtE;
    private EditText EtP;
    private EditText EtP2;
    private EditText ETN;
    private Button BtnS;
    private FBAuthHelper fbAuthHelper;///
    private FBUserHelper fsUserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fbAuthHelper = new FBAuthHelper(this, this);
        //fsUserHelper = new FBUserHelper( this);
        EtE = findViewById(R.id.ETE2);
        EtP = findViewById(R.id.ETP3);
        ETN = findViewById(R.id.ETN);
        EtP2 = findViewById(R.id.ETP4);
        BtnS = findViewById(R.id.btnS);
        BtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkEmailValidity(EtE.getText().toString()) && checkPasswordValidity(EtP.getText().toString(), EtP2.getText().toString()) && ETN.getText().toString()!=null){
                    email = EtE.getText().toString();
                    pass = EtP.getText().toString();
                    fbAuthHelper.createUser(email, pass);
                }
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
            EtP.setError("Password must be 6 characters or longer");
            return false;
        }
    }
    private boolean checkEmailValidity(String email) {
        if (EtE != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Email is valid
            return true;
        } else {
            // Email is invalid, show an error message
            EtE.setError("Invalid email address");
            return false;
        }
    }
    @Override
    public void createUserSuccess(FirebaseUser user) {
        User user1 = new User(ETN.getText().toString());
        FBUserHelper fbUserHelper = new FBUserHelper(this);
        fbUserHelper.add(user1);
        Toast.makeText(this, "creating user...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(FirebaseUser user) {
        startActivity(new Intent(this, PatientActivity.class));
    }
    public void createUserFail(){
        Toast.makeText(RegisterActivity.this,"יצירת המשתמש נכשלה", Toast.LENGTH_LONG).show();
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