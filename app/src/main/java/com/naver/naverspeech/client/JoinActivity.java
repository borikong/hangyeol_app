package com.naver.naverspeech.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.soojeong.FeedbackItem;
import com.naver.naverspeech.client.soojeong.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class JoinActivity extends AppCompatActivity {

    EditText idEtx, pwdEtx, nameEtx;
    Button joinBtn;
    ImageButton extBtn;
    CheckBox[] ch = new CheckBox[17];

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //feeback 이라는 자식의 데이터 몽땅 가져온다.
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        idEtx = (EditText)findViewById(R.id.idEtx);
        pwdEtx = (EditText)findViewById(R.id.pwdEtx);
        nameEtx = (EditText)findViewById(R.id.nameEtx);

        joinBtn = (Button) findViewById(R.id.joinBtn);
        extBtn = (ImageButton)  findViewById(R.id.extBtn);

        ch[0]=(CheckBox) findViewById(R.id.ch0);
        ch[1]=(CheckBox) findViewById(R.id.ch1);
        ch[2]=(CheckBox) findViewById(R.id.ch2);
        ch[3]=(CheckBox) findViewById(R.id.ch3);
        ch[4]=(CheckBox) findViewById(R.id.ch4);
        ch[5]=(CheckBox) findViewById(R.id.ch5);
        ch[6]=(CheckBox) findViewById(R.id.ch6);
        ch[7]=(CheckBox) findViewById(R.id.ch7);
        ch[8]=(CheckBox) findViewById(R.id.ch8);
        ch[9]=(CheckBox) findViewById(R.id.ch9);
        ch[10]=(CheckBox) findViewById(R.id.ch10);
        ch[11]=(CheckBox) findViewById(R.id.ch11);
        ch[12]=(CheckBox) findViewById(R.id.ch12);
        ch[13]=(CheckBox) findViewById(R.id.ch13);
        ch[14]=(CheckBox) findViewById(R.id.ch14);
        ch[15]=(CheckBox) findViewById(R.id.ch15);
        ch[16]=(CheckBox) findViewById(R.id.ch16);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join(idEtx.getText().toString(), pwdEtx.getText().toString(),nameEtx.getText().toString());
            }
        });

        extBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    void join(String id, String pwd, String name) {
        String temp="";
        for(int i=0;i<ch.length;i++) {
            if(ch[i].isChecked()) {
                temp+=i+",";

            }
        }
        if(id.length()!=0 && pwd.length()!=0 && name.length()!=0) {
          //  Toast.makeText(getApplicationContext(), id+","+pwd,Toast.LENGTH_LONG).show();
           // Toast.makeText(getApplicationContext(), temp,Toast.LENGTH_LONG).show();
            joinUser(id, pwd, name, temp);

            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"정보를 입력해주세요.",Toast.LENGTH_LONG).show();
        }
    }

    private void joinUser(String id, String pwd, String name, String temp) {

        //추가하길 원하는 item class를 만든다.
        User user = new User(id,pwd,name,temp);

        //date 값을 상위 자식으로 설정(기본키 비슷하한 역할)하고 만들어 둔 item을 추가한다.
        databaseReference.child(id).setValue(user);

    }
}
