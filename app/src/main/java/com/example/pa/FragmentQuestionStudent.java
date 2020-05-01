package com.example.pa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pa.Adapter.ListQuestionReadyAdapter;
import com.example.pa.Model.CClass;
import com.example.pa.Model.Problem;
import com.example.pa.Request.Request;

import java.util.ArrayList;

public class FragmentQuestionStudent extends Fragment implements ListQuestionReadyAdapter.OnQuestionListListener {

    private Request request;
    private RecyclerView rvProblem;
    private ListQuestionReadyAdapter listQuestionReadyAdapter;
    private ArrayList<Problem> problems;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_student, container, false);
        rvProblem = view.findViewById(R.id.rv_question_student);
        progressBar = view.findViewById(R.id.pg_question);

        problems = new ArrayList<Problem>();
        rvProblem.setLayoutManager(new LinearLayoutManager(getContext()));
        listQuestionReadyAdapter = new ListQuestionReadyAdapter(getContext(),this);
        listQuestionReadyAdapter.setListProblem(problems);
        rvProblem.setAdapter(listQuestionReadyAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        request = new Request(this.getContext());
        new LoadProblemAsync().execute();
    }

    @Override
    public void onClick(int problemId, Problem problem) {
        Intent start = new Intent(getContext(), StartQuestion.class);
        start.putExtra(StartQuestion.EXTRA_PROBLEM_ID, problemId);
        start.putExtra(StartQuestion.EXTRA_PROBLEM, problem);
        startActivity(start);
    }

    private class LoadProblemAsync extends AsyncTask<Void,Void, Void> implements onServerCallBack {
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
            CClass cClass = getActivity().getIntent().getParcelableExtra(HomeClassStudentActivity.CCLASS);
            request.getAllProblem(this,cClass.getId());
            Log.d("id kelas",String.valueOf(cClass.getId()));
            return null;
        }

        @Override
        public void onSuccessLoad(ArrayList<Problem> problem) {
            progressBar.setVisibility(View.GONE);
            problems.addAll(problem);
            Log.d("test","masuk");
            listQuestionReadyAdapter.setListProblem(problem);
            listQuestionReadyAdapter.notifyDataSetChanged();
        }
    }
    public interface onServerCallBack{
        void onSuccessLoad(ArrayList<Problem> problem);
    }
}
