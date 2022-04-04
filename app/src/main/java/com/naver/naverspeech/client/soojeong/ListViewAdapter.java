package com.naver.naverspeech.client.soojeong;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naver.naverspeech.client.R;

import java.util.ArrayList;

/**
 * Created by DS on 2018-08-07.
 */

public class ListViewAdapter extends BaseAdapter{

    private ArrayList<InterviewDataItem> listViewItemList = new ArrayList<InterviewDataItem>() ;

    public ListViewAdapter() {

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
            convertView = inflater.inflate(R.layout.interviewlistitem, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;

        InterviewDataItem listViewItem = listViewItemList.get(position);


        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());

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
    public void addItem(Drawable icon, String title, String desc) {
        InterviewDataItem item = new InterviewDataItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }

    // 아이템 데이터 추가를 위한 함수. ( Test 용 )
    public void addItem() {
        InterviewDataItem item = new InterviewDataItem();
        listViewItemList.add(item);
    }
}
