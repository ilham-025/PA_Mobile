package com.example.pa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pa.Model.Problem;
import com.example.pa.R;

import java.util.ArrayList;

public class ListQuestionAdapter extends RecyclerView.Adapter<ListQuestionAdapter.ViewHolder> {

    private ArrayList<Problem> problems;
    private OnQuestionListListener onQuestionListListener;
    private Context context;

    public ListQuestionAdapter(Context context, OnQuestionListListener onQuestionListListener){
        this.context = context;
        this.onQuestionListListener = onQuestionListListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_question_lecturer, parent, false);
        return new ViewHolder(view, onQuestionListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Problem problem = problems.get(position);
        holder.judul.setText(problem.getTitle());
        holder.tempo.setText(problem.getStartTime()+" "+problem.getStartDate()+" Sampai "+problem.getEndTime()+" "+problem.getEndDate());
    }

    @Override
    public int getItemCount() {
        return problems.size();
    }

    public void setListProblem(ArrayList<Problem> problems){
        this.problems = problems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView judul, tempo;
        public ViewHolder(@NonNull View itemView, OnQuestionListListener onQuestionListListener) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_judul_soal);
            tempo = itemView.findViewById(R.id.tv_tempo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onQuestionListListener.onClick(getAdapterPosition(), problems.get(getAdapterPosition()));
        }
    }
    public interface OnQuestionListListener{
        void onClick(int position, Problem problem);

    }
}
