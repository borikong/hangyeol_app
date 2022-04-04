package com.naver.naverspeech.client.soojeong;

import android.graphics.drawable.Drawable;

/**
 * Created by DS on 2018-08-07.
 */

public class InterviewDataItem {
   private Drawable icon;

    private String title;
    private String desc;

    public InterviewDataItem(){

    }


    public  InterviewDataItem(Drawable icon, String title, String desc){

        this.icon=icon;
        this.title=title;
        this.desc=desc;
    }


    public  InterviewDataItem( String title, String desc){
        this.title=title;
        this.desc=desc;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
