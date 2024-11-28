package com.example.dentex.FireBase;

public class User {
    String name;
    String password;
    String userType;

    public User(String name, String password, String password2) {
        this.name = name;
        this.userType = "Patient";
        if (password == password2)
            this.password = password;
    }

    public User(String name, String password, String password2, String userType) {
        this.name = name;
        if (password == password2)
            this.password = password;
        if (userType.equals("Doctor")||userType.equals("Clinic"))
            this.userType = userType;
        else {
            this.userType = "Patient";
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
