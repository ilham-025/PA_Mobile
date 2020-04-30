package com.example.pa;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pa.Adapter.ListClassAdapter;
import com.example.pa.Adapter.ListFindClassAdapter;
import com.example.pa.Model.Auth;
import com.example.pa.Model.CClass;
import com.example.pa.viewmodel.ClassViewModel;
import com.example.pa.viewmodel.FindClassViewModel;
import com.example.pa.viewmodel.RequestError;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class GabungKelasActivity extends AppCompatActivity implements RequestError, ListFindClassAdapter.ListFindClassListener {

    EditText edtSearch;
    private ListFindClassAdapter listClassAdapter;
    private FindClassViewModel classViewModel;
    private RecyclerView recyclerView;
    private ProgressBar pgListClass;
    private ArrayList<CClass> listCClass = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gabung_kelas);
        edtSearch = findViewById(R.id.class_code);
        recyclerView = findViewById(R.id.rv_class_list);
        pgListClass = findViewById(R.id.pg_list_class);


        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        search(edtSearch.getText().toString());
                    }
                    return false;
                }
                return false;
            }
        });



    }

    @Override
    public void onClick(CClass cClass) {

    }

    @Override
    public void onError(int message) {

    }

    public void search(String code) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listClassAdapter = new ListFindClassAdapter(this);
        listClassAdapter.setListCClass(listCClass);
        recyclerView.setAdapter(listClassAdapter);
        classViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FindClassViewModel.class);

        final ListFindClassAdapter.ListFindClassListener listFindClassListener = this;
        classViewModel.setContext(this);
        classViewModel.setListclass(this, code);
        classViewModel.getListCClass().observe(this, new Observer<ArrayList<CClass>>() {
            @Override
            public void onChanged(ArrayList<CClass> cClasses) {

                Log.d("isi kelas","ada");
                listClassAdapter.setListCClass(cClasses);
                listClassAdapter.notifyDataSetChanged();
                pgListClass.setVisibility(View.GONE);
            }
        });

    }
}
