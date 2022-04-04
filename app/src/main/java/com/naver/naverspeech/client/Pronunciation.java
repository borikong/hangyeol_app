package com.naver.naverspeech.client;

import android.util.Log;

import java.util.Random;

public class Pronunciation {

    int num;
    int score=0;

    String record, answer, pronunciationQuestion;

    public Pronunciation(String pronunciationQuestion,String record){
        this.pronunciationQuestion = pronunciationQuestion;
        this.record=record;
    }

    public int getScore() {
        answer = pronunciationQuestion;

        String[] tmp = answer.split(" ");

        Log.e("SONGTest","결과문장은? : "+ record);

        for(int n = 0; n<25; n++){
            num = new Random().nextInt(tmp.length);

            Log.e("SONGTest","프로넌시에이션 " + (n+1)+"번째 : "+ tmp[num]);

            if(record.contains((tmp[num]))){
                score = score + 5;
                Log.e("SONGTest","현재점수는? : "+ score);
            }
        }
        Log.e("SONGTest","결과점수는? : "+ score);
        return (score/10);
    }

}