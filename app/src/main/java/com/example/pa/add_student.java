package com.example.pa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pa.Model.User;
import com.example.pa.Request.AddStudentRequest;
import com.example.pa.Request.Request;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class add_student extends AppCompatActivity implements View.OnClickListener, FragmentStudentLecturer.ServerCallBack {
    Button btnCreateStudent;
    TextView tv_name;
    TextView tv_email;
    TextView tv_password;
    User user;
    Request request;
    int position;
    AddStudentRequest ASR;
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
//        Snackbar.make(getCurrentFocus(),message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.create_student){
//            Log.d("coba",tv_name.getPertanyaan().toString().trim());
            user.setNama(tv_name.getText().toString().trim());
            user.setEmail(tv_email.getText().toString().trim());
            user.setPassword(tv_password.getText().toString().trim());

            if(isEdit){
                request.editStudent(user,this);
            }else {

                ASR = new AddStudentRequest(this);
                user.setRole("user");
                new AddStudentAsync().execute();
            }
        }
    }

    @Override
    public void onSuccess(ArrayList<User> users) {

    }

    @Override
    public void onSuccesEdit(String responseMessage) {
        showSnackbarMessage(responseMessage);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_POSITION,position);
        setResult(RESULT_UPDATE,intent);
        finish();
    }

    class AddStudentAsync extends AsyncTask<Void,Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return ASR.start(user);
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
//            showSnackbarMessage(message);
            finish();

        }
    }
}

