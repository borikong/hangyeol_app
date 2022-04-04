package com.naver.naverspeech.client.dayeong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.naver.naverspeech.client.Continuity;
import com.naver.naverspeech.client.R;

public class TestActivity extends AppCompatActivity {

    TextView recordtxt;
    TextView scoretxt;
    String record;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textactivity);

        record="계속성 영역 백점 받기 하나 둘 셋 넷 다섯 여섯 일곱 여덟 아홉 열 경찰과 소방, KT, 한국전력 등 4개 기관은 이날 오전 10시 30분부터 서대문구 충정로 아현국사 화재 현장에서 1차 합동감식을 벌여 화재에 따른 피해 상황을 확인했다.";

        recordtxt=(TextView)findViewById(R.id.questiontxt);
        scoretxt=(TextView)findViewById(R.id.scoretxt);

        Continuity continuity=new Continuity(record);

        recordtxt.setText(record);
        scoretxt.setText(continuity.getScore()+"");
    }
}
