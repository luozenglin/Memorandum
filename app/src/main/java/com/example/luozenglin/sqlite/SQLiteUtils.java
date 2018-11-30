package com.example.luozenglin.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.luozenglin.common.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUtils {
    private SQLiteDatabase db;
    public static final String DATABASE_NAME = "Memo.db";

    public SQLiteUtils(Context context,  int version){
        db = new DatabaseHelper(context,DATABASE_NAME,version).getWritableDatabase();
    }

    public SQLiteUtils(Context context){
        db = new DatabaseHelper(context,DATABASE_NAME).getWritableDatabase();
    }

    public void insert(Item item){
        db.execSQL("insert into memo (datetime,content) values(?,?)",
                new String [] {item.getDatetime(),item.getContent()});
        Log.i("SQLiteUtils","insert: "+Item.getDate(item.getDatetime())+" "+Item.getTime(item.getDatetime())
                +"\ncontent:  "+item.getContent());
    }

    public void update(Item item,String datetime){
        db.execSQL("update memo set datetime = ?, content = ? where datetime = ?",
                new String[] {item.getDatetime(),item.getContent(),datetime });
        Log.i("SQLiteUtils","update "+datetime);
    }

    public void delete(String datetime){
        db.execSQL("delete from memo where datetime = ?",new String[]{datetime});
        Log.i("SQLiteUtils","delete:"+Item.getDate(datetime)+" "+Item.getTime(datetime));
    }

    public List<Item> query(){
        List<Item> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from memo",null);
        if(cursor.moveToFirst()){
            do{
                Item item = new Item();
                item.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));
                item.setContent(cursor.getString(cursor.getColumnIndex("content")));
                list.add(item);
            }while (cursor.moveToNext());
        }
        cursor.close();
        List<Item> resultList = new ArrayList<>();
        if(list.size()>0){
            for(int i = list.size()-1;i>=0;i--){
                resultList.add(list.get(i));
            }
        }
        return resultList;
    }
}
