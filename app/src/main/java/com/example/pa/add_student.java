package com.example.pa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pa.Model.User;
import com.example.pa.Request.Request;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

public class add_student extends AppCompatActivity implements View.OnClickListener, Request.OnServerPostCallBack {
    Button btnCreateStudent;
    TextView tv_name;
    TextView tv_email;
    TextView tv_password;
    User user;
    Request request;
    int position;
    boolean isEdit;

    public static String EXTRA_STUDENT = "extra_student";
    public static String EXTRA_POSITION = "extra_position";
    public static String EXTRA_ISEDIT = "extra_isedit";

    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);
        btnCreateStudent = findViewById(R.id.create_student);
        user = new User();
        tv_name = findViewById(R.id.name_student);
        tv_email = findViewById(R.id.email_student);
        tv_password = findViewById(R.id.password_student);
        request = new Request(this);
        btnCreateStudent.setOnClickListener(this);
        user = getIntent().getParcelableExtra(EXTRA_STUDENT);
        if(user !=null){
            isEdit = getIntent().getBooleanExtra(EXTRA_ISEDIT,false);
            position = getIntent().getIntExtra(EXTRA_POSITION,0);
        }else {
            user = new User();
        }
        if(isEdit){
            tv_name.setText(user.getNama());
            tv_email.setText(user.getEmail());
            tv_password.setText(user.getPassword());
            btnCreateStudent.setText("Simpan");
        }
    }
    public void showSnackbarMessage(String message){
        Snackbar.make(getCurrentFocus(),message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.create_student){
            user.setNama(tv_name.getText().toString().trim());
            user.setEmail(tv_email.getText().toString().trim());
            user.setPassword(tv_password.getText().toString().trim());

            if(isEdit){
                user.setRole("student");
                request.editStudent(user,this);
            }else {
                user.setRole("student");
                request.addStudent(user,this);
            }
        }
    }

    @Override
    public void onSuccess(String message) {
        showSnackbarMessage(message);
    }

    @Override
    public void onError() {

    }
}

