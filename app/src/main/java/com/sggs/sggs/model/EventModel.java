package com.sggs.sggs.model;
import android.os.Parcel;
import android.os.Parcelable;

public class EventModel implements Parcelable {
    private String time;
    private String titleNbody;

    public EventModel(String subjectName, String timeNteacher) {
        this.time = subjectName;
        this.titleNbody = timeNteacher;
    }

    protected EventModel(Parcel in) {
        time = in.readString();
        titleNbody = in.readString();
    }

    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel in) {
            return new EventModel(in);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };

    public String getTime() {
        return time;
    }

    public String getTitleNbody() {
        return titleNbody;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeString(titleNbody);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
