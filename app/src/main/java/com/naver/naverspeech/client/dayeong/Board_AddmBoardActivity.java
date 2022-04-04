package com.naver.naverspeech.client.dayeong;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.naver.naverspeech.client.R;


/**
 * 게시판 액티비티(다영)
 */

public class Board_AddmBoardActivity extends AppCompatActivity {
    private EditText titleTxt;
    private EditText contentTxt;
    private Button okBtn;
    private ImageButton cancelBtn;


    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mboard);

        //mboard 데이터 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference("board_mboard");

        setButton();
    }

    private void setButton(){
        titleTxt = (EditText) findViewById(R.id.title);
        contentTxt = (EditText) findViewById(R.id.content);
        okBtn = (Button) findViewById(R.id.ok_Btn);

        //글쓰기 버튼 리스너-
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleTxt.getText().toString();
                String content = contentTxt.getText().toString();

                writeNewUser(title, content, "testid");

                finish();

            }
        });

        cancelBtn=(ImageButton) findViewById(R.id.btn_cancel);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Board_AddmBoardActivity.this,BoardActivity.class);
                startActivity(intent);
            }
        });
    }


    private void writeNewUser(String title,String content, String id) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
        Date currentTime = new Date();
        String date = mSimpleDateFormat.format ( currentTime );

        //자유게시판 item 객체 생성
        MboardDataItem item = new MboardDataItem(title,content,id,date);

        databaseReference.child(date).setValue(item);
    }
}
