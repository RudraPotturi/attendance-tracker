package com.example.teacherhelper;

public class AttendanceModel {

    String id, firstName;

    public AttendanceModel() {
    }

    public AttendanceModel(String id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public String getId() {

        return id;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setId(String id) {

        this.id = id;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }
}

