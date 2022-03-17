package com.example.Models;

public class Trainee {
    String Name;
    String Email, educationLevel;
    int age;

    public Trainee(String name, String email, String educationLevel, int age) {
        Name = name;
        Email = email;
        this.educationLevel = educationLevel;
        this.age = age;
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
