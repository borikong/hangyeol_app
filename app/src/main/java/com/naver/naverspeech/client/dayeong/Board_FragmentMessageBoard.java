package com.naver.naverspeech.client.dayeong;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.R;

/**
 * 자유게시판 프레그먼트
 */

public class Board_FragmentMessageBoard extends Fragment {

    private ListView listview;
    private Board_MboardListViewAdapter adapter;
    private Button writeBtn;
    private MboardDataItem item;


    public Board_FragmentMessageBoard()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(FirebaseDatabase.getInstance()==null){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_fragment_messageboard
                , container, false  );

        adapter=new Board_MboardListViewAdapter();

       getmBoardData();

        listview=(ListView)layout.findViewById(R.id.messagelist);
        listview.setAdapter(adapter);


       //글쓰기 버튼
        writeBtn = (Button) layout.findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Board_AddmBoardActivity.class);
                startActivity(intent);
            }
        });

        setListViewListener();

        return layout;
    }


    public void getmBoardData() {

        //mboard의 자식데이터
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("board_mboard");
        databaseReference.getDatabase();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MboardDataItem item = dataSnapshot.getValue(MboardDataItem.class);

                //해당 값을 adapter에 추가한다.
                adapter.addItem(item.getTitle(),item.getContent(),item.getId(),item.getDate());
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

    //리스트뷰 항목을 클릭했을 때 리스너
    public void setListViewListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Board_MboardDetailActivity.class);
                item = (MboardDataItem) adapter.getItem(position);

                intent.putExtra("date",item.getDate());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
