package com.example.dentex.FireBase;

import com.example.dentex.Appointments.Appointment;

import java.util.ArrayList;
import java.util.Date;

public class User {
    String name;
    ArrayList<Appointment> appointments;

    public User(String name) {
        this.name = name;
        this.appointments = new ArrayList<>();
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
