package com.example.hireman.modelclass;

public class AllUserData {
    private String uid;
    private String  category;

    public AllUserData() {
    }

    public AllUserData(String uid, String category) {
        this.uid = uid;
        this.category = category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
