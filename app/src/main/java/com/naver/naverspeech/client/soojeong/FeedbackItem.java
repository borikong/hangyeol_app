package com.naver.naverspeech.client.soojeong;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FeedbackItem {
    private String userId;
    private String date;
    private String content;
    private String title;
    private int like;
    private int disLike;
    private String file;

    //비어있는 생성자를 꼭 만들어준다.
    public FeedbackItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public FeedbackItem(String userId, String content, String title, String date) {
        this.userId = userId;
        this.content = content;
        this.title = title;
        this.date = date;
        like = 0;
        disLike=0;
    }
    public FeedbackItem(String userId, String content, String title, String date, String file) {
        this.userId = userId;
        this.content = content;
        this.title = title;
        this.date = date;
        this.file = file;
        like = 0;
        disLike=0;
    }

    public String getFile() {  return file;}

    public void setFile(String file) { this.file = file;   }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDisLike() {
        return disLike;
    }

    public void setDisLike(int disLike) {
        this.disLike = disLike;
    }
}
