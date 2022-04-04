package com.naver.naverspeech.client;

/**
 * 다영 계속성
 */
public class Continuity {

    private String record;
    public Continuity(String record){
        this.record=record;
    }

    public int getScore(){
        int score=0;
        int blank=0;
        int word=0;
        for(int i=0;i<record.length();i++){
            if(String.valueOf(record.charAt(i)).equals(" ")){
                blank++;
            }
        }

        word=blank+1;
        if(word<=65&&word>=60){ //60~65
            return 10;
        }else if((word<60&&word>=55)||(word>65&&word<=70)){ //55~60
            return 9;
        }else if((word>=50&&word<55)||(word>70&&word<=75)){ //50~55
            return 8;
        }else if((word<50&&word>=45)||(word>75&&word<=80)){ //45~50
            return 7;
        }else if((word>=40&&word<45)||(word>80&&word<=85)){ //40~45
            return 6;
        }else if((word>=35&&word<40)||(word>85&&word<=90)) { //35~40
            return 5;
        }else if((word>=30&&word<35)||word>90) { //30~35
            return 4;
        }else if(word>=25&&word<30) { //25~30
            return 3;
        }else if(word>=20&&word<25) { //20~25
            return 2;
        }else if(word>=1&&word<20) { //1~20
            return 1;
        }else if(word==0){ //0
            return 0;
        }
        return 0;
    }
}
