package com.naver.naverspeech.client.soojeong;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.R;

/**
 * Created by DS on 2018-08-05.
 */

public class FragmentInterview extends Fragment{

    private ListView listview;
    private ListViewAdapter adapter;

    private Button writeBtn;
    static boolean calledAlready = false;

  public FragmentInterview()
        {}

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            //onCreate 시작할 때 바로 실행되도록 먼저 불러준다.
            if (!calledAlready)
            {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
                calledAlready = true;
            }
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
           final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_fragment_interview
                    , container, false);

            adapter=new ListViewAdapter();

            //getFeedbackData() 함수를 통해 adapter에 item 붙여줄 것이다.
            getInterviewData();

            listview=(ListView)layout.findViewById(R.id.interviewlist);
            listview.setAdapter(adapter);


            //임시 버튼
            writeBtn = (Button) layout.findViewById(R.id.writeBtn);
            writeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),AddInterviewActivity.class);
                    startActivity(intent);
                }
            });
            return layout;
        }


    public void getInterviewData() {

        //interview 이라는 자식의 데이터 몽땅 가져온다.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("interview");
        databaseReference.getDatabase();

        // databaseReference에 자식(데이터)가 추가되면 발행하는 이벤트이다.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //데이터에 추가된 InterviewDataItem 가져온다.
                InterviewDataItem item = dataSnapshot.getValue(InterviewDataItem.class);

                //해당 값을 adapter에 추가한다.
                adapter.addItem(getResources().getDrawable(R.drawable.han_character),item.getTitle(),item.getDesc());
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
