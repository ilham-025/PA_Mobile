package com.example.pa.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProblemNumber implements Parcelable {
    private int id;
    private int number;
    private String pertanyaan;

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public static Creator<ProblemNumber> getCREATOR() {
        return CREATOR;
    }

    private String jawaban;
    private int problem_id;

    public ProblemNumber(){

    }
    protected ProblemNumber(Parcel in) {
        id = in.readInt();
        number = in.readInt();
        pertanyaan = in.readString();
        jawaban = in.readString();
        problem_id = in.readInt();
    }

    public static final Creator<ProblemNumber> CREATOR = new Creator<ProblemNumber>() {
        @Override
        public ProblemNumber createFromParcel(Parcel in) {
            return new ProblemNumber(in);
        }

        @Override
        public ProblemNumber[] newArray(int size) {
            return new ProblemNumber[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(number);
        dest.writeString(pertanyaan);
        dest.writeString(jawaban);
        dest.writeInt(problem_id);
    }
}
