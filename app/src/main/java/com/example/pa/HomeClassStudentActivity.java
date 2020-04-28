package com.example.pa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeClassStudentActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_class_student);
        loadFragment(new FragmentHomeStudent());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main_std);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container_std, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.home_menu_std :
                fragment = new FragmentHomeStudent();
                break;
            case R.id.question_menu_std :
                fragment = new FragmentQuestionStudent();
                break;
            case R.id.profile_menu_std :
                fragment = new FragmentProfileStudent();
                break;
        }
        return loadFragment(fragment);
    }
}