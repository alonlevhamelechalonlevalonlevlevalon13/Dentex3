package com.example.dentex.Patient;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    private final Date date;
    private final String Drname;
    private final String TreatmentType;

    public Appointment(Date date, String Drname, String treatmentType) {
        this.date = date;
        this.Drname = Drname;
        TreatmentType = treatmentType;
    }

    public String getTreatmentType() {
        return TreatmentType;
    }

    public Date getdate() {
        return date;
    }

    public String getDrname() {
        return Drname;
    }
}
