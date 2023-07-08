package com.sggs.sggs.model;
import android.os.Parcel;
import android.os.Parcelable;

public class Subject implements Parcelable {
    private String subjectName;
    private int marks;

    public Subject(String subjectName, int marks) {
        this.subjectName = subjectName;
        this.marks = marks;
    }

    protected Subject(Parcel in) {
        subjectName = in.readString();
        marks = in.readInt();
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public String getSubjectName() {
        return subjectName;
    }

    public int getMarks() {
        return marks;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subjectName);
        dest.writeInt(marks);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
