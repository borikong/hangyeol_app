package com.naver.naverspeech.client.dayeong;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.naver.naverspeech.client.R;

/**
 * 리스트뷰의 항목을 누르면 상세 글로 넘어가는 액티비티
 */

public class Board_MboardDetailActivity extends AppCompatActivity {

    private TextView title, content;
    private DatabaseReference databaseReference;
    private Button modifybtn,deletebtn,commentbtn;
    private ImageButton cancelbtn;
    private String key;
    private ListView commentlist;
    private Board_MboardCommentListViewAdapter adapter;
    private EditText commentData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mboard_detail);
        //디비처리를 위해 키값(날짜)를 리스트뷰 OnItemClickListener를 통해 인텐트로 받음
        key=getIntent().getStringExtra("date");

        getData();
        getCommentData();
        setLayout();

    }

    private  void setLayout(){
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        modifybtn=(Button)findViewById(R.id.modifybtn);
        deletebtn=(Button)findViewById(R.id.deletebtn);
        cancelbtn=(ImageButton) findViewById(R.id.btn_cancel);
        commentbtn=(Button)findViewById(R.id.commentbtn);
        commentData=(EditText)findViewById(R.id.commentdata);

        modifybtn.setText("수정");
        deletebtn.setText("삭제");
        commentbtn.setText("등록");

        //수정하기 버튼 클릭
        modifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Board_MboardDetailActivity.this,Board_ModifyInterviewActivity.class);
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
                Intent intent=new Intent(Board_MboardDetailActivity.this,BoardActivity.class);
                startActivity(intent);
            }
        });

        //취소 버튼 리스너
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Board_MboardDetailActivity.this,BoardActivity.class);
                startActivity(intent);
            }
        });

        //댓글등록버튼 리스너
        commentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment(commentData.getText().toString(),"commentTestId");
                commentData.setText("");
            }
        });

        adapter=new Board_MboardCommentListViewAdapter();
        commentlist=(ListView)findViewById(R.id.commentlist);
        commentlist.setAdapter(adapter);

    }

    private void getData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("board_mboard").child(key);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String str=dataSnapshot.getValue().toString();

                if(dataSnapshot.getKey().equals("content")){
                    content.setText(str);
                }else if(dataSnapshot.getKey().equals("title")) {
                    title.setText(str);
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
        databaseReference = FirebaseDatabase.getInstance().getReference("board_mboard").child(key);
        databaseReference.removeValue();
    }

    //댓글 데이터 가져오기
    private void getCommentData(){
        //mboard의 자식데이터
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("board_mboard").child(key).child("comment");
        databaseReference.getDatabase();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Mboard_CommentDataItem item = dataSnapshot.getValue(Mboard_CommentDataItem.class);

                //해당 값을 adapter에 추가한다.
                adapter.addItem(item.getContent(),item.getId(),item.getDate());
                adapter.notifyDataSetChanged();

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

    private void addComment(String comment,String id){
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
        Date currentTime = new Date();
        String date = mSimpleDateFormat.format ( currentTime );

        //자유게시판 댓글 객체 생성
        Mboard_CommentDataItem item = new Mboard_CommentDataItem(comment,"commenttestid",date);

        databaseReference.child("comment").child(date).setValue(item);
    }

}
