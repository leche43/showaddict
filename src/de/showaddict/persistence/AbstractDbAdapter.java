package de.showaddict.persistence;

import java.util.logging.Logger;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractDbAdapter {
	
	public static Logger LOGGER = Logger.getLogger(AbstractDbAdapter.class.getName());
	
	private final Context context;
	protected ShowDatabaseHelper dbHelper;
	protected SQLiteDatabase db;
	
	private static String COLUMN_ID = "";
	private static String TABLE_NAME = "";

	public AbstractDbAdapter(Context context) {
		this.context = context;
		dbHelper = new ShowDatabaseHelper(this.context);
	}
	
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}

}
