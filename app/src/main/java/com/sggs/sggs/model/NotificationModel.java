package com.sggs.sggs.model;
import android.os.Parcel;
import android.os.Parcelable;

public class NotificationModel implements Parcelable {
    private String time;
    private String titleNbody;

    public NotificationModel(String subjectName, String timeNteacher) {
        this.time = subjectName;
        this.titleNbody = timeNteacher;
    }

    protected NotificationModel(Parcel in) {
        time = in.readString();
        titleNbody = in.readString();
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
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
