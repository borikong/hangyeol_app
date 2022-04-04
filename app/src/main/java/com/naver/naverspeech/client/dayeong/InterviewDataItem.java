package com.naver.naverspeech.client.dayeong;

/**
 * 면접 정보 게시판 DTO(다영)
 */

public class InterviewDataItem {
    private String title,company,type,specific_type,content,id,date;

    public InterviewDataItem(){

    }


    public  InterviewDataItem(String title, String company, String type, String specific_type, String content,String id,String date){
        this.title=title;
        this.company=company;
        this.type=type;
        this.specific_type=specific_type;
        this.content=content;
        this.id=id;
        this.date=date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany(){
        return company;
    }

    public void setCompany(String company){
        this.company=company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecific_type() {
        return specific_type;
    }

    public void setSpecific_type(String specific_type) {
        this.specific_type = specific_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
