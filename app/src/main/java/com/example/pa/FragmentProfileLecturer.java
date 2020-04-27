package com.example.pa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pa.Model.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentProfileLecturer extends Fragment {
    TextView tvName,tvEmail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_lecturer, container, false);
        tvEmail = view.findViewById(R.id.tv_email);
        tvName = view.findViewById(R.id.tv_name);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvName.setText(Auth.user.getNama());
        tvEmail.setText(Auth.user.getEmail());
    }
}