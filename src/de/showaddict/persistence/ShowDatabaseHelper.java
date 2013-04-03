package de.showaddict.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ShowDatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "showaddict.db";
	private static final int DATABASE_VERSION = 1;
	
	public ShowDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public ShowDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ShowDbAdapter.SHOW_TABLE_CREATE);
		db.execSQL(NextEpisodeDbAdapter.NEXT_EPISODE_TABLE_CREATE);
		db.execSQL(ProgressDbAdapter.PROGRESS_TABLE_CREATE);
		db.execSQL(ShowInfoDbAdapter.SHOW_INFO_TABLE_CREATE);
		db.execSQL(SeasonDbAdapter.SEASON_TABLE_CREATE);
		db.execSQL(EpisodeDbAdapter.EPISODE_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + NextEpisodeDbAdapter.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + EpisodeDbAdapter.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + SeasonDbAdapter.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ProgressDbAdapter.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ShowInfoDbAdapter.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ShowDbAdapter.TABLE_NAME);
		onCreate(db);
	}
	
}
