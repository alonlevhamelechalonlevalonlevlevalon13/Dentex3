package com.example.dentex.FireBase;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FBAuthHelper {
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private FBReply fbReply;
    private static final String TAG = "alon";
    Activity activity;


    public interface FBReply{
        public void createUserSuccess(FirebaseUser user);
        public void loginSuccess(FirebaseUser user);
    }

    public FBAuthHelper(Activity activity, FBReply fbReply) {
        this.activity =activity;
        this.fbReply = fbReply;
    }

    public static FirebaseUser getCurrentUser() {return mAuth.getCurrentUser();}
    public boolean isLoggedIn() {return getCurrentUser() != null;}

    public void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((OnCompleteListener<AuthResult>) new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(fbReply != null)
                                fbReply.createUserSuccess(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(fbReply != null)
                                fbReply.loginSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity,"log in failed",Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                        }
                    }
                });
    }
    public void logout(){
        mAuth.signOut();
    }
}
