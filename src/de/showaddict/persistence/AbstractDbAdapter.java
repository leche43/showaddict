package de.showaddict.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractDbAdapter {
	
	private final Context context;
	protected ShowDatabaseHelper dbHelper;
	protected SQLiteDatabase db;

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
