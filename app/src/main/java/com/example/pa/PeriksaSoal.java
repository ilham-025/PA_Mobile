package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pa.Model.Answer;
import com.example.pa.Model.AnswerNumber;
import com.example.pa.Model.Problem;
import com.example.pa.Model.ProblemNumber;
import com.example.pa.Model.User;
import com.example.pa.Request.Request;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class PeriksaSoal extends AppCompatActivity implements Request.CheckProblemCallBack, Request.NilaiCallBack, View.OnClickListener, Request.ShowNilaiCallBack {
    LinearLayout lyJawaban;
    TextView edtJudul, edtName;
    private Request request;
    EditText edtNilai;
    Button btnSave;
    Problem problem;
    User user;
    ImageButton btnBack;

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
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        request = new Request(this);


        problem = getIntent().getParcelableExtra(EXTRA_PROBLEM);
        if(problem.getTitle()!=null){
            edtJudul.setText(problem.getTitle());
        }
        user = getIntent().getParcelableExtra(EXTRA_USER);
        if(user.getNama()!=null){
            edtName.setText("Nama Siswa: "+user.getNama());
        }
        request.CheckStudentAnswerNumber(getIntent().getIntExtra(EXTRA_ANSWER_ID,0),getIntent().getIntExtra(EXTRA_PROBLEM_ID,0),this);
        btnSave.setOnClickListener(this);
    }


    @Override
    public void onSuccess(ArrayList<ProblemNumber> problemNumbers, ArrayList<AnswerNumber> answerNumbers) {
        int item = 1;
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_condensed_regular);
        for(int i = 0 ;i<problemNumbers.size();i++){
            TextView txt = new TextView(this);
            txt.setTypeface(typeface);
            txt.setTextColor(Color.BLACK);
            txt.setText(item+". ");
            txt.setTextSize(20);

            TextView txtSoal = new TextView(this);
            txtSoal.setWidth(lyJawaban.getWidth());
            txtSoal.setText(problemNumbers.get(i).getPertanyaan());
            txtSoal.setId(View.generateViewId());
            txtSoal.setTypeface(typeface);
            txtSoal.setTextColor(Color.BLACK);
            txtSoal.setTextSize(20);

            TextView txtJawabanAsli = new TextView(this);
            txtJawabanAsli.setWidth(lyJawaban.getWidth());
            txtJawabanAsli.setText("(Jawaban Benar: "+problemNumbers.get(i).getJawaban()+")");
            txtJawabanAsli.setId(View.generateViewId());
            txtJawabanAsli.setGravity(Gravity.RIGHT);
            txtJawabanAsli.setTypeface(typeface);

            TextView txtJawabanMurid = new TextView(this);
            txtJawabanMurid.setWidth(lyJawaban.getWidth());
            txtJawabanMurid.setText("Jawaban: "+answerNumbers.get(i).getText());
            txtJawabanMurid.setId(View.generateViewId());
            txtJawabanMurid.setTypeface(typeface);
            txtJawabanMurid.setTextColor(Color.BLACK);
            txtJawabanMurid.setTextSize(18);

            LinearLayout lay = new LinearLayout(this);
            lay.setOrientation(LinearLayout.VERTICAL);
            lay.addView(txtSoal);
            lay.addView(txtJawabanMurid);
            lay.addView(txtJawabanAsli);

            LinearLayout lay2 = new LinearLayout(this);
            lay2.setOrientation(LinearLayout.HORIZONTAL);
            lay2.addView(txt);
            lay2.addView(lay);
            lyJawaban.addView(lay2);
            item++;
        }
        Log.d("id problem",String.valueOf(getIntent().getIntExtra(EXTRA_PROBLEM_ID,0)));
        Log.d("id_user", String.valueOf(user.getId()));
        request.showNilai(getIntent().getIntExtra(EXTRA_PROBLEM_ID,0),101, this);
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
        } else if (v.getId() == R.id.btn_back) {
            finish();
        }
    }

    @Override
    public void onSuccessShow(ArrayList<Answer> answer) {
        for (int i = 0;i<answer.size(); i++ ) {
            Log.d("nilai",String.valueOf(answer.get(i).getNilai()));
            edtNilai.setText(String.valueOf(answer.get(i).getNilai()));
        }
    }

    @Override
    public void onErrorShow() {

    }
}
