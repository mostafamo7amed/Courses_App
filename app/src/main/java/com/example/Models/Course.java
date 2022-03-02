package com.example.Models;

public class Course {
    String field;
    String description;
    String trainer;
    String address;
    int contactNumber;
    String date;
    String time;

    public Course(String field, String description, String trainer, String address, int contactNumber, String date, String time) {
        this.field = field;
        this.description = description;
        this.trainer = trainer;
        this.address = address;
        this.contactNumber = contactNumber;
        this.date = date;
        this.time = time;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
