package com.nwitty.android.quotebook;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuoteDb {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_QUOTE = "quote";
	public static final String KEY_SOURCE = "source";
	
	private static final String LOG_TAG = "QuotesDB";
	public static final String SQLITE_TABLE = "Quotes";
	
	private static final String DATABASE_CREATE =
			  "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
			   KEY_ROWID + " integer PRIMARY KEY autoincrement," +
			   KEY_QUOTE + "," +
			   KEY_SOURCE + ");";
	 
	public static void onCreate(SQLiteDatabase db) {
	  Log.w(LOG_TAG, DATABASE_CREATE);
	  db.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
		onCreate(db);
	}
}
