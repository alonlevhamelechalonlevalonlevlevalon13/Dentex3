package com.example.dentex.Doctors;

import static com.example.dentex.FireBase.FBUserHelper.db;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dentex.Appointments.Appointment;
import com.example.dentex.FireBase.FBAuthHelper;
import com.example.dentex.FireBase.FBUserHelper;
import com.example.dentex.FireBase.User;
import com.example.dentex.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DoctorAppointments extends AppCompatActivity implements FBUserHelper.FBReply {
    FBUserHelper fbUserHelper;
    User user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments);
        Button buttonGen = findViewById(R.id.buttonGenerate);
        fbUserHelper = new FBUserHelper(this);
        fbUserHelper.getOne(FBAuthHelper.getCurrentUser().getUid());
        buttonGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH,7);
                Date date=calendar.getTime();
                for (int i = 0; i < 10; i++) {
                    calendar.add(Calendar.MINUTE,30);
                    date=calendar.getTime();

                    db.collection("openappointments").
                            add(new Appointment(date, user1.getName(), "fbdsTODO"));
                }
            }

        });
    }

    @Override
    public void getAllSuccess(ArrayList<User> users) {}

    @Override
    public void getOneSuccess(User user) {
        user1 = user;
    }

    @Override
    public void addUserSuccess(String id) {}
}