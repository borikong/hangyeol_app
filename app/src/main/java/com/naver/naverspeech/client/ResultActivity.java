package com.naver.naverspeech.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.naver.naverspeech.client.seohyun.MainActivity2;
import com.naver.naverspeech.client.soojeong.MainActivity3;
import com.naver.naverspeech.client.soojeong.SujeongTestActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends BaseActivity {
    ArrayList<Entry> entries;
    private ProgressDialog pd;
    SQLiteHelper1 dbHelper1;
    private int score[] = {0,0,0,0,0};
    private String userName = "SONG";
    TextView scoreText;
    double avg;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        dbHelper1 = new SQLiteHelper1(getApplicationContext(), "Score.db", null, 1);
        score = dbHelper1.getScore(userName);
        score[0]=7;
        avg = (score[0] + score[1]+ score[2] + score[3] + score[4])/5;
        scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText("Your Socre : "+ avg);
        btn1=(Button) findViewById(R.id.btn1);

        pd = new ProgressDialog(ResultActivity.this);
        pd.setMessage("loading");

        entries = new ArrayList<>();
        load_data_from_server();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    public void load_data_from_server() {
        pd.show();

        entries.add(new Entry(score[0], 0));
        entries.add(new Entry(score[1], 1));
        entries.add(new Entry(score[2], 2));
        entries.add(new Entry(score[3], 3));
        entries.add(new Entry(score[4], 4));

        Log.e("SONGTest","그래프 entries:"+entries);


        RadarChart chart = (RadarChart)findViewById(R.id.chartr);

        RadarDataSet dataset_comp1 = new RadarDataSet(entries, "song");

        dataset_comp1.setColor(Color.BLUE);
        dataset_comp1.setDrawFilled(true);

        Log.e("SONGTest","그래프 dataset_comp1:"+dataset_comp1);

        ArrayList<RadarDataSet> dataSets = new ArrayList<RadarDataSet>();
        dataSets.add(dataset_comp1);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("어휘력");
        labels.add("발음");
        labels.add("계속성");
        labels.add("속도");
        labels.add("논리력");

        Log.e("SONGTest","그래프 dataSets:"+dataSets);

        RadarData data = new RadarData(labels, dataSets);
        chart.setData(data);
        String description = "데일리 테스트 1일차";
        chart.setDescription(description);
        //chart.setWebLineWidthInner(0.5f);

        chart.invalidate();
        chart.animate();
        pd.hide();

        btn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

    }


}
