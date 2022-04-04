package com.naver.naverspeech.client.seohyun;

/**
 * Created by DS on 2018-09-21.
 */

public class Speed {

    int n;
    int num;
    int level;

    String record;

    public Speed(String record){
        this.record=record;
    }


    public int getScore() {
                /*1. 음절 갯수를 구한다*/

        String[] tmp = record.split(" "); //공백을 기준으로 단어를 나누는 메소드

        for (String s : tmp) {
            n++; //단어 수 만큼 n의 값 증가
            num = record.length() - (n - 1);  /*단어가 n개 일 때, 공백 부분은 n-1개*/
        }
                /*2. 구한 음절 갯수를 녹음 시간으로 나눈다*/

        int result = CalculateResult(num);
        n = 0;
        num = record.length(); //원래대로 초기화.


        return result;
    }


    public int CalculateResult(int nums){

        System.out.println(num+"단어의 개수 입니다.");

    /*평가 그래프*/
        if(nums>=260 && nums<=270){ //1분당 260~270여자 기준
            level=10;
        }else if((nums>=260 && nums<265) || (nums>270 && nums<=275)){ //5개
            level=9;
        }else if((nums>=255 && nums<260) || (nums>275 && nums<280)){ //5개
            level=8;
        }else if((nums>=250 && nums<255) || (nums>280 && nums<285)){ //5개
            level=7;
        }else if((nums>=245 && nums<250) || (nums>285 && nums<290)){ //5개
            level=6;
        }else if((nums>=240 && nums<245) || (nums>290 && nums<295)){ //5개
            level=5;
        }else if((nums>=235 && nums<240) || (nums>295 && nums<300)){ //5개
            level=4;
        }else if((nums>=230 && nums<235) || (nums>305 && nums<310)){ //5개
            level=3;
        }else if((nums>=225 && nums<230) || (nums>310 && nums<315)){ //5개
            level=2;
        }else{
            level=1;
        }
        return level;
    }

}
