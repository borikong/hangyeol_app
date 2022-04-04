package com.naver.naverspeech.client.dayeong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.naver.naverspeech.client.R;

import com.naver.naverspeech.client.soojeong.MainActivity3;


/**
 * 게시판 액티비티(다영)
 */

public class BoardActivity extends AppCompatActivity {

    private String[] navItems = {"사용자 정보", "설정", "회원탈퇴","로그아웃"};
    private ListView navlist;
    private LinearLayout mainContainer;
    private ImageButton menu;
    private ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_board);
        setDrawer(navItems);
        setTab();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(BoardActivity.this, MainActivity3.class);
        startActivity(intent);
    }

    //viewpager
    void setTab(){
        Board_ViewPagerAdapter adapter=new Board_ViewPagerAdapter(getSupportFragmentManager());
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout mTab=(TabLayout)findViewById(R.id.tabs);
        mTab.setupWithViewPager(viewPager);

    }

    //drawer
    void setDrawer(String[] navItems){
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navlist = (ListView) findViewById(R.id.nav_list); //drawer에 보여질 list
        mainContainer = (LinearLayout) findViewById(R.id.mainContainer); //메인 컨텐츠

        //drawer에 나타날 list 연결
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view=super.getView(position,convertView,parent);

                TextView tv=(TextView)view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        navlist.setAdapter(adapter);

        //메뉴아이콘 누르면 drawer열리게 하는 Listener
        menu=(ImageButton) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }


}
