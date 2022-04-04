package com.naver.naverspeech.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.soojeong.MainActivity3;
import com.naver.naverspeech.client.soojeong.User;

public class LoginActivity extends AppCompatActivity {


    EditText idEtx, pwdEtx;
    Button loginBtn, joinBtn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (FirebaseDatabase.getInstance() == null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }


        idEtx = (EditText)findViewById(R.id.idEtx);
        pwdEtx = (EditText)findViewById(R.id.pwdEtx);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(idEtx.getText().toString(), pwdEtx.getText().toString());
            }
        });
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), JoinActivity.class));
            }
        });
    }

    void login(final String id, final String pwd) {
        if(id.length()!=0 && pwd.length()!=0) {
            //  Toast.makeText(getApplicationContext(), id+","+pwd,Toast.LENGTH_LONG).show();

            databaseReference = FirebaseDatabase.getInstance().getReference("user");
            databaseReference.getDatabase();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    User user=dataSnapshot.getValue(User.class);
                    if(user.getId().equals(id) && user.getPwd().equals(pwd))
                    {
                        Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                        intent.putExtra("id", id);
                        intent.putExtra("pwd", pwd);
                        intent.putExtra("name",user.getName());
                        intent.putExtra("favorite",user.getFavorite());
                        intent.putExtra("day", user.getDay()+"");
                        startActivity(intent);
                        finish();
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
        else {
            Toast.makeText(getApplicationContext(),"아이디와 비밀번호를 확인해주세요.",Toast.LENGTH_LONG).show();
        }
    }
}
