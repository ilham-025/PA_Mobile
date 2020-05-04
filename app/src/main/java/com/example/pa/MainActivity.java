package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.pa.Model.Auth;
import com.example.pa.Request.Request;
import com.example.pa.db.UserHelper;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements Request.OnServerPostCallBack,View.OnClickListener{

    private EditText edtEmail,edtPassword;
    private Button btn;
    private FrameLayout layoutLoading;
    private UserHelper userHelper;;
    Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutLoading = findViewById(R.id.loading_layout);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        request = new Request(this);
        userHelper = UserHelper.getInstance(this);
        userHelper.open();
        Boolean exists = userHelper.checkIfExists();
        if(exists){
            Log.d("user","Exits");
            Auth.user = userHelper.all();
            Intent move;
            if(Auth.user.getRole().equals("teacher")) {
                move = new Intent(MainActivity.this, home_lecturer.class);
//                move.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }else {
                move = new Intent(this,home_student.class);
//                move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
            startActivity(move);
            finish();
        }
        btn = findViewById(R.id.login);
        btn.setOnClickListener(this);
    }

    @Override
    public void onSuccess(String message) {
        layoutLoading.setVisibility(View.GONE);
        btn.setVisibility(View.VISIBLE);
        userHelper.open();
        Intent move;
        if(message.equals("success")){
            long cek = userHelper.insert(Auth.user);
            Log.d("insert",String.valueOf(cek));
            if(Auth.user.getRole().equals("teacher")) {
                move = new Intent(MainActivity.this, home_lecturer.class);
            }else {
                move = new Intent(this,home_student.class);
            }
            startActivity(move);
            finish();
        }else{
            showSnackbarMessage("Check Your E-Mail or Password");
        }

    }

    @Override
    public void onError() {
        btn.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
        showSnackbarMessage("Please check YOur internet");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            btn.setVisibility(View.GONE);
            layoutLoading.setVisibility(View.VISIBLE);
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            try {
                request.loginApi(email,password,this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void showSnackbarMessage(String message){
        Snackbar.make(getCurrentFocus(),message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userHelper.close();
    }
}
