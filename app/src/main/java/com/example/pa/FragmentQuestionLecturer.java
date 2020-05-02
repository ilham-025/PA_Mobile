package com.example.pa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.pa.Adapter.ListQuestionAdapter;
import com.example.pa.Model.CClass;
import com.example.pa.Model.Problem;
import com.example.pa.Request.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentQuestionLecturer extends Fragment implements ListQuestionAdapter.OnQuestionListListener {
    private FloatingActionButton btnAdd;
    private Request request;
    private RecyclerView rvProblem;
    private ListQuestionAdapter listQuestionAdapter;
    private ArrayList<Problem> problems;
    private ProgressBar progressBar;
    private CClass cClass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_lecturer, container, false);
        btnAdd = view.findViewById(R.id.btn_add_question);
        rvProblem = view.findViewById(R.id.rv_question_lecturer);
        progressBar = view.findViewById(R.id.pg_question);

        problems = new ArrayList<Problem>();
        rvProblem.setLayoutManager(new LinearLayoutManager(getContext()));
        listQuestionAdapter = new ListQuestionAdapter(getContext(),this);
        listQuestionAdapter.setListProblem(problems);
        rvProblem.setAdapter(listQuestionAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        request = new Request(this.getContext());
        cClass = getActivity().getIntent().getParcelableExtra(HomeClassLecturerActivity.CCLASS);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getContext(), add_question.class);
                move.putExtra(add_question.CCLASS,cClass);
                startActivity(move);
            }
        });
        new LoadProblemAsync().execute();
    }


    @Override
    public void onClick(int position, Problem problem) {
        Intent start = new Intent(getContext(), StudentFinish.class);
        start.putExtra(StudentFinish.EXTRA_PROBLEM_ID, problem.getId());
        start.putExtra(StudentFinish.EXTRA_PROBLEM, problem);
        startActivity(start);
    }
    private class LoadProblemAsync extends AsyncTask<Void,Void, Void> implements onServerCallBack{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            if(problems.size()>0){
                problems.clear();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            request.getAllProblem(this,cClass.getId());
            return null;
        }

        @Override
        public void onSuccessLoad(ArrayList<Problem> problem) {
            progressBar.setVisibility(View.GONE);
            problems.addAll(problem);
            Log.d("test","masuk");
            listQuestionAdapter.setListProblem(problem);
            listQuestionAdapter.notifyDataSetChanged();
        }
    }
    public interface onServerCallBack{
        void onSuccessLoad(ArrayList<Problem> problem);
    }

    @Override
    public void onResume() {
        super.onResume();
        new FragmentQuestionLecturer.LoadProblemAsync().execute();
    }
}
