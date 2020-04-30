package com.example.pa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pa.Model.Auth;
import com.example.pa.db.UserHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentProfileStudent extends Fragment implements View.OnClickListener {
    TextView tvName, tvEmail;
    private Button btnLogout;
    UserHelper userHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile_student, container, false);
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        btnLogout = view.findViewById(R.id.btn_logout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userHelper = UserHelper.getInstance(getContext());
        userHelper.open();
        tvName.setText(Auth.user.getNama());
        tvEmail.setText(Auth.user.getEmail());
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            Log.d("test logiut","clicked");
            userHelper.delete(Auth.user.getId());
            Intent move = new Intent(this.getActivity(),MainActivity.class);
            move.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(move);
            getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userHelper.close();
    }
}
