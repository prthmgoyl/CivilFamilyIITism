package com.example.civilfamilyiitism;

public class noticemodel {
    String uid , time , message ,year, username , timeone , to;

    public noticemodel() {
    }

    public noticemodel(String uid, String time, String message, String to , String username , String timeone , String year) {
        this.uid = uid;
        this.time = time;
        this.message = message;
        this.year=year;
        this.username = username;
        this.timeone = timeone;
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTimeone() {
        return timeone;
    }

    public void setTimeone(String timeone) {
        this.timeone = timeone;
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
