package com.example.Models;

public class Employee {
    String email , name ,position,uid;
    int number;
    String age;


    public Employee() {
    }

    public Employee(String email, String name, String position, String uid, String age, int number) {
        this.email = email;
        this.name = name;
        this.position = position;
        this.uid = uid;
        this.age = age;
        this.number = number;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
