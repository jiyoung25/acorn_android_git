package com.example.step14sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //App에서 DB를 처음 사용할 때 호출되는 메소드
    @Override
    public void onCreate(SQLiteDatabase db) {
        //사용할 테이블을 만들면 된다.
        //1. 실행할 sql문을 준비하고
        String sql = "CREATE TABLE todo"+
                " (num INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, regdate TEXT)";
        //2. SQLiteDataBase객체를 이용해서 실행한다.
        db.execSQL(sql);
    }

    //DB를 리셋(업그레이드)할 때 호출되는 메소드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //업그레이드할 내용을 작성하면 된다.
        db.execSQL("DROP TABLE IF EXISTS todo"); //만일 테이블이 존재하면 삭제한다.
        //다시 만들어질 수 있도록 onCreate()메소드를 호출한다.
        onCreate(db);
    }
}
