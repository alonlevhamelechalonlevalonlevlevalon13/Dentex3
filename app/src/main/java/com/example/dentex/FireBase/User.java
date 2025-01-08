package com.example.dentex.FireBase;

import com.example.dentex.Appointments.Appointment;

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
        if (appointments.isEmpty()||date.getTime()>appointments.get(appointments.size()).getDate().getTime()){
            appointments.add(new Appointment(date,Dname,treatmentType));
        }
        for (int i = 0; i < appointments.size(); i++) {
            if (date.getTime() < this.appointments.get(i).getDate().getTime()){
                appointments.add(i,new Appointment(date,Dname,treatmentType));
            }
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
