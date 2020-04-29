package com.example.pa.viewmodel;

public interface RequestError {
    public static String REQUEST_ALL = "request_all";
    public static String REQUEST_BY_ID = "request_by_id";

    void onError(int message);
}
