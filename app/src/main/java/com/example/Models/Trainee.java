package com.example.Models;

public class Trainee {
    String UID;
    String Name;
    String Email, educationLevel;
    String age;
    String type;

    public Trainee() {

    }

    public Trainee(String name, String email, String educationLevel, String age, String UID) {
        this.Name = name;
        this.Email = email;
        this.educationLevel = educationLevel;
        this.age = age;
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
