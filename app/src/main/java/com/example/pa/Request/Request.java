package com.example.pa.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pa.FragmentHomeLecturer;
import com.example.pa.FragmentHomeStudent;
import com.example.pa.FragmentQuestionLecturer;
import com.example.pa.FragmentQuestionStudent;
import com.example.pa.FragmentStudentLecturer;
import com.example.pa.Model.Announcement;
import com.example.pa.Model.Auth;
import com.example.pa.Model.Problem;
import com.example.pa.Model.ProblemNumber;
import com.example.pa.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Request {
    protected Context context;
    protected RequestQueue requestQueue;
    protected User user;
    protected static String message;
    protected ArrayList<User> list;
    ArrayList<Announcement> announcements;

    public Request(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);

    }
    protected String ip ="192.168.137.1";
    public String getIp(){
        return this.ip;
    }
    public void login(String email, String password, final OnServerCallBack onServerCallBack) throws JSONException {
        String url = "http://"+getIp()+"/elearning/public/api/login";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",email);
        jsonObject.put("password",password);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.has("status")){
                        Log.d("loginerror","credential invalid");
                    }else {
                        Auth.apiToken = response.getString("api_token");
                        Auth.user.setId(response.getInt("id"));
                        Auth.user.setEmail(response.getString("email"));
                        Auth.user.setPassword(response.getString("password"));
                        Auth.user.setNama(response.getString("name"));
                        Auth.user.setRole(response.getString("role"));
                        onServerCallBack.onSuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onServerCallBack.onError();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    public void getAllProblem(final FragmentQuestionLecturer.onServerCallBack serverCallBack){
        String url = "http://"+getIp()+"/elearning/public/api/problems";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Problem> problems = new ArrayList<Problem>();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Problem problem = new Problem();
                        problem.setTitle(jsonObject.getString("title"));
                        problem.setStartDate(jsonObject.getString("start_date"));
                        problem.setStartTime(jsonObject.getString("start_time"));
                        problem.setEndDate(jsonObject.getString("end_date"));
                        problem.setEndTime(jsonObject.getString("end_time"));
                        problem.setId(jsonObject.getInt("id"));
                        problems.add(problem);
                        Log.d("test",problem.getTitle());
                    }
                    serverCallBack.onSuccessLoad(problems);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void getAllProblem(final FragmentQuestionStudent.onServerCallBack onServerCallBack){
        String url = "http://"+getIp()+"/elearning/public/api/problems-ready";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Problem> problems = new ArrayList<Problem>();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Problem problem = new Problem();
                        problem.setTitle(jsonObject.getString("title"));
                        problem.setStartDate(jsonObject.getString("start_date"));
                        problem.setStartTime(jsonObject.getString("start_time"));
                        problem.setEndDate(jsonObject.getString("end_date"));
                        problem.setEndTime(jsonObject.getString("end_time"));
                        problem.setId(jsonObject.getInt("id"));
                        problems.add(problem);
                        Log.d("test",problem.getTitle());
                    }
                    onServerCallBack.onSuccessLoad(problems);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
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
                        list = new ArrayList<User>();
                        String TAG = "nambah";
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    User user = new User();
                                    user.setNama(jsonObject.getString("name"));
                                    user.setEmail(jsonObject.getString("email"));
                                    user.setPassword(jsonObject.getString("password"));
                                    user.setId(jsonObject.getInt("id"));
                                    list.add(user);
                                    Log.d("test", user.getNama());
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

    public void getAllAnnouncement(final FragmentHomeStudent.onServerCallBack onServerCallBack){
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
    public void editStudent(User user, final FragmentStudentLecturer.ServerCallBack serverCallBack){
        JSONObject studentData = new JSONObject();
        String url = "http://"+getIp()+"/elearning/public/api/edit-user";
        try {
            studentData.put("id", user.getId());
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
