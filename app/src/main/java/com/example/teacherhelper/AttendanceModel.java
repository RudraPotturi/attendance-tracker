package com.example.teacherhelper;

public class AttendanceModel {

    String id, firstName;
    Boolean check;

    public AttendanceModel() {
    }

    public AttendanceModel(String id, String firstName, Boolean check) {
        this.id = id;
        this.firstName = firstName;
        this.check = check;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public AttendanceModel(String id, String firstName) {
        this.id = id;
        this.firstName = firstName;
        this.check = false;
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

    @Override
    public String toString() {
        return firstName + '\n';
    }
}

