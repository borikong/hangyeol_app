package com.naver.naverspeech.client.soojeong;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class AddInterviewActivity extends AppCompatActivity {
    private EditText titleTxt;
    private EditText contentTxt;
    private Button okBtn;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interview);

        //interview 이라는 자식의 데이터 몽땅 가져온다.
        databaseReference = FirebaseDatabase.getInstance().getReference("interview");

        titleTxt = (EditText) findViewById(R.id.title);
        contentTxt = (EditText) findViewById(R.id.content);
        okBtn = (Button) findViewById(R.id.ok_Btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleTxt.getText().toString();
                String content = contentTxt.getText().toString();

                //writeNewUser() 함수를 통해 새로운 글을 추가한다.
                writeNewUser("testId", content, title);

                finish();

            }
        });
    }

    private void writeNewUser(String userId, String content, String title) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
        Date currentTime = new Date();
        String date = mSimpleDateFormat.format ( currentTime );

        //추가하길 원하는 item class를 만든다.
        InterviewDataItem item = new InterviewDataItem(title,content);

        //date 값을 상위 자식으로 설정(기본키 비슷하한 역할)하고 만들어 둔 item을 추가한다.
        databaseReference.child(date).setValue(item);
    }
}
