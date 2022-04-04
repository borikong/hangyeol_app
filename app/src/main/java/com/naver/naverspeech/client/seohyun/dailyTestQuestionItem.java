package com.naver.naverspeech.client.seohyun;

/**
 * Created by DS on 2018-08-11.
 */

public class dailyTestQuestionItem {

    private String continuity;
    private String logic;
    private String pronunciation;
    private String speed;
    private String vocabulary;
    private int num;

    public dailyTestQuestionItem(){}

    public dailyTestQuestionItem(String continuity, String logic, String pronunciation, String speed, String vocabulary, int num) {
        this.continuity = continuity;
        this.logic = logic;
        this.pronunciation = pronunciation;
        this.speed = speed;
        this.vocabulary=vocabulary;
        this.num=num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public String getContinuity() {
        return continuity;
    }

    public String getLogic() {
        return logic;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getSpeed() {
        return speed;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setContinuity(String continuity) {
        this.continuity = continuity;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }
}
