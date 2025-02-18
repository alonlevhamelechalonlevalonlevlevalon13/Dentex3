package com.example.dentex.model;

import java.util.Date;
import java.util.UUID;

public class Appointment{
    public Date date;
    public String drname;
    public String treatmentType;
    public UUID alarm;

    public Appointment(Date date, String Drname, String treatmentType) {
        this.date = date;
        this.drname = Drname;
        this.treatmentType = treatmentType;
    }

    public UUID getAlarm() {
        return alarm;
    }

    public void setAlarm(UUID alarm) {
        this.alarm = alarm;
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
