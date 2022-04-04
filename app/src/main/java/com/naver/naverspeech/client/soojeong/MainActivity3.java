package com.naver.naverspeech.client.soojeong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.DailyTestActivity;
import com.naver.naverspeech.client.MainActivity;
import com.naver.naverspeech.client.R;
import com.naver.naverspeech.client.SQLiteHelper1;
import com.naver.naverspeech.client.StartTrainingActivity;
import com.naver.naverspeech.client.TrainingActivity;
import com.naver.naverspeech.client.dayeong.BoardActivity;
import com.naver.naverspeech.client.dayeong.VirtualInterview_faceActivitity;
import com.naver.naverspeech.client.seohyun.Fragment1;
import com.naver.naverspeech.client.seohyun.MainActivity2;

import org.xmlpull.v1.XmlPullParser;


public class MainActivity3 extends AppCompatActivity {

    private String[] navItems = {"사용자 정보", "설정", "회원탈퇴", "로그아웃"};
    private ListView navlist;
    private TableLayout mainContainer;
    ActionBarDrawerToggle drawerToggle;
    private ImageButton menu;
    private  XmlPullParser xpp;
    ImageButton dailyTest, interview, training, record, feedback, board;
    Integer today;
    public static StringBuilder sb;//

    FirebaseDatabase database;
    DatabaseReference myRef;
    static boolean calledAlready = false;
    DatabaseReference databaseReference;

    private TextView saying;

    SQLiteHelper1 dbHelper1;

    String userName = "SONG";
    String trainingType = "vocabulary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        dbHelper1 = new SQLiteHelper1(getApplicationContext(), "Score.db", null, 1);

        Log.e("SONGTest","이게더ㅚ니?");

        // 로그인 만들면 없어질것!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        dbHelper1.insert("SONG");


        saying = (TextView) findViewById(R.id.saying);
        if ( FirebaseDatabase.getInstance() == null)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready = true;
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("info");
        databaseReference.getDatabase();


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("today")){
                    today = Integer.parseInt(dataSnapshot.getValue().toString());
                    saying.setText(1+"일차입니다. 파이팅!");
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



        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navlist = (ListView) findViewById(R.id.nav_list); //drawer에 보여질 list
        mainContainer = (TableLayout) findViewById(R.id.mainContainer); //메인 컨텐츠


        //drawer에 나타날 list 연결
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        navlist.setAdapter(adapter);


        //메뉴아이콘 누르면 drawer열리게 하는 Listener
        menu = (ImageButton) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        setButton();

    }

    void setButton() {
        dailyTest = (ImageButton) findViewById(R.id.dailytest);
        interview = (ImageButton) findViewById(R.id.interview);
        training = (ImageButton) findViewById(R.id.training);
        record = (ImageButton) findViewById(R.id.record);
        feedback = (ImageButton) findViewById(R.id.feedback);
        board = (ImageButton) findViewById(R.id.m_board);

        //데일리테스트 버튼 리스너
        dailyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("message");
                myRef.setValue("Hello, World!");

                // 이름과 날짜를 업데이트함
                dbHelper1.update(userName, 1);

                Intent intent = new Intent(MainActivity3.this, DailyTestActivity.class);
                startActivity(intent);

            }
        });

        //가상면접 버튼 리스너
        interview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity3.this, VirtualInterview_faceActivitity.class);
                startActivity(intent);
            }
        });

        //트레이닝 버튼 리스너
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이름과 선택영역을 업데이트함
                dbHelper1.update(userName, 2);

                Intent intent = new Intent(MainActivity3.this, StartTrainingActivity.class);
                startActivity(intent);
            }
        });

        //기록장 버튼 리스너
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("dailyTestQuestion");
                databaseReference.getDatabase();

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String temp = "day"+today;
                        if(dataSnapshot.getKey().equals(temp)) {
                            Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
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


                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                startActivity(intent);

            }
        });


        //피드백 버튼 리스너
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, SujeongTestActivity.class);
                startActivity(intent);

            }
        });


        //게시판 Activity로 이동
        board.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent(MainActivity3.this, BoardActivity.class);
                                         startActivity(intent);
                                     }
                                 }
        );
    }


}
