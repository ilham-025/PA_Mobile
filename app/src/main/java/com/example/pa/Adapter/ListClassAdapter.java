package com.example.pa.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pa.Model.CClass;
import com.example.pa.R;

import java.util.ArrayList;

public class ListClassAdapter extends RecyclerView.Adapter<ListClassAdapter.CClasViewHolder> {
    private ArrayList<CClass> listCClass;
    private ListClassListener listClassListener;
    public ListClassAdapter(ListClassListener listClassListener){
        this.listClassListener = listClassListener;
    }

    public void setListCClass(ArrayList<CClass> listCClass) {
        this.listCClass = listCClass;
    }

    @NonNull
    @Override
    public CClasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_class,parent,false);
        return new CClasViewHolder(view,listClassListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CClasViewHolder holder, int position) {
        holder.name.setText(listCClass.get(position).getName());
        holder.description.setText(listCClass.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return listCClass.size();
    }

    public class CClasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView description;

        public CClasViewHolder(@NonNull View itemView,ListClassListener listClassListener) {
            super(itemView);
            name = itemView.findViewById(R.id.class_name);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listClassListener.onClick(listCClass.get(getAdapterPosition()));
        }
    }

    public interface ListClassListener{
        void onClick(CClass cClass);
    }
}
