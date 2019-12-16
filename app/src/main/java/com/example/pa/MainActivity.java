package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pa.Model.Auth;
import com.example.pa.Request.Request;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements Request.OnServerPostCallBack,View.OnClickListener{

    private EditText edtEmail,edtPassword;
    private Button btnLogin;
    Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        request = new Request(this);
        Button btn;
        btn = findViewById(R.id.login);
        btn.setOnClickListener(this);
    }

    @Override
    public void onSuccess(String message) {
        Intent move;
        if(message.equals("success")){
            if(Auth.user.getRole().equals("teacher")) {
                move = new Intent(MainActivity.this, home_lecturer.class);
            }else {
                move = new Intent(this,home_student.class);
            }
            startActivity(move);
        }else{
            showSnackbarMessage("credential error");
        }

    }

    @Override
    public void onError() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
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
}
