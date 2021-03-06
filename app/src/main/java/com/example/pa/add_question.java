package com.example.pa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.pa.Model.CClass;
import com.example.pa.Model.Problem;
import com.example.pa.Model.ProblemNumber;
import com.example.pa.Request.Request;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class add_question extends AppCompatActivity implements View.OnClickListener, Request.OnServerPostCallBack {
    protected ArrayList<EditText> listEditTextPertanyaan;
    protected ArrayList<EditText> listEditTextJawaban;
    protected Calendar myCalendar;
    protected DatePickerDialog.OnDateSetListener date_start, date_finish;
    protected ImageButton btnAddQuestion;
    protected LinearLayout lySoal;
    protected Button btnCreateSoal;
    protected EditText tglmulai, tglselesai, jammulai, jamselesai,edt_judul_soal;
    protected Request request;
    private ImageButton btnBack;
    public static String CCLASS = "cclass";
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);

        request = new Request(this);

        btnAddQuestion = findViewById(R.id.add_question);
        btnCreateSoal = findViewById(R.id.btn_create_question);
        lySoal = findViewById(R.id.ly_soal);
        tglmulai = findViewById(R.id.tgl_mulai);
        jammulai = findViewById(R.id.jam_mulai);
        tglselesai = findViewById(R.id.tgl_selesai);
        jamselesai = findViewById(R.id.jam_selesai);
        edt_judul_soal = findViewById(R.id.judul_soal);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        listEditTextJawaban = new ArrayList<EditText>();
        listEditTextPertanyaan = new ArrayList<EditText>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            edt_judul_soal.setFocusedByDefault(true);
        }

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
                        jamselesai.setText(selectedHour+":" +selectedMinute);
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
            Typeface typeface = ResourcesCompat.getFont(add_question.this, R.font.roboto_condensed_regular);
            @Override
            public void onClick(View v) {
                TextView txt = new TextView(add_question.this);
                txt.setText(item+". ");

                EditText edt = new EditText(add_question.this);
                edt.setWidth(lySoal.getWidth());
                edt.setTypeface(typeface);
                edt.setHint("Pertanyaan");
                edt.setId(View.generateViewId());
                EditText edt2 = new EditText(add_question.this);
                edt2.setTypeface(typeface);
                edt2.setHint("Jawaban");
                edt2.setWidth(lySoal.getWidth());
                edt2.setId(View.generateViewId());

                LinearLayout lay = new LinearLayout(add_question.this);
                lay.setOrientation(LinearLayout.VERTICAL);
                lay.addView(edt);
                lay.addView(edt2);

                listEditTextPertanyaan.add(edt);
                listEditTextJawaban.add(edt2);

                LinearLayout lay2 = new LinearLayout(add_question.this);
                lay2.setOrientation(LinearLayout.HORIZONTAL);
                lay2.setGravity(Gravity.CENTER);
                lay2.addView(txt);
                lay2.addView(lay);
                lySoal.addView(lay2);
                item++;
            }
        });

        btnCreateSoal.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        ArrayList<ProblemNumber> listProblemNuber = new ArrayList<ProblemNumber>();
        if(v.getId()==R.id.btn_create_question){
            String title = edt_judul_soal.getText().toString().trim();
            String startTime = jammulai.getText().toString().trim();
            String startDate = tglmulai.getText().toString().trim();
            String endTime = jamselesai.getText().toString().trim();
            String endDate = tglselesai.getText().toString().trim();
            Boolean empty = true;
            if(TextUtils.isEmpty(title) || TextUtils.isEmpty(startDate) ||TextUtils.isEmpty(endTime) ||TextUtils.isEmpty(endDate) ||TextUtils.isEmpty(startTime)){
                empty=true;
            }else{
                empty=false;
            }
            if(empty==false && listEditTextJawaban.size() >0 ) {
                Problem problem = new Problem();
                problem.setStartTime(startTime);
                problem.setTitle(title);
                problem.setStartDate(startDate);
                problem.setEndTime(endTime);
                problem.setEndDate(endDate);
                CClass cClass = getIntent().getParcelableExtra(add_question.CCLASS);
                problem.setClass_id(cClass.getId());
                for (int i = 0; i < listEditTextJawaban.size(); i++) {
                    ProblemNumber problemNumber = new ProblemNumber();
                    problemNumber.setNumber(i);
                    problemNumber.setPertanyaan(listEditTextPertanyaan.get(i).getText().toString().trim());
                    problemNumber.setJawaban(listEditTextJawaban.get(i).getText().toString().trim());
                    listProblemNuber.add(problemNumber);
                }
//            Log.d("lol",problem.getStartTime());
//            Log.d("lil",problem.getStartDate());
                Log.d("lel", endTime);
//            Log.d("lal",problem.getEndDate());

                request.addProblem(problem, listProblemNuber);
                finish();
            }else{
                showSnackbarMessage("tolong diisi semua fieldnya");
            }
        } else if (v.getId() == R.id.btn_back) {
            finish();
        }
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
    public void showSnackbarMessage(String message){
        Snackbar.make(getCurrentFocus(),message,Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onSuccess(String message) {
        showSnackbarMessage(message);
    }

    @Override
    public void onError() {

    }
}
