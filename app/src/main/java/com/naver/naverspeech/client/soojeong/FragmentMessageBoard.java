package com.naver.naverspeech.client.soojeong;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.naver.naverspeech.client.R;

/**
 * Created by DS on 2018-08-05.
 */

public class FragmentMessageBoard extends Fragment {

    private ListView listview;
    private ListViewAdapter adapter;

    public FragmentMessageBoard()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_fragment_messageboard
                , container, false  );


        adapter=new ListViewAdapter();

        listview=(ListView)layout.findViewById(R.id.interviewlist);
        listview.setAdapter(adapter);

        adapter.addItem(getResources().getDrawable(R.drawable.han_character),"여기가 자유게시판","자유게시판 조아요");
        adapter.addItem(getResources().getDrawable(R.drawable.han_character),"자유자유","자유다");
        adapter.addItem(getResources().getDrawable(R.drawable.dailytest),"데일리 테스트","안녕");
        adapter.addItem(getResources().getDrawable(R.drawable.dailytest),"이야이야호","안녕");
        adapter.addItem(getResources().getDrawable(R.drawable.dailytest),"WOW","안녕");
        return layout;
    }

}
