package com.nwitty.android.quotebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class QuoteProvider extends ContentProvider {
	
	private DatabaseHelper dbHelper;
	
	private static final String AUTHORITY = "com.nwitty.android.quotebook.contentprovider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/quotes");
	
	private static final int ALL_QUOTES = 1;
	private static final int SINGLE_QUOTE = 2;
	
	private static final UriMatcher uriMatcher;
	 static {
	  uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	  uriMatcher.addURI(AUTHORITY, "quotes", ALL_QUOTES);
	  uriMatcher.addURI(AUTHORITY, "quotes/#", SINGLE_QUOTE);
	 }
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
			case ALL_QUOTES:
				//noop 
				break;
			case SINGLE_QUOTE:
				String id = uri.getPathSegments().get(1);
				selection = QuoteDb.KEY_ROWID + "=" + id
						+ (!TextUtils.isEmpty(selection) ? 
						" AND (" + selection + ')' : "");
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		int deleteCount = db.delete(QuoteDb.SQLITE_TABLE, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		  case ALL_QUOTES: 
			  return "vnd.android.cursor.dir/vnd."+ AUTHORITY + ".quotes";
		  case SINGLE_QUOTE: 
			  return "vnd.android.cursor.item/vnd."+ AUTHORITY + ".quotes";
		  default: 
			  throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
			case ALL_QUOTES:
				//noop
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		 }
		 long id = db.insert(QuoteDb.SQLITE_TABLE, null, values);
		 getContext().getContentResolver().notifyChange(uri, null);
		 return Uri.parse(CONTENT_URI + "/" + id);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(QuoteDb.SQLITE_TABLE);
		 
		switch (uriMatcher.match(uri)) {
			case ALL_QUOTES:
				//noop 
				break;
			case SINGLE_QUOTE:
				String id = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(QuoteDb.KEY_ROWID + "=" + id);
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		 
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		  return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
			case ALL_QUOTES:
				//noop 
				break;
			case SINGLE_QUOTE:
				String id = uri.getPathSegments().get(1);
				selection = QuoteDb.KEY_ROWID + "=" + id
						+ (!TextUtils.isEmpty(selection) ? 
						" AND (" + selection + ')' : "");
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		int updateCount = db.update(QuoteDb.SQLITE_TABLE, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}

}
