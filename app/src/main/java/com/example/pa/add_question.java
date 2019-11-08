package com.example.pa;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class add_question extends AppCompatActivity {
    protected ImageButton btnAddQuestion;
    protected LinearLayout lySoal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);

        btnAddQuestion = findViewById(R.id.add_question);
        lySoal = findViewById(R.id.ly_soal);

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            int item = 1;
            @Override
            public void onClick(View v) {
                TextView txt = new TextView(add_question.this);
                txt.setText("Soal "+item);
                EditText edt = new EditText(add_question.this);
                TextView txt2 = new TextView(add_question.this);
                txt2.setText("Jawaban Soal "+item);
                EditText edt2 = new EditText(add_question.this);
                LinearLayout lay = new LinearLayout(add_question.this);
                lay.setOrientation(LinearLayout.VERTICAL);
                lay.setPadding(0,0,0,10);
                lay.addView(txt);
                lay.addView(edt);
                lay.addView(txt2);
                lay.addView(edt2);
                lySoal.addView(lay);
                item++;
            }
        });
    }
}
