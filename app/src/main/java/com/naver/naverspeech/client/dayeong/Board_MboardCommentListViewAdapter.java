package com.naver.naverspeech.client.dayeong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import com.naver.naverspeech.client.R;

/**
 * 자유게시판 댓글 리스트뷰 어댑터
 */

public class Board_MboardCommentListViewAdapter extends BaseAdapter {

    private ArrayList<Mboard_CommentDataItem> listViewItemList = new ArrayList<Mboard_CommentDataItem>();

    public Board_MboardCommentListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.board_listitem, parent, false);
        }

        TextView commentTextView = (TextView) convertView.findViewById(R.id.title); //comment
        TextView datenidTextView = (TextView) convertView.findViewById(R.id.datenid);

        Mboard_CommentDataItem listViewItem = listViewItemList.get(position);
        String dateformat = listViewItem.getDate();
        String datestr = dateformat.substring(0, 4) + "년" + dateformat.substring(4, 6) + "월" + dateformat.substring(6, 8) + "일" + " " + dateformat.substring(8, 10) + ":" + dateformat.substring(10, 12);
        commentTextView.setText(listViewItem.getContent());
        datenidTextView.setText(listViewItem.getId() + "-" + datestr);

        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(String content,String id, String date) {
        Mboard_CommentDataItem item = new Mboard_CommentDataItem();

        item.setContent(content);
        item.setId(id);
        item.setDate(date);

        listViewItemList.add(item);
    }

}
