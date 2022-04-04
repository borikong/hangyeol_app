package com.naver.naverspeech.client.soojeong;

/**
 * Created by DS on 2018-08-07.
 */

public class InterviewDataItem2 {
    private String userId;
    private String date;
    private String content;
    private String title;
    private String file;
    public InterviewDataItem2(){

    }

    public InterviewDataItem2(String userId, String date, String content, String title) {
        this.userId = userId;
        this.date = date;
        this.content = content;
        this.title = title;
    }


    public InterviewDataItem2(String userId, String content, String title, String date, String file) {
        this.userId = userId;
        this.content = content;
        this.title = title;
        this.date = date;
        this.file = file;

    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

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

}
