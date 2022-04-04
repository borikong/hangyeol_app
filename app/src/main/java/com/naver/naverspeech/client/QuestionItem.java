package com.naver.naverspeech.client;

public class QuestionItem {
    private int num;
    private String vocabulary;
    private String pronunciation;
    private String continuity;
    private String speed;
    private String logic;

    public QuestionItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public QuestionItem(int num, String vocabulary, String pronunciation, String continuity, String speed, String logic) {
        this.num = num;
        this.vocabulary = vocabulary;
        this.pronunciation = pronunciation;
        this.continuity = continuity;
        this.speed = speed;
        this.logic = logic;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getContinuity() {
        return continuity;
    }

    public void setContinuity(String continuity) {
        this.continuity = continuity;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }
}
