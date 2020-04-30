package com.example.pa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pa.Adapter.ListAnnouncementAdapter;
import com.example.pa.Model.Announcement;
import com.example.pa.Model.CClass;
import com.example.pa.Request.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentHomeStudent extends Fragment{
    private Request request;
    private RecyclerView rvAnnouncement;
    private ListAnnouncementAdapter listAnnouncementAdapter;
    private Announcement announcement;
    private ArrayList<Announcement> list;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_student, container, false);
        rvAnnouncement = view.findViewById(R.id.rv_announcement);
        progressBar = view.findViewById(R.id.pg_announcement);
        rvAnnouncement.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<Announcement>();
        request = new Request(getContext());
        listAnnouncementAdapter = new ListAnnouncementAdapter();
        listAnnouncementAdapter.setListAnnouncement(list);
        rvAnnouncement.setAdapter(listAnnouncementAdapter);
        new FragmentHomeStudent.LoadAnnouncementAsync().execute();
    }

    public interface onServerCallBack{
        void onSuccessLoad(ArrayList<Announcement> announcements);
    }
    public class LoadAnnouncementAsync extends AsyncTask<Void,Void,Void> implements onServerCallBack, FragmentHomeLecturer.onServerCallBack {

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
