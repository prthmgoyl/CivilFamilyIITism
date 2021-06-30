package com.example.civilfamilyiitism;

public class noticemodel {
    String uid , time , message ,year, username;

    public noticemodel() {
    }

    public noticemodel(String uid, String time, String message, String year , String username) {
        this.uid = uid;
        this.time = time;
        this.message = message;
        this.year=year;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
