package de.showaddict.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import de.showaddict.entity.Season;

public class SeasonDbAdapter extends AbstractDbAdapter {
	
	public static final String TABLE_NAME = "t_season";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SEASON = "season";
	public static final String COLUMN_PERCENTAGE = "perc";
	public static final String COLUMN_AIRED = "aired";
	public static final String COLUMN_COMPLETED = "completed";
	public static final String COLUMN_LEFT = "left";
	public static final String COLUMN_SHOW_FK = "show_fk";
	
	private String[] allColumns = {
			COLUMN_ID, COLUMN_SEASON, COLUMN_PERCENTAGE, COLUMN_AIRED, COLUMN_COMPLETED, COLUMN_LEFT
	};
	
	
	public static final String SEASON_TABLE_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COLUMN_SEASON + " INTEGER, "
			+ COLUMN_PERCENTAGE	+ " INTEGER, " 
			+ COLUMN_AIRED + " INTEGER, " 
			+ COLUMN_COMPLETED	+ " INTEGER, " 
			+ COLUMN_LEFT + " INTEGER,"
			+ COLUMN_SHOW_FK + " INTEGER, "
			+ "FOREIGN KEY(" + COLUMN_SHOW_FK + ") REFERENCES " + ShowDbAdapter.TABLE_NAME + "(" + ShowDbAdapter.COLUMN_ID + ")"
					+ ");";

	public SeasonDbAdapter(Context context) {
		super(context);
	}
	
	public long createSeason(Season season, long showId) {
		ContentValues values = new ContentValues();
	    values.put(COLUMN_SEASON, season.getSeason());
	    values.put(COLUMN_PERCENTAGE, season.getPercentage());
	    values.put(COLUMN_AIRED, season.getAired());
	    values.put(COLUMN_COMPLETED, season.getCompleted());
	    values.put(COLUMN_LEFT, season.getLeft());
	    values.put(COLUMN_SHOW_FK, showId);
	    
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		Cursor cursor = queryBuilder.query(db, null , COLUMN_SHOW_FK + " = ? AND " + COLUMN_SEASON + " = ?", 
				new String[] {"" + showId, season.getSeason().toString()}, null, null, null);
		
		long id;
		if(! (cursor.moveToFirst()) || cursor.getCount() == 0) {
		    id = db.insert(TABLE_NAME, null,
		    		values);
		} else {
			Season seasonDb = buildSeasonAllColumns(cursor);
			if(!season.equals(seasonDb)) {
				updateSeason(values, cursor.getInt(0));
			}
			id = cursor.getInt(0);
		}
		cursor.close();
	    
	    return id;
	}
	


	private void updateSeason(ContentValues values, int seasonId) {
		LOGGER.info("STARTED UPDATE SEASON: " + seasonId);
		String whereClause = COLUMN_ID + " = ?";
		db.update(TABLE_NAME, values, whereClause, new String[] {"" + seasonId});
		LOGGER.info("FINISHED UPDATE SEASON: " + seasonId);
	}
	
	public List<Season> getSeasonsFromShow(long showId) {
		List<Season> seasons = new ArrayList<Season>();
		String whereSelection = "show_fk = ?";
		Cursor cursor = db.query(TABLE_NAME, allColumns, whereSelection, new String[] {"" + showId}, null, null,
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Season season = buildSeasonAllColumns(cursor);
			
			seasons.add(season);
		}
			
		cursor.close();
		close();
		return seasons;
	}

	/**
	 * method to build a new season object from db
	 * 
	 * requires all columns
	 * 
	 * @param cursor
	 * @return
	 */
	private Season buildSeasonAllColumns(Cursor cursor) {
		Season season = new Season();
		season.setId(cursor.getInt(0));
		season.setSeason(cursor.getInt(1));
		season.setPercentage(cursor.getInt(2));
		season.setAired(cursor.getInt(3));
		season.setCompleted(cursor.getInt(4));
		season.setLeft(cursor.getInt(5));
		return season;
	}

}
