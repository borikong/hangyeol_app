package com.naver.naverspeech.client.soojeong;

/**
 * Created by DS on 2018-08-11.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.naverspeech.client.R;

import java.util.ArrayList;


public class ListViewAdapter2 extends BaseAdapter {
    private ArrayList<InterviewDataItem2> listViewItemList = new ArrayList<InterviewDataItem2>() ;

    public ListViewAdapter2() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.interviewlistitem2, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title) ;
        TextView contentTextView = (TextView) convertView.findViewById(R.id.content) ;
        TextView userIdTextView = (TextView) convertView.findViewById(R.id.userId) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date) ;



        InterviewDataItem2 listViewItem2 = listViewItemList.get(position);


        titleTextView.setText(listViewItem2.getTitle());
        contentTextView.setText(listViewItem2.getContent());
        userIdTextView.setText(listViewItem2.getUserId());
        dateTextView.setText(listViewItem2.getDate());

        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(String title, String content, String date, String userId) {
        InterviewDataItem2 item = new InterviewDataItem2();


        item.setTitle(title);
        item.setContent(content);
        item.setDate(date);
        item.setUserId(userId);

        listViewItemList.add(item);
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(String title, String content, String date, String userId, String file) {
        InterviewDataItem2 item = new InterviewDataItem2();


        item.setTitle(title);
        item.setContent(content);
        item.setDate(date);
        item.setUserId(userId);
        item.setFile(file);

        listViewItemList.add(item);
    }
    // 아이템 데이터 추가를 위한 함수. ( Test 용 )
    public void addItem() {
        InterviewDataItem2 item = new InterviewDataItem2();
        listViewItemList.add(item);
    }


}
