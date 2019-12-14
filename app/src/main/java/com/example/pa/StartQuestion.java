package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pa.Model.ProblemNumber;
import com.example.pa.Request.Request;

import java.util.ArrayList;

public class StartQuestion extends AppCompatActivity implements Request.ProblemNumberReady {
    LinearLayout lySoal;
    private Request request;

    public static String EXTRA_PROBLEM_ID = "extra_problem_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_question);

        lySoal = findViewById(R.id.ly_soal);
        request = new Request(this);
        request.getProblemNumber(getIntent().getIntExtra(EXTRA_PROBLEM_ID, 0), this);

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
            item++;
        }
    }
}
