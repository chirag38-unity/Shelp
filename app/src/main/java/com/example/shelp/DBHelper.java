package com.example.shelp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "ContactsData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table ContactDetails(name TEXT primary key, number INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists ContactDetails");
    }

    public boolean insertContact(String name, String number){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("number", number);
        long result = DB.insert("ContactDetails", null, contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteContact(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("ContactDetails", "name=?",new String[]{name});
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ContactDetails", null);
        return cursor;
    }

}
