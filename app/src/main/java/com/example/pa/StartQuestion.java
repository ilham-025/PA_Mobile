package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pa.Model.ProblemNumber;
import com.example.pa.Request.Request;

import java.util.ArrayList;

public class StartQuestion extends AppCompatActivity implements Request.ProblemNumberReady {
    LinearLayout lySoal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_question);

        lySoal = findViewById(R.id.ly_soal);
    }

    @Override
    public void onSucces(ArrayList<ProblemNumber> list) {
        for(int i = 0 ;i<list.size();i++){
            int item = 1;
            TextView txt = new TextView(StartQuestion.this);
            txt.setText(item+". ");

            TextView txtSoal = new TextView(StartQuestion.this);
            txtSoal.setWidth(lySoal.getWidth());
            txtSoal.setText(list.get(i).getPertanyaan());
            txtSoal.setId(View.generateViewId());
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
