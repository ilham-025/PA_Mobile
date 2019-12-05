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

public class ListQuestionReadyAdapter extends RecyclerView.Adapter<ListQuestionReadyAdapter.ViewHolder> {
    private ArrayList<Problem> problems;
    private ListQuestionReadyAdapter.OnQuestionListListener onQuestionListListener;
    private Context context;

    public ListQuestionReadyAdapter(Context context, ListQuestionReadyAdapter.OnQuestionListListener onQuestionListListener){
        this.context = context;
        this.onQuestionListListener = onQuestionListListener;
    }

    @NonNull
    @Override
    public ListQuestionReadyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_question_student, parent, false);
        return new ListQuestionReadyAdapter.ViewHolder(view, onQuestionListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListQuestionReadyAdapter.ViewHolder holder, int position) {
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
        public ViewHolder(@NonNull View itemView, ListQuestionReadyAdapter.OnQuestionListListener onQuestionListListener) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_judul_soal);
            tempo = itemView.findViewById(R.id.tv_tempo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onQuestionListListener.onClick(getAdapterPosition(), problems.get(getAdapterPosition()), true);
        }
    }
    public interface OnQuestionListListener{
        void onClick(int position, Problem problem, boolean isEdit);

    }
}
