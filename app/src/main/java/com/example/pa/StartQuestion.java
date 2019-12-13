package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StartQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_question);

        LinearLayout lySoal = findViewById(R.id.ly_soal);

        int item = 1;
        TextView txt = new TextView(StartQuestion.this);
        txt.setText(item+". ");

        TextView txtSoal = new TextView(StartQuestion.this);
        txtSoal.setWidth(lySoal.getWidth());
        txtSoal.setHint("Pertanyaan");
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
