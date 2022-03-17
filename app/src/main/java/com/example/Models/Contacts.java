package com.example.Models;

public class Contacts {
    String Name;
    String Email;
    String phone;
    String Region;
    String Commercial_register;
    int Contact_number;

    public Contacts(String name, String email, String phone, String region, String commercial_register, int contact_number) {
        Name = name;
        Email = email;
        this.phone = phone;
        Region = region;
        Commercial_register = commercial_register;
        Contact_number = contact_number;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
