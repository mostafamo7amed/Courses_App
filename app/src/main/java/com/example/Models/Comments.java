package com.example.Models;

import android.provider.ContactsContract;

public class Comments {
    String email;
    String name;
    String comment;
    String time;
    String date;
    String key;

    public Comments() {
    }

    public Comments(String email, String name, String comment, String time, String date,String key) {
        this.email = email;
        this.name = name;
        this.comment = comment;
        this.time = time;
        this.date = date;
        this.key = key;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String UID) {
        this.key = UID;
    }
}
