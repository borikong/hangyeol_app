package com.naver.naverspeech.client.seohyun;

/**
 * Created by DS on 2018-08-11.
 */

public class dailyTestScoreItem {

    private int continuity, logic, pronunciation, speed, vocabulary, num;
    private String date, name;

    public dailyTestScoreItem(){}

    public dailyTestScoreItem(int continuity, int logic, int pronunciation, int speed, int vocabulary, int num, String date, String name) {
        this.continuity = continuity;
        this.logic = logic;
        this.pronunciation = pronunciation;
        this.speed = speed;
        this.vocabulary = vocabulary;
        this.num=num;
        this.date = date;
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setContinuity(int continuity) {
        this.continuity = continuity;
    }

    public void setLogic(int logic) {
        this.logic = logic;
    }

    public void setPronunciation(int pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setVocabulary(int vocabulary) {
        this.vocabulary = vocabulary;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContinuity() {
        return continuity;
    }

    public int getLogic() {
        return logic;
    }

    public int getPronunciation() {
        return pronunciation;
    }

    public int getSpeed() {
        return speed;
    }

    public int getVocabulary() {
        return vocabulary;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
}
