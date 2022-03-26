package com.example.Models;

public class Course {
    String field ,courseMaterial ;
    String description;
    String trainer;
    String address;
    int contactNumber ,courseNumber;
    String date;
    String time;
    String key;

    public Course() {
    }

    public Course(String field, String courseMaterial, String description, String trainer, String address, int contactNumber, int courseNumber, String date, String time, String key) {
        this.field = field;
        this.courseMaterial = courseMaterial;
        this.description = description;
        this.trainer = trainer;
        this.address = address;
        this.contactNumber = contactNumber;
        this.courseNumber = courseNumber;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    public Course(String field, String courseMaterial, String description, String trainer, String address, int contactNumber, int courseNumber, String date, String time) {
        this.field = field;
        this.courseMaterial = courseMaterial;
        this.description = description;
        this.trainer = trainer;
        this.address = address;
        this.contactNumber = contactNumber;
        this.courseNumber = courseNumber;
        this.date = date;
        this.time = time;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCourseMaterial() {
        return courseMaterial;
    }

    public void setCourseMaterial(String courseMaterial) {
        this.courseMaterial = courseMaterial;
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

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
