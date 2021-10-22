package com.example.crud.model;

public class Members {
    private String first_name,last_name,phone,gender;

    public Members() {
    }

    public Members(String first_name, String last_name, String phone, String gender) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.gender = gender;
    }
    public Members(String first_name, String last_name, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
