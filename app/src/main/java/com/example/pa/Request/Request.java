package com.example.pa.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
import com.example.pa.Model.Answer;
import com.example.pa.Model.AnswerNumber;
import com.example.pa.Model.Auth;
import com.example.pa.Model.CClass;
import com.example.pa.Model.MClass;
import com.example.pa.Model.Problem;
import com.example.pa.Model.ProblemNumber;
import com.example.pa.Model.User;
import com.example.pa.StudentFinish;
import com.example.pa.singelton.RequestSingelton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Request {
    protected Context context;
    protected RequestQueue requestQueue;
    protected User user;
    protected static String message;
    protected ArrayList<User> list;
    private RequestSingelton requestSingelton;
    ArrayList<Announcement> announcements;

    public Request(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        requestSingelton = RequestSingelton.getInstance(context);
    }
    protected String ip =Auth.ip;
    public String getIp(){
        return this.ip;
    }
    public void nilai(int answer_id,int nilai, final NilaiCallBack nilaiCallBack){
        String url = "http://"+getIp()+"/elearning/public/api/nilai";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("answer_id",answer_id);
            jsonObject.put("nilai",nilai);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("message","masukkk");

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("message","masukkk");
                    Log.d("message",response.getString("message"));
                    nilaiCallBack.onSucces(response.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);

        Log.d("message","masukkk");
    }
    public void CheckStudentAnswerNumber(int answer_id, int problem_id, final CheckProblemCallBack checkProblemCallBack){
        String url = "http://"+getIp()+"/elearning/public/api/check-answer";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("problem_id",problem_id);
            jsonObject.put("answer_id",answer_id);
            JSONArray answer_number_array = new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<ProblemNumber> problemNumbers = new ArrayList<>();
                    ArrayList<AnswerNumber> answerNumbers = new ArrayList<>();
                    JSONArray listAnswerNumbers = response.getJSONArray("answer_numbers");
                    JSONArray listProblemNumbers = response.getJSONArray("problem_numbers");
                    for(int i=0;i<listAnswerNumbers.length();i++){
                        JSONObject problemNumberJson = listProblemNumbers.getJSONObject(i);
                        JSONObject answerNumberJson = listAnswerNumbers.getJSONObject(i);
                        ProblemNumber problemNumber = new ProblemNumber();
                        problemNumber.setId(problemNumberJson.getInt("id"));
                        problemNumber.setProblem_id(problemNumberJson.getInt("problem_id"));
                        problemNumber.setPertanyaan(problemNumberJson.getString("pertanyaan"));
                        problemNumber.setJawaban(problemNumberJson.getString("jawaban"));
                        problemNumbers.add(problemNumber);
                        AnswerNumber answerNumber = new AnswerNumber();
                        answerNumber.setId(answerNumberJson.getInt("id"));
                        answerNumber.setAnswer_id(answerNumberJson.getInt("answer_id"));
                        answerNumber.setText(answerNumberJson.getString("text"));
                        answerNumbers.add(answerNumber);
                    }
                    Log.d("list answer",answerNumbers.toString());
                    checkProblemCallBack.onSuccess(problemNumbers,answerNumbers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    public void addAnswer(Answer answer, ArrayList<AnswerNumber> answerNumbers, final AddProblemCallBack addProblemCallBack){
        String url = "http://"+getIp()+"/elearning/public/api/add-answers";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("problem_id",answer.getProblem_id());
            jsonObject.put("user_id",answer.getUser_id());
            JSONArray answer_number_array = new JSONArray();
            for(int i=0;i<answerNumbers.size();i++){
                JSONObject answer_number_object = new JSONObject();
                answer_number_object.put("text", answerNumbers.get(i).getText());
                answer_number_object.put("answer_id", answerNumbers.get(i).getAnswer_id());
                answer_number_array.put(answer_number_object);
            }
            jsonObject.put("answer_numbers", answer_number_array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.has("status") && response.getInt("status")==200){
                        addProblemCallBack.success(response.getString("message"));
                        Log.d("status",response.getString("message"));
                    }
                    Log.d("status",response.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    public void getProblemNumber(final int problem_id, final ProblemNumberReady problemNumberReady){
        String url = "http://"+getIp()+"/elearning/public/api/problem-numbers-ready";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("problem_id",problem_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("dfkjn", String.valueOf(problem_id));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<ProblemNumber> problemNumbers = new ArrayList<ProblemNumber>();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ProblemNumber problemNumber = new ProblemNumber();
                        problemNumber.setNumber(jsonObject.getInt("number"));
                        problemNumber.setPertanyaan(jsonObject.getString("pertanyaan"));
                        problemNumber.setProblem_id(jsonObject.getInt("problem_id"));
                        problemNumbers.add(problemNumber);
                        Log.d("kngdfkjhgdfkjn", jsonObject.getString("pertanyaan"));
                    }
                    problemNumberReady.onSucces(problemNumbers);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    public void login(String email, String password, final OnServerPostCallBack onServerCallBack) throws JSONException {
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
                        onServerCallBack.onSuccess("error");
                    }else {
                        Auth.apiToken = response.getString("api_token");
                        Auth.user.setId(response.getInt("id"));
                        Auth.user.setEmail(response.getString("email"));
                        Auth.user.setPassword(response.getString("password"));
                        Auth.user.setNama(response.getString("name"));
                        Auth.user.setRole(response.getString("role"));
                        onServerCallBack.onSuccess("success");
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

    public void loginApi(String email, String password, final OnServerPostCallBack onServerCallBack) throws JSONException {
        String url = "http://"+getIp()+"/elearning/public/api/login-api";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",email);
        jsonObject.put("password",password);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.has("status")){
                        Log.d("loginerror","credential invalid");
                        onServerCallBack.onSuccess("error");
                    }else {
//                        Auth.apiToken = response.getString("api_token");
                        Auth.user.setId(response.getInt("id"));
                        Auth.user.setEmail(response.getString("Email"));
                        Auth.user.setPassword(response.getString("Password"));
                        Auth.user.setNama(response.getString("Nama"));
                        Auth.user.setRole(response.getString("role"));
                        onServerCallBack.onSuccess("success");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    onServerCallBack.onError();
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


    public void getAllProblem(final FragmentQuestionLecturer.onServerCallBack serverCallBack,int class_id){
        String url = "http://"+getIp()+"/elearning/public/api/problems?class_id="+class_id;

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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void getAllProblem(final FragmentQuestionStudent.onServerCallBack onServerCallBack,int class_id){
        String url = "http://"+getIp()+"/elearning/public/api/problems-ready?class_id="+class_id;

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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };

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
            jsonObject.put("class_id",problem.getClass_id());
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
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    public void getAllStudentFinish(final StudentFinish.ServerCallBack serverCallBack, int idProblem){
        String url ="http://"+getIp()+"/elearning/public/api/answers?problem_id="+idProblem;
        Log.d(String.valueOf(idProblem), "lolasdfas");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        list = new ArrayList<User>();
                        ArrayList list2 = new ArrayList<Answer>();
                        String TAG = "nambah";
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    User user = new User();
                                    Answer answer = new Answer();
                                    user.setNama(jsonObject.getString("name"));
                                    user.setEmail(jsonObject.getString("email"));
                                    answer.setId(jsonObject.getInt("id"));
                                    list.add(user);
                                    list2.add(answer);
                                    Log.d("id", String.valueOf(jsonObject.getInt("id")));
                                }
                                serverCallBack.onSuccess(list,list2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "lolasdfas");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", String.valueOf(error));

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void getAllStudentApi(final FragmentStudentLecturer.ServerCallBack serverCallBack,int class_id){
        String url ="http://"+getIp()+"/elearning/public/api/student-in-class?class_id="+class_id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        list = new ArrayList<User>();
                        String TAG = "nambah";
                        Log.d(TAG,"masuuk");
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                User user = new User();
                                user.setNama(jsonObject.getString("Nama"));
                                user.setEmail(jsonObject.getString("Email"));
                                user.setPassword(jsonObject.getString("Password"));
                                user.setId(jsonObject.getInt("id"));
                                list.add(user);
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
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }
    public void getAllAnnouncement(final FragmentHomeLecturer.onServerCallBack onServerCallBack,int class_id){
        String url = "http://"+getIp()+"/elearning/public/api/announcements?class_id="+class_id;
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
                        announcement.setClass_id(jsonObject.getInt("class_id"));
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
                        Log.d("Error response",error.toString());
                    }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
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
                Log.d("Error",error.toString());

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void addAnnouncement(Announcement announcement, final FragmentHomeLecturer.onServerCallBack onServerCallBack){
        String url = "http://"+getIp()+"/elearning/public/api/add-announcement";
        JSONObject announcementData = new JSONObject();
        try {
            announcementData.put("name",announcement.getName());
            announcementData.put("date",announcement.getDate());
            announcementData.put("text",announcement.getText());
            announcementData.put("class_id",announcement.getClass_id());
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    public void editStudent(User user, final OnServerPostCallBack onServerCallBack){
        JSONObject studentData = new JSONObject();
        String url = "http://"+getIp()+"/elearning/public/api/edit-user";
        try {
            studentData.put("id", user.getId());
            studentData.put("name", user.getNama());
            studentData.put("email", user.getEmail());
            studentData.put("password", user.getPassword());
            studentData.put("role", user.getRole());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.PUT, url, studentData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            onServerCallBack.onSuccess(response.getString("message"));
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
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);


//        return message;
    }
    public void addStudent(User user, final OnServerPostCallBack onServerCallBack){
        String url = "http://"+getIp()+"/elearning/public/api/add-user";
        JSONObject studentData = new JSONObject();
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
                (com.android.volley.Request.Method.POST, url, studentData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        onServerCallBack.onSuccess("murid berhasil di tambah");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", String.valueOf(error));

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+Auth.apiToken);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    public void addClass(CClass cClass, final AddClasstCallBack addClasstCallBack){
        String url = "http://" + getIp() + "/elearning/public/api/add-class";
        JSONObject classData = new JSONObject();
        try {
            classData.put("name", cClass.getName());
            classData.put("code", cClass.getCode());
            classData.put("description", cClass.getDescription());
            classData.put("teacher_id", cClass.getTeacher_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, classData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                addClasstCallBack.onSuccessAdd();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("menambah announcemnt", "error Menambah");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + Auth.apiToken);
                return params;
            }
        };
        requestSingelton.getRequestQueue().add(jsonObjectRequest);
    }

    public void joinClass(MClass mClass, final JoinClassCallBack joinClassCallBack){
        String url = "http://" + getIp() + "/elearning/public/api/join-class";
        JSONObject classData = new JSONObject();
        try {
            classData.put("class_id", mClass.getClass_id());
            classData.put("user_id", mClass.getUser_id());
            classData.put("code", mClass.getCode());
        } catch (JSONException e) {
            e.printStackTrace();
            joinClassCallBack.onErrorAdd();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, classData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                joinClassCallBack.onSuccessAdd();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("menambah member kelas", "error Menambah");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + Auth.apiToken);
                return params;
            }
        };
        requestSingelton.getRequestQueue().add(jsonObjectRequest);
    }

    public void showAnswerNumber(Answer answer, final ShowAnswerNumberCallBack showAnswerNumberCallBack){
        String url = "http://" + getIp() + "/elearning/public/api/answer/show?problem_id="+answer.getProblem_id()+"&user_id="+answer.getUser_id();
        JsonObjectRequest showAnswerNumberRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    ArrayList<AnswerNumber> answerNumbers = new ArrayList<>();
                    for(int i=0;i<data.length();i++){
                        AnswerNumber answerNumber = new AnswerNumber();
                        JSONObject answerNumberJSONObject = data.getJSONObject(i);
                        answerNumber.setId(answerNumberJSONObject.getInt("id"));
                        answerNumber.setText(answerNumberJSONObject.getString("text"));
                        answerNumber.setAnswer_id(answerNumberJSONObject.getInt("answer_id"));
                        answerNumbers.add(answerNumber);
                    }
                    showAnswerNumberCallBack.onSuccessShow(answerNumbers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + Auth.apiToken);
                return params;
            }
        };
        requestSingelton.getRequestQueue().add(showAnswerNumberRequest);
    }

    public void showNilai(int problem_id, int user_id, final ShowNilaiCallBack showNilaiCallBack){
        String url = "http://" + getIp() + "/elearning/public/api/answer/show-nilai?problem_id="+problem_id+"&user_id="+user_id;
        JsonObjectRequest showAnswerNumberRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    ArrayList<Answer> answers = new ArrayList<>();
                    for(int i=0;i<data.length();i++) {
                        Answer answer1 = new Answer();
                        JSONObject dataJSONObject = data.getJSONObject(i);
                        answer1.setId(dataJSONObject.getInt("id"));
                        answer1.setUser_id(dataJSONObject.getInt("user_id"));
                        answer1.setProblem_id(dataJSONObject.getInt("problem_id"));
                        answer1.setNilai(dataJSONObject.getInt("nilai"));
                        Log.d("ini nilai", String.valueOf(answer1.getNilai()));
                        answers.add(answer1);
                    }
                    showNilaiCallBack.onSuccessShow(answers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + Auth.apiToken);
                return params;
            }
        };
        requestSingelton.getRequestQueue().add(showAnswerNumberRequest);
    }

    public interface ShowAnswerNumberCallBack{
        public void onSuccessShow(ArrayList<AnswerNumber> answerNumbers);
        public void onErrorShow();
    }

    public interface ShowNilaiCallBack{
        public void onSuccessShow(ArrayList<Answer> answer);
        public void onErrorShow();
    }

    public interface AddAnnouncementCallBack{
        public void onSuccessAdd();
        public void onErrorAdd();
    }
    public interface AddClasstCallBack{
        public void onSuccessAdd();
        public void onErrorAdd();
    }
    public interface JoinClassCallBack{
        public void onSuccessAdd();
        public void onErrorAdd();
    }
    public interface OnServerPostCallBack {
        public void onSuccess(String message);
        public void onError();
    }
    public interface OnServerGetCallBack{
        public void onSuccess(ArrayList<Object> list);
    }
    public interface ProblemNumberReady{
        public void onSucces(ArrayList<ProblemNumber> list);
    }
    public interface AddProblemCallBack{
        void success(String Message);
        void error(String Message);
    }
    public interface CheckProblemCallBack{
        void onSuccess(ArrayList<ProblemNumber> problemNumbers, ArrayList<AnswerNumber> answerNumbers);
        void onError();
    }
    public interface NilaiCallBack{
        void onSucces(String message);
        void onError();
    }
    public interface AddQuestionCallBack{
        void onSuccess(String message);
        void onError();
    }
}
