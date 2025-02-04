package com.example.dentex.FireBase;

import com.example.dentex.Appointments.Appointment;

import java.util.ArrayList;
import java.util.Date;

public class User {
    String name;
    ArrayList<Appointment> appointments;
    Boolean isDoc;

    public User(String name) {
        this.name = name;
        this.isDoc = false;
        this.appointments = new ArrayList<>();
    }

    public User(String name, String userType) {
        this.name = name;
        if (userType.equals("Doctor"))
            this.isDoc = true;
        else {
            this.isDoc = false;
            this.appointments = new ArrayList<>();
        }

    }
    public void addAppointment(Appointment appointment){
        if (appointments.isEmpty()||appointment.getDate().getTime()>appointments.get(appointments.size()).getDate().getTime()){
            appointments.add(appointment);
        }
        for (int i = 0; i < appointments.size(); i++) {
            if (appointment.getDate().getTime() < this.appointments.get(i).getDate().getTime()){
                appointments.add(i,appointment);
            }
        }
    }

    public Boolean getDoc() {
        return isDoc;
    }

    public void setDoc(Boolean doc) {
        isDoc = doc;
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
