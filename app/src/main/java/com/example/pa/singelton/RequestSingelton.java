package com.example.pa.singelton;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestSingelton {
    private static RequestSingelton requestSingelton;
    private RequestQueue requestQueue;

    private RequestSingelton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized RequestSingelton getInstance(Context context){
        if (requestSingelton == null){
            requestSingelton = new RequestSingelton(context);
        }
        return requestSingelton;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
