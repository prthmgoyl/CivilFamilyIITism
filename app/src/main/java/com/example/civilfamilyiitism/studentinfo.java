package com.example.civilfamilyiitism;

public class studentinfo {
    private String username,email,phone , password,uid,year,designation , imgurl;

    public studentinfo() {
    }

    public studentinfo(String username, String email, String phone, String password,String uid,String year,String designation , String imgurl) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.uid = uid;
        this.year = year;
        this.designation = designation;
        this.imgurl = imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
