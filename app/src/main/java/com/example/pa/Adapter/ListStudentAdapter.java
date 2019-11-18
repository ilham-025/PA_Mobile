package com.example.pa.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pa.Model.Student;
import com.example.pa.R;

import java.util.ArrayList;
import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListStudentAdapter extends RecyclerView.Adapter<ListStudentAdapter.UserViewHolder> {
    private ArrayList<Student> students;
    private OnStudentListListener onStudentListListener;

    public ListStudentAdapter(OnStudentListListener onStudentListListener){
        this.onStudentListListener = onStudentListListener;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_student,parent,false);
        return new UserViewHolder(view,this.onStudentListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Student student = students.get(position);
        holder.tvNama.setText(student.getNama());
        holder.tvEmaill.setText(student.getEmail());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void setListStudent(ArrayList<Student> students){
        this.students = students;
    }
    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvNama,tvEmaill;
        public UserViewHolder(@NonNull View itemView, OnStudentListListener onStudentListListener) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_name_student);
            tvEmaill = itemView.findViewById(R.id.tv_email_student);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStudentListListener.onClick(getAdapterPosition(),students.get(getAdapterPosition()),true);
        }
    }
    public interface OnStudentListListener{
        void onClick(int position, Student student, boolean isEdit);

    }
}
