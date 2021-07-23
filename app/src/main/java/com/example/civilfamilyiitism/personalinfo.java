package com.example.civilfamilyiitism;

public class personalinfo {
    String visibility , alternatephone , fatherphone , motherphone ,
    presentaddress , permanentaddress , dob;

    public personalinfo() {
    }

    public personalinfo(String visibility, String alternatephone, String fatherphone, String motherphone, String presentaddress, String permanentaddress, String dob) {
        this.visibility = visibility;
        this.alternatephone = alternatephone;
        this.fatherphone = fatherphone;
        this.motherphone = motherphone;
        this.presentaddress = presentaddress;
        this.permanentaddress = permanentaddress;
        this.dob = dob;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getAlternatephone() {
        return alternatephone;
    }

    public void setAlternatephone(String alternatephone) {
        this.alternatephone = alternatephone;
    }

    public String getFatherphone() {
        return fatherphone;
    }

    public void setFatherphone(String fatherphone) {
        this.fatherphone = fatherphone;
    }

    public String getMotherphone() {
        return motherphone;
    }

    public void setMotherphone(String motherphone) {
        this.motherphone = motherphone;
    }

    public String getPresentaddress() {
        return presentaddress;
    }

    public void setPresentaddress(String presentaddress) {
        this.presentaddress = presentaddress;
    }

    public String getPermanentaddress() {
        return permanentaddress;
    }

    public void setPermanentaddress(String permanentaddress) {
        this.permanentaddress = permanentaddress;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
