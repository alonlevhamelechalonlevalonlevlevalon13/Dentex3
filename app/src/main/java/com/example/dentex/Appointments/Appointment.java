package com.example.dentex.Appointments;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    private Date date;
    private String Drname;
    private String TreatmentType;

    public Appointment(Date date, String Drname, String treatmentType) {
        this.date = date;
        this.Drname = Drname;
        TreatmentType = treatmentType;
    }

    public Appointment(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDrname() {
        return Drname;
    }

    public void setDrname(String drname) {
        Drname = drname;
    }

    public String getTreatmentType() {
        return TreatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        TreatmentType = treatmentType;
    }
}
