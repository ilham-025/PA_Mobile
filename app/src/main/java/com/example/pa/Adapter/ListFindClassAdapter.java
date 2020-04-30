package com.example.pa.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pa.Model.CClass;
import com.example.pa.R;

import java.util.ArrayList;

public class ListFindClassAdapter extends RecyclerView.Adapter<ListFindClassAdapter.CClasViewHolder> {
    private ArrayList<CClass> listCClass;
    private ListFindClassListener listFindClassListener;
    public ListFindClassAdapter(ListFindClassListener listFindClassListener){
        this.listFindClassListener = listFindClassListener;
    }

    public void setListCClass(ArrayList<CClass> listCClass) {
        this.listCClass = listCClass;
    }

    @NonNull
    @Override
    public CClasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_class_search,parent,false);
        return new CClasViewHolder(view,listFindClassListener);
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
        Button btnJoin;

        public CClasViewHolder(@NonNull View itemView,ListFindClassListener listClassListener) {
            super(itemView);
            name = itemView.findViewById(R.id.class_name);
            description = itemView.findViewById(R.id.description);
            btnJoin = itemView.findViewById(R.id.btn_join);
            btnJoin.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listFindClassListener.onClick(listCClass.get(getAdapterPosition()));
        }
    }

    public interface ListFindClassListener{
        void onClick(CClass cClass);
    }
}
