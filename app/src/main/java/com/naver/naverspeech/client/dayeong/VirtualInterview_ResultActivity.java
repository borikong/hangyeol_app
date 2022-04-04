package com.naver.naverspeech.client.dayeong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.naver.naverspeech.client.soojeong.MainActivity3;
import com.naver.naverspeech.client.R;
import com.naver.naverspeech.client.soojeong.MainActivity3;


/**
 * Created by DS on 2018-09-02.
 */

public class VirtualInterview_ResultActivity extends AppCompatActivity {

    int[] early,mid,late;
    int[] happiness,neutral,surprise,anger,contempt,disgust,fear,sadness;
    private final int EMOTION=8; //감정 개수
    private static final int WIDTH=750; //핸드폰 화면 가로

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(VirtualInterview_ResultActivity.this, MainActivity3.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualinterview_result);
        TextView scoretxt=(TextView)findViewById(R.id.score);
        TextView early_firsttxt=(TextView)findViewById(R.id.early_first);
        TextView early_secondtxt=(TextView)findViewById(R.id.early_second);
        TextView mid_firsttxt=(TextView)findViewById(R.id.mid_first);
        TextView mid_secondtxt=(TextView)findViewById(R.id.mid_second);
        TextView late_firsttxt=(TextView)findViewById(R.id.late_first);
        TextView late_secondtxt=(TextView)findViewById(R.id.late_second);
        TextView total_firsttxt=(TextView)findViewById(R.id.total_first);
        TextView total_secondtxt=(TextView)findViewById(R.id.total_second);
        TextView total_thirdtxt=(TextView)findViewById(R.id.total_third);
        TextView total_forthtxt=(TextView)findViewById(R.id.total_forth);
        float emotionTotal=0;
        int index,index2;
        float score=0;
        int nagative_score=0;

        getRawEmotionScore();

        emotionTotal=getEmotionTotal();
        score=getScore();

        early=setArray(0);
        mid=setArray(1);
        late=setArray(2);
        Log.d("점수",early[0]+"");

        drawGraph(early_firsttxt,early_secondtxt,early);
        drawGraph(mid_firsttxt,mid_secondtxt,mid);
        drawGraph(late_firsttxt,late_secondtxt,late);
        drawAllGraph(total_firsttxt,total_secondtxt,total_thirdtxt,total_forthtxt,getTotalArray(early,mid,late));

        scoretxt.setText(Math.round(score*100)/100.00+"점");
    }

    //가상면접 점수 계산
    private float getScore(){
        int negative_score=0;
        float score=0;
        //부정적인 감정의 개수 계산
        for(int i=0;i<3;i++){
            negative_score=negative_score+anger[i]+disgust[i]+fear[i]+sadness[i]+contempt[i];
        }
        //전체 점수 계산
        score=100-(float)negative_score/getEmotionTotal()*100;
        return score;
    }

    //인식된 전체 감정의 개수 계산
    private int getEmotionTotal(){
        int emotionTotal=0;
        for(int i=0;i<3;i++){ //여기 나중에2를  3으로 바꿔야함
            emotionTotal=emotionTotal+happiness[i]+neutral[i]+surprise[i]+anger[i]+contempt[i]+disgust[i]+fear[i]+sadness[i];
        }

        return emotionTotal;
    }

    private int sumOfSubEmotion(int[] array){
        int sum=0;
        for(int i=0;i<EMOTION;i++){
            sum=sum+array[i];
        }
        return sum;
    }

    //시기별 그래프를 그리는 함수
    private void drawGraph(TextView firsttv,TextView secondtv,int[] emotionscore){

        int firstEmotion=getFirst(emotionscore);
        int secondEmotion=getSecond(emotionscore,getFirst(emotionscore));
        int sum=sumOfSubEmotion(emotionscore);

        float firstTvPercentage=(float)emotionscore[firstEmotion]/(float)sum*100; //부분 감정 비율 계산 : 첫번째로 많이 감지된 감정의 개수/(첫번째 감정의 개수+두 번째 감정의 개수)*100
        float secondTvPercentage=(float)emotionscore[secondEmotion]/(float)sum*100;

        //감정점수가 일정 점수(5%) 미만이면 그래프에 나타내지 않기
       if(secondTvPercentage<=5){
            emotionscore[secondEmotion]=0;
        }

        if(emotionscore[firstEmotion]==0){
            firsttv.setBackgroundColor(Color.WHITE);
            firsttv.setText("인식된 감정이 없습니다.");
        }else if(emotionscore[secondEmotion]==0){ //감정이 하나만 감지되었을 때
            firsttv.setBackgroundColor(getEmotionColor(firstEmotion));
            firsttv.setWidth(WIDTH);
            firsttv.setText(emotionToString(firstEmotion)+"\n("+(int)firstTvPercentage+"%)");
            secondtv.setVisibility(View.GONE);
        }else{
            firsttv.setBackgroundColor(getEmotionColor(firstEmotion));
            secondtv.setBackgroundColor(getEmotionColor(secondEmotion));
            firsttv.setWidth((int)(firstTvPercentage/(firstTvPercentage+secondTvPercentage)*WIDTH));
            secondtv.setWidth((int)(secondTvPercentage/(firstTvPercentage+secondTvPercentage)*WIDTH));
            firsttv.setText(emotionToString(firstEmotion)+"\n("+(int)firstTvPercentage+"%)");
            secondtv.setText(emotionToString(secondEmotion)+"\n("+(int)secondTvPercentage+"%)");
        }
    }

    //전체 감정분석 그래프 그리기
    private void drawAllGraph(TextView firsttv,TextView secondtv, TextView thirdtv, TextView forthtv, int[] emotionscore){
        int firstEmotion =getFirst(emotionscore);
        int secondEmotion=getSecond(emotionscore,firstEmotion);
        int thirdEmotion=getThird(emotionscore,firstEmotion,secondEmotion);
        int forthEmotion=getForth(emotionscore,firstEmotion,secondEmotion,thirdEmotion);
        int emotionTotal=getEmotionTotal();

        float firstTvPercentage=(float)emotionscore[firstEmotion]/(float)emotionTotal*100;
        float secondTvPercentage=(float)emotionscore[secondEmotion]/(float)emotionTotal*100;
        float thirdTvPercentage=(float)emotionscore[thirdEmotion]/(float)emotionTotal*100;
        float forthTvPercentage=(float)emotionscore[forthEmotion]/(float)emotionTotal*100;

        //감정점수가 일정 점수(5%) 미만이면 그래프에 나타내지 않기
        if(secondTvPercentage<5){
            emotionscore[secondEmotion]=0;
            emotionscore[thirdEmotion]=0;
            emotionscore[forthEmotion]=0;
        }else if(thirdTvPercentage<5){
            emotionscore[thirdEmotion]=0;
            emotionscore[forthEmotion]=0;
        }else if(forthTvPercentage<5){
            emotionscore[forthEmotion]=0;
        }

        if(emotionscore[firstEmotion]==0){
            firsttv.setBackgroundColor(Color.WHITE);
            firsttv.setText("인식된 감정이 없습니다.");
        }else if(emotionscore[secondEmotion]==0){
            firsttv.setBackgroundColor(getEmotionColor(firstEmotion));
            firsttv.setWidth(WIDTH);
            firsttv.setText(emotionToString(firstEmotion)+"\n("+(int)firstTvPercentage+"%)");
            secondtv.setVisibility(View.GONE);
        }else if(emotionscore[thirdEmotion]==0){
                firsttv.setBackgroundColor(getEmotionColor(firstEmotion));
                secondtv.setBackgroundColor(getEmotionColor(secondEmotion));
                firsttv.setWidth((int) (firstTvPercentage / (firstTvPercentage + secondTvPercentage) * WIDTH));
                secondtv.setWidth((int) (secondTvPercentage / (firstTvPercentage + secondTvPercentage) * WIDTH));
                firsttv.setText(emotionToString(firstEmotion) + "\n(" + (int) firstTvPercentage + "%)");
                secondtv.setText(emotionToString(secondEmotion) + "\n(" + (int) secondTvPercentage + "%)");
                firsttv.setBackgroundColor(getEmotionColor(firstEmotion));
                firsttv.setWidth(WIDTH);
                firsttv.setText(emotionToString(firstEmotion)+"\n("+(int)firstTvPercentage+"%)");
        }else if(emotionscore[forthEmotion]==0){
            firsttv.setBackgroundColor(getEmotionColor(firstEmotion));
            secondtv.setBackgroundColor(getEmotionColor(secondEmotion));
            thirdtv.setBackgroundColor(getEmotionColor(thirdEmotion));
            firsttv.setWidth((int)(firstTvPercentage/(firstTvPercentage+secondTvPercentage+thirdTvPercentage)*WIDTH));
            secondtv.setWidth((int)(secondTvPercentage/(firstTvPercentage+secondTvPercentage+thirdTvPercentage)*WIDTH));
            thirdtv.setWidth((int)(thirdTvPercentage/(firstTvPercentage+secondTvPercentage+thirdTvPercentage)*WIDTH));
            firsttv.setText(emotionToString(firstEmotion)+"\n("+(int)firstTvPercentage+"%)");
            secondtv.setText(emotionToString(secondEmotion)+"\n("+(int)secondTvPercentage+"%)");
            thirdtv.setText(emotionToString(thirdEmotion)+"\n("+(int)thirdTvPercentage+"%)");
        }else{
            firsttv.setBackgroundColor(getEmotionColor(firstEmotion));
            secondtv.setBackgroundColor(getEmotionColor(secondEmotion));
            thirdtv.setBackgroundColor(getEmotionColor(thirdEmotion));
            forthtv.setBackgroundColor(getEmotionColor(forthEmotion));
            firsttv.setWidth((int)(firstTvPercentage/(firstTvPercentage+secondTvPercentage+thirdTvPercentage+forthTvPercentage)*WIDTH));
            secondtv.setWidth((int)(secondTvPercentage/(firstTvPercentage+secondTvPercentage+thirdTvPercentage+forthTvPercentage)*WIDTH));
            thirdtv.setWidth((int)(thirdTvPercentage/(firstTvPercentage+secondTvPercentage+thirdTvPercentage+forthTvPercentage)*WIDTH));
            forthtv.setWidth((int)(forthTvPercentage/(firstTvPercentage+secondTvPercentage+thirdTvPercentage+forthTvPercentage)*WIDTH));
            firsttv.setText(emotionToString(firstEmotion)+"\n("+(int)firstTvPercentage+"%)");
            secondtv.setText(emotionToString(secondEmotion)+"\n("+(int)secondTvPercentage+"%)");
            thirdtv.setText(emotionToString(thirdEmotion)+"\n("+(int)thirdTvPercentage+"%)");
            forthtv.setText(emotionToString(forthEmotion)+"\n("+(int)forthTvPercentage+"%)");
        }
    }

    //첫 번째로 큰 비율을 차지하는 감정의 인덱스 구하기
    private int getFirst(int[] emotionscore){
        int first=0;
        int firstindex=0;
        for(int i=0;i<EMOTION;i++){
            if(emotionscore[i]>=first){
                first=emotionscore[i];
                firstindex=i;
            }
        }
        return firstindex;
    }

    //두 번째로 큰 비율을 차지하는 감정의 인덱스 구하기
    private int getSecond(int[] emotionscore,int firstEmotionNum){
        int second=0;
        int secondindex=0;
        for(int i=0;i<EMOTION;i++){
            if(i!=firstEmotionNum){
                if(emotionscore[i]>=second){
                    second=emotionscore[i];
                    secondindex=i;
                }
            }
        }
        return secondindex;
    }

    //세 번째로 큰 비율을 차지하는 감정의 인덱스 구하기
    private int getThird(int[] emotionscore,int firstEmotionNum, int secondEmotionNum){
        int third=0;
        int thirdindex=0;
        for(int i=0;i<EMOTION;i++){
            if(i!=firstEmotionNum&&i!=secondEmotionNum){
                if(emotionscore[i]>=third){
                    third=emotionscore[i];
                    thirdindex=i;
                }
            }
        }
        return thirdindex;
    }

    //네 번째로 큰 비율을 차지하는 감정의 인덱스 구하기
    private int getForth(int[] emotionscore,int firstEmotionNum, int secondEmotionNum, int forthEmotionNum){
        int forth=0;
        int forthindex=0;
        for(int i=0;i<EMOTION;i++){
            if(i!=firstEmotionNum&&i!=secondEmotionNum&&i!=forthEmotionNum){
                if(emotionscore[i]>=forth){
                    forth=emotionscore[i];
                    forthindex=i;
                }
            }
        }
        return forthindex;
    }

    //감정 index를 해당하는 감정 String으로 바꿈
    private String emotionToString(int emotionNum){
        if(emotionNum==0){
            return "행복";
        }else if(emotionNum==1){
            return "중립";
        }else if(emotionNum==2){
            return "놀람";
        }else if(emotionNum==3){
            return "화남";
        }else if(emotionNum==4){
            return "경멸";
        }else if(emotionNum==5){
            return "혐오";
        }else if(emotionNum==6){
            return "공포";
        }else if(emotionNum==7){
            return  "슬픔";
        }
        return null;
    }

    //각 감정의 고유한 색을 INT로 반환
    private int getEmotionColor(int emotionNum){
        if(emotionNum==0){
            return -15818591;
        }else if(emotionNum==1){
            return -16711936;
        }else if(emotionNum==2){
            return -4704318;
        }else if(emotionNum==3){
            return -10921638;
        }else if(emotionNum==4){
            return -65536;
        }else if(emotionNum==5){
            return -13398589;
        }else if(emotionNum==6){
            return -15950246;
        }else if(emotionNum==7){
            return  -4044748;
        }
        return 0;

    }

    //시기별로 감정점수를 배열에 넣음
    private int[] setArray(int era){
        int[] array=new int[EMOTION];
        array[0]=happiness[era];
        array[1]=neutral[era];
        array[2]=surprise[era];
        array[3]=anger[era];
        array[4]=contempt[era];
        array[5]=disgust[era];
        array[6]=fear[era];
        array[7]=sadness[era];
        return array;
    }

    private int[] getTotalArray(int[] earlyArray, int[] midArray, int[] lateArray){
        int[] totalArray=new int[EMOTION];
        for(int i=0;i<EMOTION;i++){
            totalArray[i]=earlyArray[i]+midArray[i]+lateArray[i];
        }
        return totalArray;
    }

    //test용 중반까지만
    private int[] getTotalArray(int[] earlyArray, int[] midArray){
        int[] totalArray=new int[EMOTION];
        for(int i=0;i<EMOTION;i++){
            totalArray[i]=earlyArray[i]+midArray[i];
        }
        return totalArray;
    }

    //배열로 감정 점수 받아옴
    private void getRawEmotionScore(){
        happiness=getIntent().getIntArrayExtra("happiness");
        neutral=getIntent().getIntArrayExtra("neutral");
        surprise=getIntent().getIntArrayExtra("surprise");
        anger=getIntent().getIntArrayExtra("anger");
        contempt=getIntent().getIntArrayExtra("contempt");
        disgust=getIntent().getIntArrayExtra("disgust");
        fear=getIntent().getIntArrayExtra("fear");
        sadness=getIntent().getIntArrayExtra("sadness");
    }

}