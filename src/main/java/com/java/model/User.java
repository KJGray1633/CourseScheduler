package com.java.model;

import java.util.ArrayList;

public class User {
    private int uid;
    private String username;
    private String password;
    private ArrayList<Course> courseHistory;
    private String major;
    private String year;

    public User(int uid) {

    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Course> getCourseHistory() {
        return courseHistory;
    }

    public String getMajor() {
        return major;
    }

    public String getYear() {
        return year;
    }

    public void setUsername(String username) {
    }
}
