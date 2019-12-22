package com.example.pa.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    private int id;
    private int problem_id;
    private int user_id;

    public Answer(){

    }
    protected Answer(Parcel in) {
        id = in.readInt();
        problem_id = in.readInt();
        user_id = in.readInt();
        nilai = in.readInt();
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    private int nilai;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(problem_id);
        dest.writeInt(user_id);
        dest.writeInt(nilai);
    }
}
