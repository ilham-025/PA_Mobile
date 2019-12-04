package com.example.pa.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pa.FragmentHomeLecturer;
import com.example.pa.FragmentStudentLecturer;
import com.example.pa.Model.Announcement;
import com.example.pa.Model.Problem;
import com.example.pa.Model.ProblemNumber;
import com.example.pa.Model.Student;
import com.example.pa.add_question;

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
    ArrayList<Announcement> announcements;

    public Request(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);

    }
    protected String ip ="192.168.137.1";
    public String getIp(){
        return this.ip;
    }
    public void addProblem(Problem problem, ArrayList<ProblemNumber> listProblemNumber){
        String url = "http://"+getIp()+"/elearning/public/api/add-problem";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title",problem.getTitle());
            jsonObject.put("start_time",problem.getStartTime());
            jsonObject.put("start_date",problem.getStartDate());
            jsonObject.put("end_time",problem.getEndTime());
            jsonObject.put("end_date",problem.getEndDate());
            JSONArray problem_number_array = new JSONArray();
            for(int i=0;i<listProblemNumber.size();i++){
                JSONObject problem_number_object = new JSONObject();
                problem_number_object.put("number", i+1);
                problem_number_object.put("pertanyaan", listProblemNumber.get(i).getPertanyaan());
                problem_number_object.put("jawaban",listProblemNumber.get(i).getJawaban());
                problem_number_array.put(problem_number_object);
            }
            jsonObject.put("problem_number", problem_number_array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("sukses tmabah soal",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error nambah soal",error.toString());
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
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

    }
    public void getAllAnnouncement(final FragmentHomeLecturer.onServerCallBack onServerCallBack){
        String url = "http://"+getIp()+"/elearning/public/api/announcements";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                announcements = new ArrayList<Announcement>();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Announcement announcement= new Announcement();
                        announcement.setId(jsonObject.getInt("id"));
                        announcement.setName(jsonObject.getString("name"));
                        announcement.setDate(jsonObject.getString("date"));
                        announcement.setText(jsonObject.getString("text"));
                        announcements.add(announcement);
                    }
                    onServerCallBack.onSuccessLoad(announcements);
                    Log.d("list announcement",announcements.toString());
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error response","error vro");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void addAnnouncement(Announcement announcement, final FragmentHomeLecturer.onServerCallBack onServerCallBack){
        String url = "http://"+getIp()+"/elearning/public/api/add-announcement";
        JSONObject announcementData = new JSONObject();
        try {
            announcementData.put("name",announcement.getName());
            announcementData.put("date",announcement.getDate());
            announcementData.put("text",announcement.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, announcementData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onServerCallBack.onSuccesAdd();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("menambah announcemnt","error Menambah");
            }
        });
        requestQueue.add(jsonObjectRequest);
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
    public interface OnServerCallBack{
        public void onSuccess();
        public void onError();
    }
}
