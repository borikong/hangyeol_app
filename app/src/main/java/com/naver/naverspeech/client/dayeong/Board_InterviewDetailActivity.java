package com.naver.naverspeech.client.dayeong;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.R;


/**
 * 리스트뷰의 항목을 누르면 상세 글로 넘어가는 액티비티
 */

public class Board_InterviewDetailActivity extends AppCompatActivity {

    private TextView title, content, company, specific_type,category;
    private InterviewDataItem item;
    private DatabaseReference databaseReference;
    private Button modifybtn,deletebtn;
    private ImageButton cancelbtn;
    private String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_detail);
        //디비처리를 위해 키값(날짜)를 리스트뷰 OnItemClickListener를 통해 인텐트로 받음
        key=getIntent().getStringExtra("date");

        getData();
        setLayout();

    }

    private  void setLayout(){
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        company = (TextView) findViewById(R.id.company);
        specific_type = (TextView) findViewById(R.id.specific_type);
        modifybtn=(Button)findViewById(R.id.modifybtn);
        deletebtn=(Button)findViewById(R.id.deletebtn);
        category=(TextView)findViewById(R.id.category);
        cancelbtn=(ImageButton) findViewById(R.id.btn_cancel);
        modifybtn.setText("수정");
        deletebtn.setText("삭제");

        //수정하기 버튼 클릭
        modifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Board_InterviewDetailActivity.this,Board_ModifyInterviewActivity.class);
                intent.putExtra("date",key);
                startActivity(intent);
            }
        });

        //삭제 버튼 클릭
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                //삭제 완료후 메뉴로 넘어가기 삭제완료 토스트 띄우기
                Toast.makeText(getApplicationContext(),"삭제완료",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Board_InterviewDetailActivity.this,BoardActivity.class);
                startActivity(intent);
            }
        });

        //취소 버튼 리스너
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Board_InterviewDetailActivity.this,BoardActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("board_interview").child(key);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String str=dataSnapshot.getValue().toString();

                if(dataSnapshot.getKey().equals("company")){
                    company.setText(str);
                }else if(dataSnapshot.getKey().equals("content")){
                    content.setText(str);
                }else if(dataSnapshot.getKey().equals("specific_type")){
                    specific_type.setText(str);
                }else if(dataSnapshot.getKey().equals("title")){
                    title.setText(str);
                }else if(dataSnapshot.getKey().equals("type")){
                    category.setText(str);
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

    private void deleteData(){
        databaseReference = FirebaseDatabase.getInstance().getReference("board_interview").child(key);
        databaseReference.removeValue();
    }

}
