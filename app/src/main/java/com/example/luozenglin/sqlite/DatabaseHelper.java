package com.example.luozenglin.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	private static final String CREATE_BOOK = "create table memo(datetime text,content text)";

	public DatabaseHelper(Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public DatabaseHelper(Context context,String name,int version){
		this(context,name,null,version);
	}

	public DatabaseHelper(Context context,String name){
		this(context,name,null,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BOOK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
