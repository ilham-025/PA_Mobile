package com.example.pa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pa.Model.CClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeClassLecturerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    public static String CCLASS = "cclass";

    private CClass cClass;
    private TextView toolbar;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_class_lecturer);

        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadFragment(new FragmentHomeLecturer());
        toolbar.setText("Pengumuman");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main_lct);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if(getIntent().getParcelableExtra(CCLASS)!=null){
            cClass = getIntent().getParcelableExtra(CCLASS);
        }
    }


    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container_lct, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.home_menu_lct :
                fragment = new FragmentHomeLecturer();
                toolbar.setText("Pengumuman");
                break;
            case R.id.student_menu_lct :
                fragment = new FragmentStudentLecturer();
                toolbar.setText("Daftar Siswa");
                break;
            case R.id.question_menu_lct :
                fragment = new FragmentQuestionLecturer();
                toolbar.setText("Daftar Tugas");
                break;
            case R.id.profile_menu_lct:
                fragment = new FragmentProfileStudent();
                toolbar.setText("Profil");
                break;
        }
        return loadFragment(fragment);
    }
}
