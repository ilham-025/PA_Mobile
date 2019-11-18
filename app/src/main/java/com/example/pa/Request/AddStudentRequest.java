package com.example.pa.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.pa.Model.Student;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;


public class AddStudentRequest extends com.example.pa.Request.Request {

    String url = "http://"+getIp()+"/elearning/public/api/add-student";
    JSONObject studentData;
    Student student;
    static String message;
    public AddStudentRequest(Context context){
        super(context);
        studentData = new JSONObject();
        String TAG = "nambah";
        Log.d(TAG,url);
    }
    public String start(Student student){
        try {
            studentData.put("name",student.getNama());
            studentData.put("email",student.getEmail());
            studentData.put("password",student.getPassword());
            studentData.put("role",student.getRole());
            String TAG = "nambah";
            Log.d(TAG,student.getNama());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, studentData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String TAG = "nambah";
                        Log.d(TAG,response.toString());
                        try {
                            if(response.getInt("status")==200){
                                AddStudentRequest.message = "Murid berhasil di tambah";
                            }else {
                                AddStudentRequest.message = "murid gagal ditambah";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", String.valueOf(error));

                    }
                });
        requestQueue.add(jsonObjectRequest);


        return message;
    }
}
