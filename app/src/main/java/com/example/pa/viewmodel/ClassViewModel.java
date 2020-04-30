package com.example.pa.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pa.Model.Auth;
import com.example.pa.Model.CClass;
import com.example.pa.Model.ProblemNumber;
import com.example.pa.Model.User;
import com.example.pa.singelton.RequestSingelton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassViewModel extends ViewModel {
    private MutableLiveData<ArrayList<CClass>> listclass = new MutableLiveData<>();
    private Context context;

    public void setContext(Context context){
        this.context=context;
    }
    public void setListclass(RequestError requestError, User user){
        if (user.getRole().equals("teacher")) {
            String url = "http://"+ Auth.ip +"/elearning/public/api/classes-teacher?teacher_id="+user.getId();
            JSONObject userJSON = new JSONObject();
            try {
                userJSON.put("user_id",user.getId());
            }catch (Exception e){
                e.printStackTrace();
            }
            JsonObjectRequest indexClassRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONArray jsonArray = null;
                    try {
                        ArrayList<CClass> listItem = new ArrayList<>();
                        jsonArray = response.getJSONArray("data");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject cClassJsonObject = jsonArray.getJSONObject(i);
                            CClass cClass = setToObject(cClassJsonObject);
                            listItem.add(cClass);
                        }
                        listclass.postValue(listItem);
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
            RequestSingelton.getInstance(context).getRequestQueue().add(indexClassRequest);
        } else {
            String url = "http://"+ Auth.ip +"/elearning/public/api/classes-student?user_id="+user.getId();
            JSONObject userJSON = new JSONObject();
            try {
                userJSON.put("user_id",user.getId());
            }catch (Exception e){
                e.printStackTrace();
            }
            JsonObjectRequest indexClassRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONArray jsonArray = null;
                    try {
                        ArrayList<CClass> listItem = new ArrayList<>();
                        jsonArray = response.getJSONArray("data");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject cClassJsonObject = jsonArray.getJSONObject(i);
                            CClass cClass = setToObject(cClassJsonObject);
                            listItem.add(cClass);
                        }
                        listclass.postValue(listItem);
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
            RequestSingelton.getInstance(context).getRequestQueue().add(indexClassRequest);
        }


    }

    public LiveData<ArrayList<CClass>> getListCClass(){
        return listclass;
    }
    private CClass setToObject(JSONObject cClassJsonObject){
        CClass cClass = new CClass();
        try {
            cClass.setId(cClassJsonObject.getInt("id"));
            cClass.setName(cClassJsonObject.getString("name"));
            cClass.setDescription(cClassJsonObject.getString("description"));
            cClass.setTeacher_id(cClassJsonObject.getInt("teacher_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cClass;
    }

}
