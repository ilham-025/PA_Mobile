package com.example.pa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pa.Model.Student;
import com.example.pa.Request.AddStudentRequest;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class add_student extends AppCompatActivity implements View.OnClickListener{
    Button btnCreateStudent;
    TextView tv_name;
    TextView tv_email;
    TextView tv_password;
    Student student;
    AddStudentRequest ASR;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);
        btnCreateStudent = findViewById(R.id.create_student);
        student = new Student();
        tv_name = findViewById(R.id.name_student);
        tv_email = findViewById(R.id.email_student);
        tv_password = findViewById(R.id.password_student);
        btnCreateStudent.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.create_student){
            ASR = new AddStudentRequest(this);
            student.setNama(tv_name.getText().toString().trim());
            student.setEmail(tv_email.getText().toString().trim());
            student.setPassword(tv_password.getText().toString().trim());
            student.setRole("student");
            new AddStudentAsync().execute();
        }
    }
    class AddStudentAsync extends AsyncTask<Void,Void, Student> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Student doInBackground(Void... voids) {
            return ASR.start(student);
        }

        @Override
        protected void onPostExecute(Student student) {
            super.onPostExecute(student);

        }
    }
}

