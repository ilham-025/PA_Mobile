package com.example.pa.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class AnswerNumber implements Parcelable {
    private int id;
    private String text;

    public AnswerNumber(){

    }

    protected AnswerNumber(Parcel in) {
        id = in.readInt();
        text = in.readString();
        answer_id = in.readInt();
    }

    public static final Creator<AnswerNumber> CREATOR = new Creator<AnswerNumber>() {
        @Override
        public AnswerNumber createFromParcel(Parcel in) {
            return new AnswerNumber(in);
        }

        @Override
        public AnswerNumber[] newArray(int size) {
            return new AnswerNumber[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    private int answer_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeInt(answer_id);
    }
}
