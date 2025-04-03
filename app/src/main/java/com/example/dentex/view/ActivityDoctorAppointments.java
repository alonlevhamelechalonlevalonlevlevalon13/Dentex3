package com.example.dentex.view;

import static com.example.dentex.utils.FBAuthHelper.mAuth;
import static com.example.dentex.utils.FBUserHelper.DataBase;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dentex.model.Appointment;
import com.example.dentex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityDoctorAppointments extends AppCompatActivity implements View.OnClickListener{

    Button btnDatePicker, buttonSubmit, btnLogOut;
    EditText name, treatment;
    private int currentYear, currentMonth, currentDay, currentHour, currentMinute;
    private int Year1, Month1, Day1;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments);
        btnDatePicker=findViewById(R.id.button6);
        btnDatePicker.setOnClickListener(this);
        buttonSubmit = findViewById(R.id.button4);
        name = findViewById(R.id.editTextText);
        treatment = findViewById(R.id.editTextText2);
        buttonSubmit.setOnClickListener(this);
        btnLogOut = findViewById(R.id.button5);
        btnLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            currentYear = c.get(Calendar.YEAR);
            currentMonth = c.get(Calendar.MONTH);
            currentDay = c.get(Calendar.DAY_OF_MONTH);
            
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
                    }, currentYear, currentMonth, currentDay);
            datePickerDialog.show();
        }
        if (v == buttonSubmit){
            if (date==null || name==null || treatment==null)
                Toast.makeText(this, "please fill in every detail", Toast.LENGTH_SHORT).show();
            else {
                DataBase.collection("openappointments").
                        add(new Appointment(date, name.getText().toString(), treatment.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(ActivityDoctorAppointments.this, "appointment added to database", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        if (v==btnLogOut){
            setupDialog();
        }
    }
    private void setupDialog() {
        new AlertDialog.Builder(this)
                .setTitle("are you sure?")
                .setMessage("are you sure you want to log out?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        startActivity(new Intent(ActivityDoctorAppointments.this, LoginActivity.class));
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
    private void setTime(){
        final Calendar c = Calendar.getInstance();
        currentHour = c.get(Calendar.HOUR_OF_DAY);
        currentMinute = c.get(Calendar.MINUTE);

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
                }, currentHour, currentMinute, true);
        timePickerDialog.show();
    }


}