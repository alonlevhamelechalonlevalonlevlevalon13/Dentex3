package com.example.dentex.view.Fragments;

import static com.example.dentex.utils.FBAuthHelper.mAuth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dentex.R;
import com.example.dentex.view.LoginActivity;

public class pt_home_fr extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public pt_home_fr() {
    }

    public static pt_home_fr newInstance(String param1, String param2) {
        pt_home_fr fragment = new pt_home_fr();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button BtnLogOut = view.findViewById(R.id.button2);
        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupDialog();

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private void setupDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("האם אתה בטוח?")
                .setMessage("אתה בטוח שאתה רוצה להתנתק?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                })
                .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}