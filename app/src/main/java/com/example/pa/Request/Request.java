package com.example.pa.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pa.FragmentStudentLecturer;
import com.example.pa.Model.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Request {
    protected Context context;
    protected RequestQueue requestQueue;
    protected Student student;
    protected static String message;
    protected ArrayList<Student> list;

    public Request(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);

    }
    protected String ip ="192.168.137.1";
    public String getIp(){
        return this.ip;
    }
    public void getAllStudent(final FragmentStudentLecturer.ServerCallBack serverCallBack){
        String url ="http://"+getIp()+"/elearning/public/api/students";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        list = new ArrayList<Student>();
                        String TAG = "nambah";
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Student student = new Student();
                                    student.setNama(jsonObject.getString("name"));
                                    student.setEmail(jsonObject.getString("email"));
                                    student.setPassword(jsonObject.getString("password"));
                                    student.setId(jsonObject.getInt("id"));
                                    list.add(student);
                                    Log.d("test",student.getNama());
                                }
                                serverCallBack.onSuccess(list);
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

//        return list;
    }
    public void editStudent(Student student, final FragmentStudentLecturer.ServerCallBack serverCallBack){
        JSONObject studentData = new JSONObject();
        String url = "http://"+getIp()+"/elearning/public/api/edit-student";
        try {
            studentData.put("id",student.getId());
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
                (com.android.volley.Request.Method.PUT, url, studentData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            serverCallBack.onSuccesEdit(response.getString("message"));
                            Log.d("error",response.toString());
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


//        return message;
    }

}
