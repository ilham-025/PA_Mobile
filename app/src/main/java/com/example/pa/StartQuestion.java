package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pa.Model.Answer;
import com.example.pa.Model.AnswerNumber;
import com.example.pa.Model.Auth;
import com.example.pa.Model.Problem;
import com.example.pa.Model.ProblemNumber;
import com.example.pa.Request.Request;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StartQuestion extends AppCompatActivity implements Request.ProblemNumberReady,View.OnClickListener, Request.AddProblemCallBack {
    LinearLayout lySoal;
    TextView edtJudul;
    private Request request;
    Button btnFinish;
    ArrayList<EditText> listEditTextAnswers;
    Problem problem;
    ImageButton btnBack;
    public static String EXTRA_PROBLEM_ID = "extra_problem_id";
    public static String EXTRA_PROBLEM = "extra_problem";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_question);

        btnFinish = findViewById(R.id.btn_finish);
        edtJudul = findViewById(R.id.tv_judul_soal);
        lySoal = findViewById(R.id.ly_soal);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        request = new Request(this);
        request.getProblemNumber(getIntent().getIntExtra(EXTRA_PROBLEM_ID, 0), this);

        problem = getIntent().getParcelableExtra(EXTRA_PROBLEM);
        if(problem.getTitle()!=null){
            edtJudul.setText(problem.getTitle());
        }
//        getSupportActionBar().setTitle(problem.getTitle());

        listEditTextAnswers = new ArrayList<EditText>();

        btnFinish.setOnClickListener(this);
        Log.d("lolol", String.valueOf(getIntent().getIntExtra(EXTRA_PROBLEM_ID, 0)));

    }

    @Override
    public void onSucces(ArrayList<ProblemNumber> list) {
        int item = 1;
        for(int i = 0 ;i<list.size();i++){
            Log.d("lololo", list.get(i).getPertanyaan());
            TextView txt = new TextView(StartQuestion.this);
            txt.setText(item+". ");

            TextView txtSoal = new TextView(StartQuestion.this);
            txtSoal.setWidth(lySoal.getWidth());
            txtSoal.setText(list.get(i).getPertanyaan());
            txtSoal.setId(View.generateViewId());
            txtSoal.setTextSize(15);

            EditText edt2 = new EditText(StartQuestion.this);
            edt2.setHint("Jawaban");
            edt2.setWidth(lySoal.getWidth());
            edt2.setId(View.generateViewId());

            LinearLayout lay = new LinearLayout(StartQuestion.this);
            lay.setOrientation(LinearLayout.VERTICAL);
            lay.addView(txtSoal);
            lay.addView(edt2);

            LinearLayout lay2 = new LinearLayout(StartQuestion.this);
            lay2.setOrientation(LinearLayout.HORIZONTAL);
            lay2.addView(txt);
            lay2.addView(lay);
            lySoal.addView(lay2);
            listEditTextAnswers.add(edt2);
            item++;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_finish){
            ArrayList<AnswerNumber> listAnswerNumber = new ArrayList<AnswerNumber>();
            Answer answer = new Answer();
            answer.setUser_id(Auth.user.getId());
            answer.setProblem_id(problem.getId());
            for(int i = 0;i<listEditTextAnswers.size();i++){
                AnswerNumber answerNumber = new AnswerNumber();
                answerNumber.setText(listEditTextAnswers.get(i).getText().toString().trim());
                listAnswerNumber.add(answerNumber);
            }
            request.addAnswer(answer,listAnswerNumber,this);
        } else if (v.getId() == R.id.btn_back) {
            finish();
        }
    }
    public void showSnackbarMessage(String message){
        Snackbar.make(getCurrentFocus(),message,Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void success(String Message) {
        showSnackbarMessage(Message);
    }

    @Override
    public void error(String Message) {

    }
}
