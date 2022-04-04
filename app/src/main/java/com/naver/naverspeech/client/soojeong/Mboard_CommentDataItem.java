package com.naver.naverspeech.client.soojeong;

/**
 *  자유게시판 DTO(다영)
 */

public class Mboard_CommentDataItem {

    private String content,date,id;

    public Mboard_CommentDataItem(){

    }

    public Mboard_CommentDataItem(String content, String id, String date){
        this.content=content;
        this.id=id;
        this.date=date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
