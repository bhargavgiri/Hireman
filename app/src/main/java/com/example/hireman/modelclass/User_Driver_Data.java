package com.example.hireman.modelclass;

public class User_Driver_Data {
    String firstname;
    String lsatname;
    String address;
    String contact;
    String dob;
    String profile_path;
    String uid;
    String staffID;
    String catogary;
    String password;

    public User_Driver_Data() {
    }

    public User_Driver_Data(String firstname, String lsatname, String address, String contact, String profile_path, String uid, String staffID, String catogary, String password,String dob) {
        this.firstname = firstname;
        this.lsatname = lsatname;
        this.address = address;
        this.contact = contact;
        this.profile_path = profile_path;
        this.uid = uid;
        this.staffID = staffID;
        this.catogary = catogary;
        this.password = password;
        this.dob=dob;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLsatname() {
        return lsatname;
    }

    public void setLsatname(String lsatname) {
        this.lsatname = lsatname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCatogary() {
        return catogary;
    }

    public void setCatogary(String catogary) {
        this.catogary = catogary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
