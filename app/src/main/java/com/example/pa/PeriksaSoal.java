package com.example.pa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.ArrayList;

public class PeriksaSoal extends AppCompatActivity {
    LinearLayout lyJawaban;
    TextView edtJudul, edtName;
    private Request request;
    Button btnSave;
    Problem problem;
    User user;

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

        request = new Request(this);


        problem = getIntent().getParcelableExtra(EXTRA_PROBLEM);
        if(problem.getTitle()!=null){
            edtJudul.setText(problem.getTitle());
        }
        user = getIntent().getParcelableExtra(EXTRA_USER);
        if(user.getNama()!=null){
            edtName.setText(user.getNama());
        }
    }

    public void onSucces(ArrayList<ProblemNumber> list, ArrayList<AnswerNumber> list2) {
        int item = 1;
        for(int i = 0 ;i<list.size();i++){
            Log.d("lololo", list.get(i).getPertanyaan());
            TextView txt = new TextView(this);
            txt.setText(item+". ");

            TextView txtSoal = new TextView(this);
            txtSoal.setWidth(lyJawaban.getWidth());
            txtSoal.setText(list.get(i).getPertanyaan());
            txtSoal.setId(View.generateViewId());
            txtSoal.setTextSize(15);

            TextView txtJawabanAsli = new TextView(this);
            txtJawabanAsli.setWidth(lyJawaban.getWidth());
            txtJawabanAsli.setText(list.get(i).getJawaban());
            txtJawabanAsli.setId(View.generateViewId());
            txtJawabanAsli.setTextSize(15);

            TextView txtJawabanMurid = new TextView(this);
            txtJawabanMurid.setWidth(lyJawaban.getWidth());
            txtJawabanMurid.setText(list2.get(i).getText());
            txtJawabanMurid.setId(View.generateViewId());
            txtJawabanMurid.setTextSize(15);

            LinearLayout lay = new LinearLayout(this);
            lay.setOrientation(LinearLayout.VERTICAL);
            lay.addView(txtSoal);
            lay.addView(txtJawabanAsli);

            LinearLayout lay2 = new LinearLayout(this);
            lay2.setOrientation(LinearLayout.HORIZONTAL);
            lay2.addView(txt);
            lay2.addView(lay);
            lyJawaban.addView(lay2);
            item++;
        }
    }
}
