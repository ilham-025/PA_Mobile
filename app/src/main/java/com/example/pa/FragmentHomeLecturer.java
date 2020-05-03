package com.example.pa;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.pa.Adapter.ListAnnouncementAdapter;
import com.example.pa.Model.Announcement;
import com.example.pa.Model.Auth;
import com.example.pa.Model.CClass;
import com.example.pa.Request.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentHomeLecturer extends Fragment implements View.OnClickListener {
    private Request request;
    private RecyclerView rvAnnouncement;
    private ListAnnouncementAdapter listAnnouncementAdapter;
    private Announcement announcement;
    private ArrayList<Announcement> list;
    private ProgressBar progressBar;
    private ImageButton btnAddAnnouncement;
    private EditText edtAnnouncemnet;
    private CircleImageView img;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_lecturer, container, false);
        rvAnnouncement = view.findViewById(R.id.rv_announcement);
        progressBar = view.findViewById(R.id.pg_announcement);
        rvAnnouncement.setLayoutManager(new LinearLayoutManager(this.getContext()));
        btnAddAnnouncement = view.findViewById(R.id.btn_add_announcement);
        edtAnnouncemnet = view.findViewById(R.id.edt_announcement);
        img = view.findViewById(R.id.img_item_photo_home);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<Announcement>();
        btnAddAnnouncement.setOnClickListener(this);
        request = new Request(getContext());
        listAnnouncementAdapter = new ListAnnouncementAdapter();
        listAnnouncementAdapter.setListAnnouncement(list);
        rvAnnouncement.setAdapter(listAnnouncementAdapter);
        new LoadAnnouncementAsync().execute();
        Glide.with(this)
                .load("http://brokenfortest")
                .placeholder(AvatarGenerator.Companion.avatarImage(getContext(), 200, AvatarConstants.Companion.getCIRCLE(), Auth.user.getNama()))
                .into(img);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add_announcement){
            String text = edtAnnouncemnet.getText().toString().trim();
            if(!TextUtils.isEmpty(text)){
                Announcement announcement = new Announcement();
                announcement.setName(Auth.user.getNama());
                announcement.setText(text);
                announcement.setDate(getCurrentDate());
                CClass cClass = getActivity().getIntent().getParcelableExtra(HomeClassLecturerActivity.CCLASS);
                announcement.setClass_id(cClass.getId());

                new AddAnnouncementAsync(announcement).execute();
                edtAnnouncemnet.setText("");

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnAddAnnouncement.getWindowToken(), 0);
            }else{
                edtAnnouncemnet.setError("Kosong");
            }
        }
    }
    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public interface onServerCallBack{
        void onSuccessLoad(ArrayList<Announcement> announcements);
        void onSuccesAdd();
    }
    public class AddAnnouncementAsync extends AsyncTask<Void,Void,Void> implements onServerCallBack{
        Announcement announcement;
        public AddAnnouncementAsync(Announcement announcement) {
            this.announcement = announcement;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            request.addAnnouncement(announcement,this);
            return null;
        }

        @Override
        public void onSuccessLoad(ArrayList<Announcement> announcements) {

        }

        @Override
        public void onSuccesAdd() {
            new LoadAnnouncementAsync().execute();
        }
    }
    public class LoadAnnouncementAsync extends AsyncTask<Void,Void,Void> implements onServerCallBack{

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
            CClass cClass = getActivity().getIntent().getParcelableExtra(HomeClassLecturerActivity.CCLASS);
            request.getAllAnnouncement(this,cClass.getId());
            return null;
        }

        @Override
        public void onSuccessLoad(ArrayList<Announcement> announcements) {
            progressBar.setVisibility(View.GONE);
            list.addAll(announcements);
            Log.d("test","masuk");
            listAnnouncementAdapter.setListAnnouncement(list);
            listAnnouncementAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSuccesAdd() {

        }
    }
}
