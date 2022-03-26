package com.example.Models;

public class Trainee {
    String UID;
    String Name;
    String Email, educationLevel;
    int age;

    public Trainee() {

    }

    public Trainee(String name, String email, String educationLevel, int age, String UID) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
