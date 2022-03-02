package com.example.Models;

public class Contacts {
    String Name;
    String Email;
    String Region;
    String Commercial_register;
    int Contact_number;

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

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getCommercial_register() {
        return Commercial_register;
    }

    public void setCommercial_register(String commercial_register) {
        Commercial_register = commercial_register;
    }

    public int getContact_number() {
        return Contact_number;
    }

    public void setContact_number(int contact_number) {
        Contact_number = contact_number;
    }
}
