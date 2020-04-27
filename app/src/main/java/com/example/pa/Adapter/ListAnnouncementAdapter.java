package com.example.pa.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pa.Model.Announcement;
import com.example.pa.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAnnouncementAdapter extends RecyclerView.Adapter<ListAnnouncementAdapter.AnnoucementViewHoler> {

    private ArrayList<Announcement> announcements;

    public void setListAnnouncement(ArrayList<Announcement> announcements){
        this.announcements = announcements;
    }
    @NonNull
    @Override
    public AnnoucementViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_announce,parent,false);
        return new AnnoucementViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnoucementViewHoler holder, int position) {
        Announcement announcement = new Announcement();
        announcement = announcements.get(position);
        holder.tvName.setText(announcement.getName());
        holder.tvDate.setText(announcement.getDate());
        holder.tvAnnoucement.setText(announcement.getText());
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class AnnoucementViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName,tvAnnoucement,tvDate;
        public AnnoucementViewHoler(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.lecturer_name);
            tvAnnoucement = itemView.findViewById(R.id.announce);
            tvDate = itemView.findViewById(R.id.tv_date);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface OnAnnouncementListListener{
        public void onClick();
    }
//    public interface ServerCallBack{
//        void onSuccessLoad();
//    }
}
