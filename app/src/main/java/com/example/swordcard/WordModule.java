package com.example.swordcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class WordModule extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public WordModule(Context context){
        super(context,"wordcard.db",null,1);
        System.out.println("Word Module Loaded");
        db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists word(" +
                "english text primary key," +
                "mean text);";
        db.execSQL(sql);
    }
    public void clear(){
        db.execSQL("drop table if exists word");
        onCreate(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addWord(String english,String mean){
        ContentValues c = new ContentValues();
        c.put("english",english);
        c.put("mean",mean);
        try{
        db.insertOrThrow("word",null,c);
        }
        catch (Exception e){
            db.update("word",c,"english=?",new String[]{english});
        }
    }
    public void addWord(WordEntry entry){
        addWord(entry.get_eng(),entry.get_mean());
    }
    public void removeWord(String english){
        try {
            db.delete("word", "english=?", new String[]{english});
        }catch(Exception e){}
    }
    public List<WordEntry> getAllWords(){
        ArrayList<WordEntry> list = new ArrayList<WordEntry>();
        try {
            Cursor c = db.query("word", null, null, null, null, null, null, null);

            c.moveToFirst();
            while (c.getPosition() < c.getCount()) {
                String english = c.getString(c.getColumnIndex("english"));
                String mean = c.getString(c.getColumnIndex("mean"));
                WordEntry entry = new WordEntry(english, mean);
                list.add(entry);
                c.moveToNext();
            }
        }
        catch(Exception e){}
        return list;
    }
//    public List<WordEntry>;
}