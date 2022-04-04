package com.naver.naverspeech.client;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper1 extends SQLiteOpenHelper {


    public SQLiteHelper1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE SCORE (_id INTEGER PRIMARY KEY AUTOINCREMENT, userName Text, num INTEGER, vocabulary INTEGER, pronunciation INTEGER, continuity INTEGER, speed INTEGER, logic INTEGER);");

        Log.e("SONGTest","이거 돌아가니마니");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String userName) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SCORE VALUES(null, '" + userName + "', null, null , null, null, null, null);");
        db.close();
    }
    public  void update(String userName, int num){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("UPDATE SCORE SET num = '" + num + "' WHERE userName = '" + userName + "';");
        db.close();
    }


    public  void update1(String userName, int vocabulary){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("UPDATE SCORE SET vocabulary = '" + vocabulary + "' WHERE userName = '" + userName + "';");
        db.close();
    }
    public  void update2(String userName, int pronunciation){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("UPDATE SCORE SET pronunciation = '" + pronunciation +"' where userName = '" + userName + "';");
        db.close();
    }
    public  void update3(String userName, int continuity){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("UPDATE SCORE SET continuity = '" + continuity +"' where userName = '" + userName + "';");
        db.close();
    }
    public  void update4(String userName, int speed){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("UPDATE SCORE SET speed = '" + speed +"' where userName = '" + userName + "';");
        db.close();
    }
    public  void update5(String userName, int logic){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("UPDATE SCORE SET logic = '" + logic +"' where userName = '" + userName + "';");
        db.close();
    }


    public void remove(String userName) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM SCORE WHERE userName='" + userName + "';");
        db.close();
    }


    public int[] getScore(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        int[] result;
        Cursor cursor = db.rawQuery("SELECT * FROM SCORE WHERE userName = '"+userName +"'", null);
        result = new int[5];
        while (cursor.moveToNext()) {
            result[0] = cursor.getInt(3);
            result[1] = cursor.getInt(4);
            result[2] = cursor.getInt(5);
            result[3] = cursor.getInt(6);
            result[4] = cursor.getInt(7);
        }

        return result;
    }
    public int getNum(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM SCORE WHERE userName = '"+userName +"'", null);
        while (cursor.moveToNext()) {
            result = cursor.getInt(2);
        }

        return result;
    }


    public String getUserId() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT * FROM USER", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(1);
        }
        return  result;
    }

}
