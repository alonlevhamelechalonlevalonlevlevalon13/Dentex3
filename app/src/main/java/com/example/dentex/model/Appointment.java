package com.example.dentex.model;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.UUID;

public class Appointment {
    public Date date;
    public String drname;
    public String treatmentType;
    public String alarm;

    public Appointment(Date date, String Drname, String treatmentType) {
        this.date = date;
        this.drname = Drname;
        this.treatmentType = treatmentType;
    }

    @Exclude
    public UUID getAlarmUUID() {
        if(alarm != null)
            return UUID.fromString(alarm);
        else
            return null;
    }

    public String getAlarm() {
        return this.alarm;
    }

    public void setAlarm(String alarm) {
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

    public Appointment(){
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Exclude
    public void setAlarmUUID(UUID id) {
        if (id ==null)
            this.alarm = null;
        else
            this.alarm = id.toString();
    }
}
