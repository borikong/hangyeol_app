package com.naver.naverspeech.client.dayeong;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.naver.naverspeech.client.R;

public class Board_AddInterviewActivity extends AppCompatActivity {
    private EditText title, company, specific_type, content;
    private String type;
    private Button okBtn;
    private ImageButton cancelBtn;
    private DatabaseReference databaseReference;
    private  Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interview);

        //board_interview 데이터 인스턴스 생성
        databaseReference = FirebaseDatabase.getInstance().getReference("board_interview");

        setSpinner();
        setButton();

    }

    private void setButton(){
        title= (EditText) findViewById(R.id.title);
        company= (EditText) findViewById(R.id.company);
        specific_type=(EditText)findViewById(R.id.specific_type);
        content=(EditText)findViewById(R.id.content);
        okBtn = (Button) findViewById(R.id.ok_Btn);

        //글쓰기 버튼 리스너
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titletxt=title.getText().toString();
                String companytxt=company.getText().toString();
                String specific_typetxt=specific_type.getText().toString();
                String contenttxt=content.getText().toString();
                type=spinner.getSelectedItem().toString();

                //writeNewUser() 함수를 통해 새로운 글을 추가한다.
                writeNewUser("boribori", titletxt,companytxt, type, specific_typetxt,contenttxt);

                finish();

            }
        });

        cancelBtn=(ImageButton) findViewById(R.id.btn_cancel);

        //취소 버튼 리스너
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Board_AddInterviewActivity.this,BoardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void writeNewUser(String id, String title, String company, String type, String specific_type, String content) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
        Date currentTime = new Date();
        String date = mSimpleDateFormat.format ( currentTime );

        //면접정보게시판 객체 생성
        InterviewDataItem item = new InterviewDataItem(title,company,type,specific_type,content,id,date);

        //date 값을 상위 자식으로 설정(기본키 비슷하한 역할)하고 만들어 둔 item을 추가한다.
        databaseReference.child(date).setValue(item);
    }


    private void setSpinner(){
        spinner = (Spinner)findViewById(R.id.category_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
