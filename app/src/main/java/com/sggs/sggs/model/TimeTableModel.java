package com.sggs.sggs.model;
import android.os.Parcel;
import android.os.Parcelable;

public class TimeTableModel implements Parcelable {
    private String subjectName;
    private String timeNteacher;

    public TimeTableModel(String subjectName, String timeNteacher) {
        this.subjectName = subjectName;
        this.timeNteacher = timeNteacher;
    }

    protected TimeTableModel(Parcel in) {
        subjectName = in.readString();
        timeNteacher = in.readString();
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

    public String getSubjectName() {
        return subjectName;
    }

    public String getTimeNteacher() {
        return timeNteacher;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subjectName);
        dest.writeString(timeNteacher);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
