package com.example.pa.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CClass implements Parcelable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String code;
    private String description;
    private int teacher_id;

    public CClass(Parcel in) {
        name = in.readString();
        code = in.readString();
        description = in.readString();
        teacher_id = in.readInt();
        id = in.readInt();
    }

    public static final Creator<CClass> CREATOR = new Creator<CClass>() {
        @Override
        public CClass createFromParcel(Parcel in) {
            return new CClass(in);
        }

        @Override
        public CClass[] newArray(int size) {
            return new CClass[size];
        }
    };

    public CClass() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(description);
        dest.writeInt(teacher_id);
        dest.writeInt(id);
    }
}
