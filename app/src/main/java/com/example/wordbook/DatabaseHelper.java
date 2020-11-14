package com.example.wordbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLES="create table word_dic(_id INTEGERT PRIMARY KEY AUTOINCREMENT,word,detail)";
    public DatabaseHelper(Context context, String name, int version){
        super(context,name,null,version);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLES);
    }

    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2){
        // TODO Auto-generated method stub
    }
}

