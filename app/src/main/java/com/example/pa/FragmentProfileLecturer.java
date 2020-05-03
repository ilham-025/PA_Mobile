package com.example.pa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.pa.Model.Auth;
import com.example.pa.db.UserHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfileLecturer extends Fragment implements View.OnClickListener{
    TextView tvName,tvEmail;
    CircleImageView img;
    private Button btnLogout;
    UserHelper userHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_lecturer, container, false);
        tvEmail = view.findViewById(R.id.tv_email);
        tvName = view.findViewById(R.id.tv_name);
        btnLogout = view.findViewById(R.id.btn_logout);
        img = view.findViewById(R.id.img_item_photo_lecturer);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userHelper = UserHelper.getInstance(getContext());
        userHelper.open();

        Log.d("test logiut","before clicked");
        tvName.setText(Auth.user.getNama());
        tvEmail.setText(Auth.user.getEmail());
        btnLogout.setOnClickListener(this);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        Glide.with(this)
                .load("http://brokenfortest")
                .placeholder(AvatarGenerator.Companion.avatarImage(getContext(), 200, AvatarConstants.Companion.getCIRCLE(), Auth.user.getNama()))
                .into(img);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userHelper.close();
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
}