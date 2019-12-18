package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pa.Model.AnswerNumber;
import com.example.pa.Model.Problem;
import com.example.pa.Model.ProblemNumber;
import com.example.pa.Model.User;
import com.example.pa.Request.Request;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class PeriksaSoal extends AppCompatActivity implements Request.CheckProblemCallBack, Request.NilaiCallBack, View.OnClickListener {
    LinearLayout lyJawaban;
    TextView edtJudul, edtName;
    private Request request;
    EditText edtNilai;
    Button btnSave;
    Problem problem;
    User user;

    public static String EXTRA_ANSWER_ID = "extra_answer_id";
    public static String EXTRA_PROBLEM_ID = "extra_problem_id";
    public static String EXTRA_USER_ID = "extra_user_id";
    public static String EXTRA_PROBLEM = "extra_problem";
    public static String EXTRA_USER = "extra_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periksa_soal);

        lyJawaban = findViewById(R.id.ly_jawaban);
        edtJudul = findViewById(R.id.tv_judul_soal);
        edtName = findViewById(R.id.tv_name);
        btnSave = findViewById(R.id.btn_save);
        edtNilai = findViewById(R.id.edt_nilai);

        request = new Request(this);


        problem = getIntent().getParcelableExtra(EXTRA_PROBLEM);
        if(problem.getTitle()!=null){
            edtJudul.setText(problem.getTitle());
        }
        user = getIntent().getParcelableExtra(EXTRA_USER);
        if(user.getNama()!=null){
            edtName.setText(user.getNama());
        }
        request.CheckStudentAnswerNumber(getIntent().getIntExtra(EXTRA_ANSWER_ID,0),getIntent().getIntExtra(EXTRA_PROBLEM_ID,0),this);
        btnSave.setOnClickListener(this);
    }


    @Override
    public void onSuccess(ArrayList<ProblemNumber> problemNumbers, ArrayList<AnswerNumber> answerNumbers) {
        int item = 1;
        for(int i = 0 ;i<problemNumbers.size();i++){
//            Log.d("lololo", list.get(i).getPertanyaan());
            TextView txt = new TextView(this);
            txt.setText(item+". ");

            TextView txtSoal = new TextView(this);
            txtSoal.setWidth(lyJawaban.getWidth());
            txtSoal.setText(problemNumbers.get(i).getPertanyaan());
            txtSoal.setId(View.generateViewId());
            txtSoal.setTextSize(20);

            TextView txtJawabanAsli = new TextView(this);
            txtJawabanAsli.setWidth(lyJawaban.getWidth());
            txtJawabanAsli.setText(problemNumbers.get(i).getJawaban());
            txtJawabanAsli.setId(View.generateViewId());
            txtJawabanAsli.setTextSize(20);

            TextView txtJawabanMurid = new TextView(this);
            txtJawabanMurid.setWidth(lyJawaban.getWidth());
            txtJawabanMurid.setText(answerNumbers.get(i).getText());
            txtJawabanMurid.setId(View.generateViewId());
            txtJawabanMurid.setTextSize(20);

            LinearLayout lay = new LinearLayout(this);
            lay.setOrientation(LinearLayout.VERTICAL);
            lay.addView(txtSoal);
            lay.addView(txtJawabanAsli);
            lay.addView(txtJawabanMurid);

            LinearLayout lay2 = new LinearLayout(this);
            lay2.setOrientation(LinearLayout.HORIZONTAL);
            lay2.addView(txt);
            lay2.addView(lay);
            lyJawaban.addView(lay2);
            item++;
        }
    }

    @Override
    public void onSucces(String message) {
        showSnackbarMessage(message);
    }

    @Override
    public void onError() {

    }
    public void showSnackbarMessage(String message){
        Snackbar.make(getCurrentFocus(),message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            Boolean empty = true;
            String nilai = edtNilai.getText().toString().trim();
            if (TextUtils.isEmpty(nilai)) {
                empty = true;
            }else {
                empty =false;
            }
            if (empty==false) {
                Log.d("nilai",nilai);
                request.nilai(getIntent().getIntExtra(EXTRA_ANSWER_ID, 0), Integer.parseInt(nilai), this);
            }else {
                showSnackbarMessage("Isi nilai-nya bang");
            }
        }
    }
}
