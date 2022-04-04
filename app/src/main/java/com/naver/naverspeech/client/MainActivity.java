package com.naver.naverspeech.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.naver.naverspeech.client.seohyun.MainActivity2;


public class MainActivity extends AppCompatActivity {
    private Button btn, btn2;
    SQLiteHelper1 dbHelper1;

    String userName = "SONG";
    String trainingType = "vocabulary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper1 = new SQLiteHelper1(getApplicationContext(), "Score.db", null, 1);

                Log.e("SONGTest","이게더ㅚ니?");

                // 로그인 만들면 없어질것!
                dbHelper1.insert("SONG");


                dbHelper1.update(userName, 1);
                startActivity(new Intent(MainActivity.this,DailyTestActivity.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
    }
}
