package com.example.pa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.pa.Adapter.ListStudentAdapter;
import com.example.pa.Model.Student;
import com.example.pa.Request.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentStudentLecturer extends Fragment implements ListStudentAdapter.OnStudentListListener {
    private FloatingActionButton btnAdd;
    private RecyclerView rv_student;
    private ArrayList<Student> list;
    private ListStudentAdapter adapter;
    private Request request;
    private ProgressBar progressBar;
    private LoadStudentAsync loadStudentAsync;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_lecturer, container, false);
        progressBar = view.findViewById(R.id.progressbar);
        btnAdd = view.findViewById(R.id.btn_add_student);
        rv_student = view.findViewById(R.id.rv_students);
        rv_student.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<>();
        request = new Request(this.getContext());
        adapter = new ListStudentAdapter(this);
        adapter.setListStudent(list);
        rv_student.setAdapter(adapter);
        loadStudentAsync = new LoadStudentAsync();
        loadStudentAsync.execute();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveAdd = new Intent(getContext(), add_student.class);
                startActivityForResult(moveAdd,add_student.REQUEST_ADD);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(int position, Student student, boolean isEdit) {
        Intent moveIntent = new Intent(this.getActivity(),add_student.class);
        moveIntent.putExtra(add_student.EXTRA_STUDENT,student);
        moveIntent.putExtra(add_student.EXTRA_POSITION,position);
        moveIntent.putExtra(add_student.EXTRA_ISEDIT,isEdit);
        startActivityForResult(moveIntent,add_student.REQUEST_UPDATE);
    }

    private class LoadStudentAsync extends AsyncTask<Void,Void,Void> implements ServerCallBack{
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
             request.getAllStudent(this);
             return null;
        }
        @Override
        public void onSuccess(ArrayList<Student> students) {
            progressBar.setVisibility(View.GONE);
            list.addAll(students);
            adapter.setListStudent(list);
            adapter.notifyDataSetChanged();

        }

        @Override
        public void onSuccesEdit(String responseMessage) {
            showSnackbarMessage(responseMessage);
        }
    }
    public void showSnackbarMessage(String message){
        Snackbar.make(rv_student,message,Snackbar.LENGTH_SHORT).show();
    }
    public interface ServerCallBack{
        void onSuccess(ArrayList<Student> students);
        void onSuccesEdit(String responseMessage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityCreated(getArguments());
        if(requestCode==add_student.REQUEST_UPDATE){
            if (resultCode==add_student.RESULT_UPDATE){
//                showSnackbarMessage("Murid Berhasil di Edit");
            }
        }
    }
}
