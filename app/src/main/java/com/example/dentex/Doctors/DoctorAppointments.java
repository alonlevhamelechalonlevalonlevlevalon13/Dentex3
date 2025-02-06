package com.example.dentex.Doctors;

import static android.content.ContentValues.TAG;
import static com.example.dentex.FireBase.FBUserHelper.db;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dentex.Appointments.Appointment;
import com.example.dentex.FireBase.FBAuthHelper;
import com.example.dentex.FireBase.FBUserHelper;
import com.example.dentex.FireBase.User;
import com.example.dentex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DoctorAppointments extends AppCompatActivity implements
        View.OnClickListener, FBUserHelper.FBReply {

    Button btnDatePicker, button4;
    EditText name, treatment;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int Year1, Month1, Day1;
    private Date date;
    private User user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments);
        btnDatePicker=findViewById(R.id.button6);
        btnDatePicker.setOnClickListener(this);
        button4 = findViewById(R.id.button4);
        name = findViewById(R.id.editTextText);
        treatment = findViewById(R.id.editTextText2);
        button4.setOnClickListener(this);
        FBUserHelper fbUserHelper = new FBUserHelper(this);
        fbUserHelper.getOne(FBAuthHelper.getCurrentUser().getUid());
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Year1 = year;
                            Month1 = monthOfYear;
                            Day1 = dayOfMonth;
                            setTime();

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == button4){
            if (date==null || name==null || treatment==null)
                Toast.makeText(this, "בבקשה מלא/י את כל השדות", Toast.LENGTH_SHORT).show();
            else {
                db.collection("openappointments").
                        add(new Appointment(date, name.getText().toString(), treatment.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Log.d(TAG, "onComplete: "+task.toString());
                                Log.d(TAG, "onComplete: "+task.toString());
                                Log.d(TAG, "onComplete: "+task.toString());
                                Log.d(TAG, "onComplete: "+task.toString());
                                Log.d(TAG, "onComplete: "+task.toString());
                                Log.d(TAG, "onComplete: "+task.toString());

                            }
                        });
                Toast.makeText(this, "התור נוסף בהצלחה", Toast.LENGTH_SHORT).show();
            }
        }

            // Get Current Time

    }
    private void setTime(){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        date = new Date(Year1,Month1,Day1,hourOfDay,minute);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
                        btnDatePicker.setText(dateFormat.format(date));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void getAllSuccess(ArrayList<User> users) {

    }

    @Override
    public void getOneSuccess(User user) {
        user1 = user;
    }

    @Override
    public void addUserSuccess(String id) {

    }
}
//        extends AppCompatActivity implements FBUserHelper.FBReply {
//    FBUserHelper fbUserHelper;
//    User user1;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_doctor_appointments);
//        Button buttonGen = findViewById(R.id.buttonGenerate);
//        fbUserHelper = new FBUserHelper(this);
//        fbUserHelper.getOne(FBAuthHelper.getCurrentUser().getUid());
//        buttonGen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.add(Calendar.DAY_OF_MONTH,7);
//                Date date;
//                for (int i = 0; i < 10; i++) {
//                    calendar.add(Calendar.MINUTE,30);
//                    date=calendar.getTime();
//
//                    db.collection("openappointments").
//                            add(new Appointment(date, user1.getName(), "fbdsTODO"));
//                }
//            }
//
//        });
//    }
//
//    @Override
//    public void getAllSuccess(ArrayList<User> users) {}
//
//    @Override
//    public void getOneSuccess(User user) {
//        user1 = user;
//    }
//
//    @Override
//    public void addUserSuccess(String id) {}
