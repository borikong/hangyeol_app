package com.naver.naverspeech.client.soojeong;

/**
 * Created by DS on 2018-09-21.
 */

public class User {
    private String id;
    private String pwd;
    private String name;
    private String favorite;
    private int day;

    //비어있는 생성자를 꼭 만들어준다.
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String pwd, String name, String favorite) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.favorite = favorite;
        day=0;
    }

    public User(String id, String pwd, String name, String favorite, int day) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.favorite = favorite;
        this.day=day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public int getDay() { return day; }

    public void setDay(int day) { this.day = day; }

    public void addDay() { day +=1;}

}
