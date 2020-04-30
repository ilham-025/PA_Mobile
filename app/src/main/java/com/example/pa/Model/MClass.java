package com.example.pa.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MClass implements Parcelable {
    private int user_id;
    private int class_id;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MClass(){}

    protected MClass(Parcel in) {
        user_id = in.readInt();
        class_id = in.readInt();
        code = in.readString();
    }

    public static final Creator<MClass> CREATOR = new Creator<MClass>() {
        @Override
        public MClass createFromParcel(Parcel in) {
            return new MClass(in);
        }

        @Override
        public MClass[] newArray(int size) {
            return new MClass[size];
        }
    };

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(user_id);
        parcel.writeInt(class_id);
        parcel.writeString(code);
    }
}
