package com.example.pa.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pa.Model.User;

import org.json.JSONException;
import org.json.JSONObject;


public class AddStudentRequest extends com.example.pa.Request.Request {

    String url = "http://"+getIp()+"/elearning/public/api/add-user";
    JSONObject studentData;
    User user;
    static String message;
    public AddStudentRequest(Context context){
        super(context);
        studentData = new JSONObject();
        String TAG = "nambah";
        Log.d(TAG,url);
    }
    public String start(User user){
        try {
            studentData.put("name", user.getNama());
            studentData.put("email", user.getEmail());
            studentData.put("password", user.getPassword());
            studentData.put("role", user.getRole());
            String TAG = "nambah";
            Log.d(TAG, user.getNama());
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
