package com.example.Models;

import android.provider.ContactsContract;

public class Comments {
    String email;
    String name;
    String comment;
    int time;
    int date;

    public Comments(String email, String name, String comment, int time, int date) {
        this.email = email;
        this.name = name;
        this.comment = comment;
        this.time = time;
        this.date = date;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
