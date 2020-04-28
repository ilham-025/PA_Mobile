package com.example.pa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pa.Model.Auth;
import com.example.pa.Request.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.Objects;


public class HomeKelasFragment extends Fragment{

    FloatingActionButton btnAddClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_kelas, container, false);
        btnAddClass = view.findViewById(R.id.btn_add_class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

}
