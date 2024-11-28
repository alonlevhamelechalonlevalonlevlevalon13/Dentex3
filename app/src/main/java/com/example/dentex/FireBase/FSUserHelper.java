package com.example.dentex.FireBase;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class FSUserHelper {
    private static final String TAG = "FireStoreHelper Tag";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionRef;
    private FSUserHelper.FBReply fbReply;

    public interface FBReply {
        void getAllSuccess(ArrayList<User> users);
        void getOneSuccess(User user);
    }

    public FSUserHelper(FSUserHelper.FBReply fbReply) {
        this.fbReply = fbReply;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        collectionRef = db.collection("users").document(currentUser.getUid()).collection("my_users");
    }

    public void add(User user) {
        collectionRef.add(user).addOnSuccessListener(documentReference -> {
            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error adding document", e);
            });
    }

    public void update(String id, User user) {
        collectionRef.document(id).update("Name", user.getName(), "Password", user.getPassword()).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "DocumentSnapshot updated with ID: " + id);
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error updating document", e);
            });
    }
    public void delete(String id) {
        collectionRef.document(id).delete().addOnSuccessListener(aVoid -> {
            Log.d(TAG, "DocumentSnapshot deleted with ID: " + id);
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error deleting document", e);
        });
    }
    public void getAll() {
        collectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> users = new ArrayList<>();
                for (com.google.firebase.firestore.DocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    User user = document.toObject(User.class);
                    users.add(user);
                }
                fbReply.getAllSuccess(users);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });

    }
    public void getOne(String id) {
        collectionRef.document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                com.google.firebase.firestore.DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    User user = document.toObject(User.class);
                    fbReply.getOneSuccess(user);
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    public CollectionReference getCollectionRef() {
        return collectionRef;
    }
}
