package com.example.pa.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.pa.Model.User;
import com.example.pa.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListStudentAdapter extends RecyclerView.Adapter<ListStudentAdapter.UserViewHolder> {
    private ArrayList<User> users;
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
        User user = users.get(position);
        holder.tvNama.setText(user.getNama());
        holder.tvEmaill.setText(user.getEmail());
        Glide.with(holder.itemView.getContext())
                .load("http://brokenfortest")
                .placeholder(AvatarGenerator.Companion.avatarImage(holder.itemView.getContext(), 200, AvatarConstants.Companion.getCIRCLE(), user.getNama()))
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setListStudent(ArrayList<User> users){
        this.users = users;
    }
    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvNama,tvEmaill;
        CircleImageView img;
        public UserViewHolder(@NonNull View itemView, OnStudentListListener onStudentListListener) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_name_student);
            tvEmaill = itemView.findViewById(R.id.tv_email_student);
            img = itemView.findViewById(R.id.img_item_photo_student);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStudentListListener.onClick(getAdapterPosition(), users.get(getAdapterPosition()),true);
        }
    }
    public interface OnStudentListListener{
        void onClick(int position, User user, boolean isEdit);

    }
}
