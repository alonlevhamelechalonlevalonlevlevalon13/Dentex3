package com.example.dentex.Appointments;

import android.app.AlarmManager;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

public class Appointment{
    public Date date;
    public String drname;
    public String treatmentType;

    public Appointment(Date date, String Drname, String treatmentType) {
        this.date = date;
        this.drname = Drname;
        this.treatmentType = treatmentType;
    }

    public String getDrname() {
        return drname;
    }

    public void setDrname(String drname) {
        this.drname = drname;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public Appointment(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
