package com.example.pa.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class Problem implements Parcelable {
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Creator<Problem> getCREATOR() {
        return CREATOR;
    }

    private String title;

    public Problem(){

    }

    protected Problem(Parcel in) {
        id = in.readInt();
        startTime = in.readString();
        startDate = in.readString();
        endTime = in.readString();
        endDate = in.readString();
        title = in.readString();
    }

    public static final Creator<Problem> CREATOR = new Creator<Problem>() {
        @Override
        public Problem createFromParcel(Parcel in) {
            return new Problem(in);
        }

        @Override
        public Problem[] newArray(int size) {
            return new Problem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private String startTime;
    private String startDate;
    private String endTime;
    private String endDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(startDate);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(endDate);
        dest.writeString(title);
    }
}
