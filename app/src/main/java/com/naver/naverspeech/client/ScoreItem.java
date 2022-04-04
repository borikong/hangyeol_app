package com.naver.naverspeech.client;

public class ScoreItem {
    private String name;
    private int num;
    private int vocabulary;
    private int pronunciation;
    private int continuity;
    private int speed;
    private int logic;


    public ScoreItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ScoreItem(String name,int num, int vocabulary, int pronunciation, int continuity, int speed, int logic) {
        this.name = name;
        this.num = num;
        this.vocabulary = vocabulary;
        this.pronunciation = pronunciation;
        this.continuity = continuity;
        this.speed = speed;
        this.logic = logic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(int vocabulary) {
        this.vocabulary = vocabulary;
    }

    public int getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(int pronunciation) {
        this.pronunciation = pronunciation;
    }

    public int getContinuity() {
        return continuity;
    }

    public void setContinuity(int continuity) {
        this.continuity = continuity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLogic() {
        return logic;
    }

    public void setLogic(int logic) {
        this.logic = logic;
    }
}
