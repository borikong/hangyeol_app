package com.naver.naverspeech.client.seohyun;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.naver.naverspeech.client.R;

public class Record_score extends AppCompatActivity {
    static boolean calledAlready = false;
    private Button vocaBtn, proBtn, conBtn, speedBtn, logicBtn;
    private LinearLayout layout, layout22, layout3, layout4;
    private Context context;
    private Intent intent;
    private String voca, pro, contin, speed, logic;
    private StorageReference mStorageRef;
    private dailyTestQuestionItem item;
    private dailyTestScoreItem scoreItem;
    private int num;
    private String questionVoca,questionSpeed,questionContin,questionPro,questionLogic;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_score);

        if (!calledAlready)
        {
           // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }

       mStorageRef = FirebaseStorage.getInstance().getReference();
       layout= (LinearLayout) findViewById(R.id.layout);
       layout22=(LinearLayout) findViewById(R.id.layout22);
       layout3=(LinearLayout) findViewById(R.id.layout3);
       layout4=(LinearLayout) findViewById(R.id.layout4);
       context=this;

       tv1=(TextView) findViewById(R.id.tv1);

       intent = getIntent();
       voca= intent.getStringExtra("voca");
       pro = intent.getStringExtra("pronounciation");
       contin=intent.getStringExtra("continu");
       speed=intent.getStringExtra("speed");
       logic=intent.getStringExtra("logic");

      if(voca != null){
          vocaBtn= new Button(context);
          vocaBtn.setText("  어휘력 "+ voca +"점 맞은 문제 확인하기 ▷  ");
          vocaBtn.setTextSize(25);

          vocaBtn.setBackgroundResource(R.drawable.border);
          layout.addView(vocaBtn);
          vocaBtn.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  getDailyTestScore(Integer.parseInt(voca), "vocabulary");
                  getDailyTestQuestion("vocabulary");
                  tv1.setText(questionVoca);
                  tv1.setTextSize(25);
             }
          });
      }

      if (pro != null){
          proBtn= new Button(context);
          proBtn=new Button(context);
          proBtn.setText("   발음 "+ pro +"점 맞은 문제 확인하기 ▷  ");
          proBtn.setTextSize(25);
          proBtn.setBackgroundResource(R.drawable.border);
          layout.addView(proBtn);
          proBtn.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  getDailyTestScore(Integer.parseInt(pro), "pronunciation");
                  getDailyTestQuestion("pronunciation");
                  tv1.setText(questionPro);
                  tv1.setTextSize(25);
              }
          });
        }
        if(contin != null){
          conBtn= new Button(context);
          conBtn.setText("  계속성 "+ contin +"점 맞은 문제 확인하기 ▷  ");
          conBtn.setBackgroundResource(R.drawable.border);
          conBtn.setTextSize(25);
          layout.addView(conBtn);

            conBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    getDailyTestScore(Integer.parseInt(contin), "continuity");
                    getDailyTestQuestion("continuity");
                    tv1.setText(questionContin);
                    tv1.setTextSize(25);
                }
            });
        }
        if(speed !=null){
            speedBtn=new Button(context);
            speedBtn.setText("   속도 "+ speed +"점 맞은 문제 확인하기 ▷  ");
            speedBtn.setBackgroundResource(R.drawable.border);
            speedBtn.setTextSize(25);
            layout.addView(speedBtn);

            speedBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    getDailyTestScore(Integer.parseInt(speed), "speed");
                    getDailyTestQuestion("speed");
                    tv1.setText(questionSpeed);
                    tv1.setTextSize(25);
                }
            });

        }
        if(logic!=null){
            logicBtn= new Button(context);
            logicBtn.setText("  논리력 "+ logic +"점 맞은 문제 확인하기 ▷  ");
            logicBtn.setBackgroundResource(R.drawable.border);
            logicBtn.setTextSize(25);
            layout.addView(logicBtn);

            logicBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    getDailyTestScore(Integer.parseInt(logic), "logic");
                    getDailyTestQuestion("logic");
                    tv1.setText(questionLogic);
                    tv1.setTextSize(25);
                }
            });
        }

    }

    public void getDailyTestScore(final int dailyTextScore, final String area) {

        //dailyTestQuestion 이라는 자식의 데이터 몽땅 가져온다.
        DatabaseReference databaseReferenceScore = FirebaseDatabase.getInstance().getReference("dailyTestScore");
        databaseReferenceScore.getDatabase();

        // databaseReference에 자식(데이터)가 추가되면 발행하는 이벤트이다.
        databaseReferenceScore.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                scoreItem = dataSnapshot.getValue(dailyTestScoreItem.class);

                if(area.equals("vocabulary") && dailyTextScore==scoreItem.getVocabulary()){
                    num=scoreItem.getNum();
                    Log.d(scoreItem.getVocabulary()+"의 값", scoreItem.getVocabulary()+"의 값");
                    Log.d(num+"의 값2", num+"의 값2");
                } else if(area.equals("pronunciation") && dailyTextScore==scoreItem.getPronunciation()){
                    num=scoreItem.getPronunciation();
                } else if(area.equals("continuity") && dailyTextScore==scoreItem.getContinuity()){
                    num=scoreItem.getNum();
                } else if(area.equals("speed") && dailyTextScore==scoreItem.getSpeed()){
                    num=scoreItem.getNum();
                } else if(area.equals("logic") && dailyTextScore==scoreItem.getLogic()){
                    num=scoreItem.getNum();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getDailyTestQuestion(final String area2) {

        //dailyTestQuestion 이라는 자식의 데이터 몽땅 가져온다.
        DatabaseReference databaseReferenceDailyTest = FirebaseDatabase.getInstance().getReference("dailyTestQuestion");
        databaseReferenceDailyTest.getDatabase();

        // databaseReference에 자식(데이터)가 추가되면 발행하는 이벤트이다.
        databaseReferenceDailyTest.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                item = dataSnapshot.getValue(dailyTestQuestionItem.class);

                if(num == item.getNum()){
                    questionVoca=item.getVocabulary();
                    questionSpeed=item.getSpeed();
                    questionContin=item.getContinuity();
                    questionPro=item.getPronunciation();
                    questionLogic=item.getLogic();
                }

                if(area2.equals("vocabulary")){
                    tv1.setText(questionVoca);
                } else if(area2.equals("pronunciation")){
                    tv1.setText(questionPro);
                } else if(area2.equals("continuity")){
                    tv1.setText(questionContin);
                } else if(area2.equals("speed")){
                    tv1.setText(questionSpeed);
                } else {
                    tv1.setText(questionLogic);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



}
