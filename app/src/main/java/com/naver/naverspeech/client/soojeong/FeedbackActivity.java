package com.naver.naverspeech.client.soojeong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.R;

public class FeedbackActivity extends AppCompatActivity {

    private ListView listview;
    private ListViewAdapter adapter;
    private Button writeBtn;
    static boolean calledAlready = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //onCreate 시작할 때 바로 실행되도록 먼저 불러준다.
        if (!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready = true;
        }

        adapter=new ListViewAdapter();

        //getFeedbackData() 함수를 통해 adapter에 item 붙여줄 것이다.
        getFeedbackData();

        listview=(ListView)findViewById(R.id.feedbackList);
        listview.setAdapter(adapter);

        writeBtn = (Button) findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getFeedbackData() {

        //feeback 이라는 자식의 데이터 몽땅 가져온다.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feedback");
        databaseReference.getDatabase();


        // databaseReference에 자식(데이터)가 추가되면 발행하는 이벤트이다.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //데이터에 추가된 FeedbackItem을 가져온다.
                FeedbackItem item = dataSnapshot.getValue(FeedbackItem.class);

                //해당 값을 adapter에 추가한다.
                adapter.addItem(getResources().getDrawable(R.drawable.han_character),item.getTitle(),item.getContent());

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


