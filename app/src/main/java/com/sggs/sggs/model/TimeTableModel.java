package com.sggs.sggs.model;
import android.os.Parcel;
import android.os.Parcelable;

public class TimeTableModel implements Parcelable {
    private String time;
    private String subjectNteacher;

    public TimeTableModel(String subjectName, String timeNteacher) {
        this.time = subjectName;
        this.subjectNteacher = timeNteacher;
    }

    protected TimeTableModel(Parcel in) {
        time = in.readString();
        subjectNteacher = in.readString();
    }

    public static final Creator<TimeTableModel> CREATOR = new Creator<TimeTableModel>() {
        @Override
        public TimeTableModel createFromParcel(Parcel in) {
            return new TimeTableModel(in);
        }

        @Override
        public TimeTableModel[] newArray(int size) {
            return new TimeTableModel[size];
        }
    };

    public String getTime() {
        return time;
    }

    public String getSubjectNteacher() {
        return subjectNteacher;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeString(subjectNteacher);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
