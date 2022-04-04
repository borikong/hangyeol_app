package com.naver.naverspeech.client.soojeong;

import android.widget.Toast;

/**
 * Created by DS on 2018-10-09.
 */

public class Vocabulary {
    int score=8;
    public Vocabulary(String str, String vocabularyQuestion ) {
        String[] array = vocabularyQuestion.split(",");

        str = str.replaceAll(" " , "");
        str = str.replaceAll("\\p{Z}", "");
        int cnt=0;
        for (int i=0; i<array.length;i++) {

        }




    }

    public int getScore() {
        return score;
    }
}