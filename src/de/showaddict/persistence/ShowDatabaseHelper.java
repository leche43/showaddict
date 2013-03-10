package de.showaddict.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import de.showaddict.entity.Show;

public class ShowDatabaseHelper extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "t_show";
	private static final String DATABASE_NAME = "show.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TVDB_ID = "tvdb_id";
	public static final String COLUMN_IMBD_ID = "imdb_id";
	public static final String COLUMN_TVRAGE_ID = "tvrage_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_PLAYS = "plays";

	private static final String SHOW_TABLE_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TVDB_ID
			+ " TEXT, " + COLUMN_IMBD_ID + " INTEGER, " + COLUMN_TVRAGE_ID
			+ " INTEGER, " + COLUMN_TITLE + " TEXT," + COLUMN_URL + " TEXT, "
			+ COLUMN_PLAYS + " INTEGER" + ")" + ";";

	public ShowDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public ShowDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SHOW_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public Long createShow(Show show) {
		Long id;
		ContentValues values = new ContentValues();
		values.put(COLUMN_TVDB_ID, show.getTvdb_id());
		values.put(COLUMN_TITLE, show.getTitle());
		SQLiteDatabase db = getWritableDatabase();
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		Cursor cursor = queryBuilder.query(db, null , "tvdb_id = ?", new String[] {""+show.getTvdb_id()}, null, null, null);
		
		if(! (cursor.moveToFirst()) || cursor.getCount() == 0) {
			id = db.insert(TABLE_NAME, null, values);
		} else {
			id = cursor.getLong(0);
		}
		cursor.close();
		close();
		return id;
	}

	/**
	 * Find all shows in the database
	 * 
	 * @return a list of all found shows
	 */
	public List<Show> getAllShows() {
		List<Show> shows = new ArrayList<Show>();
		SQLiteDatabase db = getWritableDatabase();
		String[] columns = { COLUMN_TVDB_ID, COLUMN_TITLE };

		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null,
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Show show = new Show();
			show.setTvdb_id(cursor.getInt(0));
			show.setTitle(cursor.getString(1));
			shows.add(show);
			cursor.moveToNext();
		}
		cursor.close();
		close();
		return shows;
	}

}
