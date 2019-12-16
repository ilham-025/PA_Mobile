package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pa.Adapter.ListStudentAdapter;
import com.example.pa.Model.Problem;
import com.example.pa.Model.User;
import com.example.pa.Request.Request;

import java.util.ArrayList;

public class StudentFinish extends AppCompatActivity implements ListStudentAdapter.OnStudentListListener {
    private RecyclerView rvStudent;
    private ArrayList<User> list;
    private ListStudentAdapter adapter;
    private Request request;
    private ProgressBar progressBar;
    private LoadStudentAsync loadStudentAsync;
    Problem problem;
    TextView tvJudul;

    public static String EXTRA_PROBLEM_ID = "extra_problem_id";
    public static String EXTRA_PROBLEM = "extra_problem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_finish);

        rvStudent = findViewById(R.id.rv_students);
        progressBar = findViewById(R.id.progressbar);
        tvJudul = findViewById(R.id.tv_judul_soal);

        rvStudent.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        request = new Request(this);
        adapter = new ListStudentAdapter(this);
        adapter.setListStudent(list);

        rvStudent.setAdapter(adapter);

        loadStudentAsync = new LoadStudentAsync();
        loadStudentAsync.execute();

        problem = getIntent().getParcelableExtra(EXTRA_PROBLEM);
        if(problem.getTitle()!=null){
            tvJudul.setText(problem.getTitle());
        }
    }

    @Override
    public void onClick(int position, User user, boolean isEdit) {
        Intent start = new Intent(this, PeriksaSoal.class);
        start.putExtra(PeriksaSoal.EXTRA_PROBLEM_ID, problem.getId());
        start.putExtra(PeriksaSoal.EXTRA_PROBLEM, problem);
        start.putExtra(PeriksaSoal.EXTRA_USER_ID, user.getId());
        start.putExtra(PeriksaSoal.EXTRA_USER, user);
        startActivity(start);
    }

    private class LoadStudentAsync extends AsyncTask<Void,Void,Void> implements ServerCallBack {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            if(list.size()>0){
                list.clear();
            }
        }
        @Override
        protected Void doInBackground(Void... voids) {
            request.getAllStudentFinish(this, getIntent().getIntExtra(EXTRA_PROBLEM_ID, 0));
            return null;
        }
        @Override
        public void onSuccess(ArrayList<User> users) {
            progressBar.setVisibility(View.GONE);
            list.addAll(users);
            adapter.setListStudent(list);
            adapter.notifyDataSetChanged();

        }
    }

    public interface ServerCallBack{
        void onSuccess(ArrayList<User> users);
    }
}
