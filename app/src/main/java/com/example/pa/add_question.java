package com.example.pa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class add_question extends AppCompatActivity {
    protected Calendar myCalendar;
    protected DatePickerDialog.OnDateSetListener date_start, date_finish;
    protected ImageButton btnAddQuestion;
    protected LinearLayout lySoal;
    protected EditText tglmulai, tglselesai, jammulai, jamselesai;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);

        btnAddQuestion = findViewById(R.id.add_question);
        lySoal = findViewById(R.id.ly_soal);
        tglmulai = findViewById(R.id.tgl_mulai);
        jammulai = findViewById(R.id.jam_mulai);
        tglselesai = findViewById(R.id.tgl_selesai);
        jamselesai = findViewById(R.id.jam_selesai);

        myCalendar = Calendar.getInstance();
        date_start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        date_finish = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelFinish();
            }
        };

        jammulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(add_question.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        jammulai.setText(String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        jamselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(add_question.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        jamselesai.setText(String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        tglmulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(add_question.this, date_start, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tglselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(add_question.this, date_finish, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

    private void updateLabelStart() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tglmulai.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelFinish() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tglselesai.setText(sdf.format(myCalendar.getTime()));
    }
}
