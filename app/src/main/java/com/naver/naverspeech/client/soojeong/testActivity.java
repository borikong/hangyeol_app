package com.naver.naverspeech.client.soojeong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.naver.naverspeech.client.R;

/**
 * Created by DS on 2018-08-07.
 */

public class testActivity extends AppCompatActivity {

    private ListView listview;
    private ListViewAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter=new ListViewAdapter();

        setContentView(R.layout.view);
        listview=(ListView)findViewById(R.id.interviewlist);
        listview.setAdapter(adapter);

        adapter.addItem(getResources().getDrawable(R.drawable.menu),"안녕","안녕");
        adapter.addItem(getResources().getDrawable(R.drawable.han_character),"한결캐릭터","안녕");
        adapter.addItem(getResources().getDrawable(R.drawable.dailytest),"데일리 테스트","안녕");
    }

}
