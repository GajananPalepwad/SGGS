package com.sggs.sggs.model;
import android.os.Parcel;
import android.os.Parcelable;

public class SubjectModel implements Parcelable {
    private String subjectName;
    private int marks;

    public SubjectModel(String subjectName, int marks) {
        this.subjectName = subjectName;
        this.marks = marks;
    }

    protected SubjectModel(Parcel in) {
        subjectName = in.readString();
        marks = in.readInt();
    }

    public static final Creator<SubjectModel> CREATOR = new Creator<SubjectModel>() {
        @Override
        public SubjectModel createFromParcel(Parcel in) {
            return new SubjectModel(in);
        }

        @Override
        public SubjectModel[] newArray(int size) {
            return new SubjectModel[size];
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
