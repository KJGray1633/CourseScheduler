package com.java.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class DatabaseConnection {

    private JSONArray jsonArray = new JSONArray();

    private JSONObject jsonObject = new JSONObject();

    public boolean addCourse(int uid, int cid) {

        // user id = 1
//        if (getCourse(cid) == null) {
//            return false;
//        }
//        else {
            //database call: UPDATE user SET cid = cid WHERE uid = uid

            // without db call
            jsonObject.put("userID", uid);
            jsonObject.put("courseID", cid);
            jsonArray.put(jsonObject);
            System.out.println(jsonArray);

            return true;
//        }

    }

    public boolean dropCourse(int uid, int cid) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject user = jsonArray.getJSONObject(i);
            if (user.getInt("userID") == uid && user.getInt("courseID") == cid) {
                user.remove("courseID");
                System.out.println(jsonArray);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Course> searchCourses(String query) {
        return null;
    }

    public User validateUser(String username, String password) {
        return null;
    }

    public Course getCourse(int cid) {
        return null;
    }

    public Schedule getSchedule(int uid) {
        return null;
    }
}
