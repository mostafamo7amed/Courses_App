package com.example.Models;

import android.provider.ContactsContract;

public class Comments {
    String email;
    String name;
    String comment;
    String time;
    String date;
    String key;
    String uid ,type;

    public Comments() {
    }

    public Comments(String email, String name, String comment, String time, String date, String key, String UID, String type) {
        this.email = email;
        this.name = name;
        this.comment = comment;
        this.time = time;
        this.date = date;
        this.key = key;
        this.uid = UID;
        this.type = type;
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

    public String getUID() {
        return uid;
    }

    public void setUID(String UID) {
        this.uid = UID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
