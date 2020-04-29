package com.example.pa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pa.Adapter.ListClassAdapter;
import com.example.pa.Model.Auth;
import com.example.pa.Model.CClass;
import com.example.pa.viewmodel.ClassViewModel;
import com.example.pa.viewmodel.RequestError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeKelasFragment extends Fragment implements RequestError, ListClassAdapter.ListClassListener {

    FloatingActionButton btnAddClass;
    private ListClassAdapter listClassAdapter;
    private ClassViewModel classViewModel;
    private RecyclerView recyclerView;
    private ArrayList<CClass> listCClass = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_kelas, container, false);
        btnAddClass = view.findViewById(R.id.btn_add_class);
        recyclerView = view.findViewById(R.id.rv_class_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listClassAdapter = new ListClassAdapter(this);
        listClassAdapter.setListCClass(listCClass);
        recyclerView.setAdapter(listClassAdapter);
        classViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ClassViewModel.class);
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move;
                if(Auth.user.getRole().equals("teacher")) {
                    move = new Intent(getContext(), BuatKelasActivity.class);
                }else {
                    move = new Intent(getContext(), GabungKelasActivity.class);
                }
                startActivity(move);
            }
        });
        final ListClassAdapter.ListClassListener listClassListener = this;
        classViewModel.setContext(this.getContext());
        classViewModel.setListclass(this,Auth.user);
        classViewModel.getListCClass().observe(this, new Observer<ArrayList<CClass>>() {
            @Override
            public void onChanged(ArrayList<CClass> cClasses) {
//                Log.d("isi kelas",cClasses.get(0).getName());
                listClassAdapter.setListCClass(cClasses);
                listClassAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onError(int message) {

    }

    @Override
    public void onClick(CClass cClass) {

    }
}
