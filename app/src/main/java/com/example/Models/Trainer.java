package com.example.Models;

public class Trainer {
    String Name;
    String Gender;
    String Email;
    String Specialization;
    String Nationality;
    int contact_number;

    public Trainer(String name, String gender, String email, String specialization, String nationality, int contact_number) {
        Name = name;
        Gender = gender;
        Email = email;
        Specialization = specialization;
        Nationality = nationality;
        this.contact_number = contact_number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public int getContact_number() {
        return contact_number;
    }

    public void setContact_number(int contact_number) {
        this.contact_number = contact_number;
    }
}
