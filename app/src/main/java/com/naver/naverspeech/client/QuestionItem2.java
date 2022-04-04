package com.naver.naverspeech.client;

public class QuestionItem2 {
    private String category;
    private String question1;
    private String question2;
    private String question3;
    private String question4;
    private String question5;

    public QuestionItem2() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public QuestionItem2(String category, String question1, String question2, String question3, String question4, String question5 ) {
        this.category = category;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }
}
