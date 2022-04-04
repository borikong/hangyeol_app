package com.naver.naverspeech.client;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

public class ResultActivity2 extends AppCompatActivity {

    ArrayList<Entry> entries;
    private ProgressDialog pd;
    SQLiteHelper1 dbHelper1;
    private int score[] = {8,7,9,8,6};
    private String userName = "song";
    TextView scoreText;
    double avg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        //dbHelper1 = new SQLiteHelper1(getApplicationContext(), "Score.db", null, 1);
        //score = dbHelper1.getScore("song");

        avg = (score[0] + score[1]+ score[2] + score[3] + score[4])/5;
        scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText("Your Socre : "+ avg);

        pd = new ProgressDialog(ResultActivity2.this);
        pd.setMessage("loading");

        Log.e("SONGTest","점수확인용"+score[0]+ score[1]+ score[2]+ score[3]);
        entries = new ArrayList<>();


    }
}
