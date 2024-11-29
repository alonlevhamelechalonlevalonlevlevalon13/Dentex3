package com.example.dentex.FireBase;

import com.example.dentex.Patient.Appointment;

import java.util.ArrayList;
import java.util.Date;

public class User {
    String name;
    ArrayList<Appointment> appointments;
    String userType;

    public User(String name) {
        this.name = name;
        this.userType = "Patient";
        this.appointments = new ArrayList<>();
    }

    public User(String name, String userType) {
        this.name = name;
        if (userType.equals("Doctor")||userType.equals("Clinic"))
            this.userType = userType;
        else {
            this.userType = "Patient";
        }

    }
    public void addAppointment(Date date, String Dname, String treatmentType){
        appointments.add(new Appointment(date,Dname,treatmentType));
    }
    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
}
