package com.sggs.sggs.model;

public class CalendarModel {
    private String date_time;
    private String attendance_status;

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "date_time='" + date_time + '\'' +
                ", attendance_status='" + attendance_status + '\'' +
                '}';
    }
}