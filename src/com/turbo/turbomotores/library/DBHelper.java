package com.turbo.turbomotores.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "turbomotores.db";
	public static final String TABLE_ARTICLE = "article";
	public static final String TABLE_CATEGORY = "category";
	public static final String TABLE_TAG = "tag";
	public static final String TABLE_ARTICLECATEGORY = "articlecategory";
	public static final String TABLE_ARTICLETAG = "articletag";
	
	private static final String DATABASE_CREATE_ARTICLE = "create table "
			+ TABLE_ARTICLE + "( " + "_id" + " integer primary key autoincrement, " 
								+ "title" + " text not null, " 
								+ "url" + " text not null, " 
								+ "excerpt" + " text not null, "
								+ "text" + " text not null, "
								+ "author" + " text not null, "
								+ "imageUrl" + " text not null, "
								+ "date" + " text not null);";
	
	private static final String DATABASE_CREATE_CATEGORY = "create table "
			+ TABLE_CATEGORY + "( " + "_id" + " integer primary key autoincrement, " 
								+ "name" + " text not null);";
	
	private static final String DATABASE_CREATE_TAG = "create table "
			+ TABLE_TAG + "( " + "_id" + " integer primary key autoincrement, " 
								+ "name" + " text not null);";
	
	private static final String DATABASE_CREATE_ARTICLECATEGORY = "create table "
			+ TABLE_ARTICLECATEGORY + "( " + "_id" + " integer primary key autoincrement, " 
								+ "articleId" + " integer not null," 
								+ "categoryId" + " integer not null);";
	
	private static final String DATABASE_CREATE_ARTICLETAG = "create table "
			+ TABLE_ARTICLETAG + "( " + "_id" + " integer primary key autoincrement, " 
								+ "articleId" + " integer not null," 
								+ "tagId" + " integer not null);";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_ARTICLE);
		database.execSQL(DATABASE_CREATE_CATEGORY);
		database.execSQL(DATABASE_CREATE_TAG);
		database.execSQL(DATABASE_CREATE_ARTICLECATEGORY);
		database.execSQL(DATABASE_CREATE_ARTICLETAG);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + DATABASE_CREATE_ARTICLE);
		db.execSQL("DROP TABLE IF EXISTS" + DATABASE_CREATE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS" + DATABASE_CREATE_TAG);
		db.execSQL("DROP TABLE IF EXISTS" + DATABASE_CREATE_ARTICLECATEGORY);
		db.execSQL("DROP TABLE IF EXISTS" + DATABASE_CREATE_ARTICLETAG);
		onCreate(db);
	}

}
