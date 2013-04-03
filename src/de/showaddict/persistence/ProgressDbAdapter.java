package de.showaddict.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import de.showaddict.entity.Progress;

public class ProgressDbAdapter extends AbstractDbAdapter {
	
	public static final String TABLE_NAME = "t_progress";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PERCENTAGE = "perc";
	public static final String COLUMN_AIRED = "aired";
	public static final String COLUMN_COMPLETED = "completed";
	public static final String COLUMN_LEFT = "left";
	public static final String COLUMN_SHOW_FK = "show_fk";
	
	private String[] allColumns = {
			COLUMN_ID, COLUMN_PERCENTAGE, COLUMN_AIRED, COLUMN_COMPLETED, COLUMN_LEFT
	};
	
	public static final String PROGRESS_TABLE_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PERCENTAGE
			+ " INTEGER, " + COLUMN_AIRED + " INTEGER, " + COLUMN_COMPLETED
			+ " INTEGER, " + COLUMN_LEFT + " INTEGER, " 
			+ COLUMN_SHOW_FK + " INTEGER, "
			+ "FOREIGN KEY(" + COLUMN_SHOW_FK + ") REFERENCES " + ShowDbAdapter.TABLE_NAME + "(" + ShowDbAdapter.COLUMN_ID + ")"
			+")" + ";";
	

	public ProgressDbAdapter(Context context) {
		super(context);
	}
	
	public void createProgress(Progress progress, long showId) {
		ContentValues values = new ContentValues();
	    values.put(COLUMN_PERCENTAGE, progress.getPercentage());
	    values.put(COLUMN_AIRED, progress.getAired());
	    values.put(COLUMN_COMPLETED, progress.getCompleted());
	    values.put(COLUMN_LEFT, progress.getLeft());
	    values.put(COLUMN_SHOW_FK, showId);
	    
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		Cursor cursor = queryBuilder.query(db, null , COLUMN_SHOW_FK + " = ?", 
				new String[] {"" + showId}, null, null, null);
		
		if(! (cursor.moveToFirst()) || cursor.getCount() == 0) {
		    db.insert(TABLE_NAME, null,
		    		values);
		} else {
			Progress progressDb = buildProgressAllColumns(cursor);
			if(!progress.equals(progressDb)) {
				updateProgress(values, cursor.getInt(0));
			} 
		}
		cursor.close();
	}
	
	private void updateProgress(ContentValues values, int progressId) {
		LOGGER.info("STARTED UPDATE PROGRESS: " + progressId);
		String whereClause = COLUMN_ID + " = ?";
		db.update(TABLE_NAME, values, whereClause, new String[] {"" + progressId});
		LOGGER.info("FINISHED UPDATE PROGRESS: " + progressId);
	}

	public Progress getProgressFromShow(long showId) {
		String whereSelection = "show_fk = ?";
		Cursor cursor = db.query(TABLE_NAME, allColumns, whereSelection, new String[] {"" + showId}, null, null,
				null);

		cursor.moveToFirst();
		
		Progress progress = buildProgressAllColumns(cursor);
			
		cursor.close();
		close();
		return progress;
	}

	/**
	 * method to build a new progress object from db
	 * 
	 * requires all columns
	 * 
	 * @param cursor
	 * @return
	 */
	private Progress buildProgressAllColumns(Cursor cursor) {
		Progress progress = new Progress();
		progress.setId(cursor.getInt(0));
		progress.setPercentage(cursor.getInt(1));
		progress.setAired(cursor.getInt(2));
		progress.setCompleted(cursor.getInt(3));
		progress.setLeft(cursor.getInt(4));
		return progress;
	}
}
