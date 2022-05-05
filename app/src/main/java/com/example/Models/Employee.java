package com.example.Models;

public class Employee {
    String email , name ,position,uid;
    String number;
    String gender;
    String age;
    String type;


    public Employee() {
    }

    public Employee(String email, String name, String position, String uid, String age, String number) {
        this.email = email;
        this.name = name;
        this.position = position;
        this.uid = uid;
        this.age = age;
        this.number = number;
    }

    public Employee(String email, String name, String position, String uid, String number, String gender, String age, String type) {
        this.email = email;
        this.name = name;
        this.position = position;
        this.uid = uid;
        this.number = number;
        this.gender = gender;
        this.age = age;
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
