package com.example.pa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pa.Model.Auth;
import com.example.pa.Model.CClass;
import com.example.pa.Request.Request;
import com.google.android.material.snackbar.Snackbar;

public class BuatKelasActivity extends AppCompatActivity implements Request.AddClasstCallBack {
    private EditText edtCode;
    private EditText edtName;
    private EditText edtDescription;
    private Button btnAddClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_kelas);
        edtName = findViewById(R.id.edt_class_name);
        edtCode = findViewById(R.id.edt_class_code);
        edtDescription = findViewById(R.id.edt_description_class);
        btnAddClass = findViewById(R.id.btn_add_class);
        final Request.AddClasstCallBack addClasstCallBack = this;
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.requestFocus();
                edtName.setSelected(true);
                String name = edtName.getText().toString().trim();
                String code = edtCode.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                if(name.isEmpty()||code.isEmpty()||description.isEmpty()){
                    showSnackbarMessage("please fill everything ");
                }else{
                    CClass cClass = new CClass();
                    cClass.setName(name);
                    cClass.setDescription(description);
                    cClass.setCode(code);
                    cClass.setTeacher_id(Auth.user.getId());
                    new Request(getApplicationContext()).addClass(cClass,addClasstCallBack);
                    edtCode.setText("");
                    edtDescription.setText("");
                    edtName.setText("");
                }
            }
        });
    }
    public void showSnackbarMessage(String message){
        try {
            Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
        }catch (Exception $e){

        }
    }

    @Override
    public void onSuccessAdd() {
        showSnackbarMessage("adding class success");
    }

    @Override
    public void onErrorAdd() {

    }
}
