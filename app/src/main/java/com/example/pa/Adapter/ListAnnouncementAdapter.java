package com.example.pa.Adapter;

import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.pa.Model.Announcement;
import com.example.pa.Model.LinkExtractor;
import com.example.pa.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

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
        Spanned text;
        announcement = announcements.get(position);
        holder.tvName.setText(announcement.getName());
        holder.tvDate.setText(announcement.getDate());
        Glide.with(holder.itemView.getContext())
                .load("http://brokenfortest")
                .placeholder(AvatarGenerator.Companion.avatarImage(holder.itemView.getContext(), 200, AvatarConstants.Companion.getCIRCLE(), announcement.getName()))
                .into(holder.img);
        String link = LinkExtractor.extractUrls(announcement.getText());
        if(!link.equals("")){
            text = Html.fromHtml(announcement.getText()+"<a href='"+link+"'> Menuju Link"+"</a>");
        }else {
            text = Html.fromHtml(announcement.getText());
        }
        holder.tvAnnoucement.setMovementMethod(LinkMovementMethod.getInstance());
        holder.tvAnnoucement.setText(text);
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class AnnoucementViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName,tvAnnoucement,tvDate;
        CircleImageView img;
        public AnnoucementViewHoler(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.lecturer_name);
            tvAnnoucement = itemView.findViewById(R.id.announce);
            tvDate = itemView.findViewById(R.id.tv_date);
            img = itemView.findViewById(R.id.img_item_photo_lecturer);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface OnAnnouncementListListener{
        public void onClick();
    }
}
