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
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.R;

/**
 * 면접정보게시판 프레그먼트
 */

public class Board_FragmentInterview extends Fragment {

    private ListView listview;
    private Board_InterviewListViewAdapter adapter;
    private Button writeBtn,searchbtn;
    private Spinner spinner;
    DatabaseReference databaseReference;
    InterviewDataItem item;
    boolean isnew=false;


    public Board_FragmentInterview() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseDatabase.getInstance() == null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_fragment_interview
                , container, false);
        getInterviewData();

        adapter = new Board_InterviewListViewAdapter();


        listview = (ListView) layout.findViewById(R.id.interviewlist);
        listview.setAdapter(adapter);
        //글쓰기 버튼
        writeBtn = (Button) layout.findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Board_AddInterviewActivity.class);
                startActivity(intent);
            }
        });

        searchbtn=(Button)layout.findViewById(R.id.searchbtn);
        searchbtn.setText("검색");
        spinner=(Spinner)layout.findViewById(R.id.spinner);

        //항목별 조회 버튼
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isnew=true; //항목별 조회 버튼이 눌렸음->리스트뷰 어댑터를 다시 설정해야 함
                searchData(spinner.getSelectedItem().toString());

            }
        });



        setListViewListener();
        return layout;
    }

//직종에 따라 글 검색
    private void searchData(final String type){
        databaseReference = FirebaseDatabase.getInstance().getReference("board_interview");
        databaseReference.getDatabase();
        if(isnew==true){
            adapter=null;
        }

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                InterviewDataItem item=dataSnapshot.getValue(InterviewDataItem.class);
                if(isnew==true){
                    adapter=new Board_InterviewListViewAdapter();
                    listview.setAdapter(adapter);
                    isnew=false;
                }

                if(item.getType().equals(type)){
                    adapter.addItem(item.getTitle(), item.getCompany(), item.getType(), item.getSpecific_type(), item.getContent(), item.getId(), item.getDate());
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

    public void getInterviewData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("board_interview");
        databaseReference.getDatabase();

        // databaseReference에 자식(데이터)가 추가되면 발행하는 이벤트이다.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //데이터에 추가된 InterviewDataItem 가져온다.
                InterviewDataItem item = dataSnapshot.getValue(InterviewDataItem.class);

                //해당 값을 adapter에 추가한다.
                adapter.addItem(item.getTitle(), item.getCompany(), item.getType(), item.getSpecific_type(), item.getContent(), item.getId(), item.getDate());
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
                Intent intent = new Intent(getContext(), Board_InterviewDetailActivity.class);
               item = (InterviewDataItem) adapter.getItem(position);
                // String title=((InterviewDataItem)adapter.getItem(position)).getTitle();

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
