package com.example.pa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeClassLecturerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_class_lecturer);
        loadFragment(new FragmentHomeLecturer());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main_lct);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
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
                break;
            case R.id.student_menu_lct :
                fragment = new FragmentStudentLecturer();
                break;
            case R.id.question_menu_lct :
                fragment = new FragmentQuestionLecturer();
                break;
            case R.id.profile_menu_lct:
                fragment = new FragmentProfileStudent();
                break;
        }
        return loadFragment(fragment);
    }
}
