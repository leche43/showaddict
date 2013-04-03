package de.showaddict.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import de.showaddict.entity.NextEpisode;

public class NextEpisodeDbAdapter extends AbstractDbAdapter {
	
	
	public static final String TABLE_NAME = "t_next_episode";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SEASON = "season";
	public static final String COLUMN_NUM = "num";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_FIRST_AIRED = "first_aired";
	public static final String COLUMN_IMAGE_URL = "image_url";
	public static final String COLUMN_SHOW_FK = "show_fk";
	
	private String[] allColumns = {
			COLUMN_ID, COLUMN_SEASON, COLUMN_NUM, COLUMN_TITLE, COLUMN_FIRST_AIRED, COLUMN_IMAGE_URL
	};
	

	
	public static final String NEXT_EPISODE_TABLE_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SEASON
			+ " INTEGER, " + COLUMN_NUM + " INTEGER, " 
			+ COLUMN_TITLE + " TEXT, "
			+ COLUMN_FIRST_AIRED + " INTEGER, "
			+ COLUMN_IMAGE_URL + " TEXT,"
			+ COLUMN_SHOW_FK + " INTEGER, "
			+ "FOREIGN KEY(" + COLUMN_SHOW_FK + ") REFERENCES " + ShowDbAdapter.TABLE_NAME + "(" + ShowDbAdapter.COLUMN_ID + ")"
					+");";

	public NextEpisodeDbAdapter(Context context) {
		super(context);
	}
	
	public void createNextEpisode(NextEpisode nextEpisode, long showId) {
		ContentValues values = new ContentValues();
	    values.put(COLUMN_SEASON, nextEpisode.getSeason());
	    values.put(COLUMN_NUM, nextEpisode.getNum());
	    values.put(COLUMN_TITLE, nextEpisode.getTitle());
	    values.put(COLUMN_FIRST_AIRED, nextEpisode.getFirst_aired());
	    values.put(COLUMN_IMAGE_URL, nextEpisode.getImages().getScreen());
	    values.put(COLUMN_SHOW_FK, showId);
	    
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		Cursor cursor = queryBuilder.query(db, null , COLUMN_SHOW_FK + " = ?", 
				new String[] {"" + showId}, null, null, null);
		
		if(! (cursor.moveToFirst()) || cursor.getCount() == 0) {
		    db.insert(TABLE_NAME, null,
		    		values);
		} else {
			if(nextEpisode.getTitle() != cursor.getString(3)) {
				updateNextEpisode(values, cursor.getInt(0));
			} 
		}
		cursor.close();
	}
	
	private void updateNextEpisode(ContentValues values, long nextEpisodeId) {
		LOGGER.info("STARTED UPDATE NEXT EPISODE: " + nextEpisodeId);
		String whereClause = COLUMN_ID + " = ?";
		db.update(TABLE_NAME, values, whereClause, new String[] { "" + nextEpisodeId});
		LOGGER.info("FINISHED UPDATE NEXT EPISODE: " + nextEpisodeId);
	}

	public NextEpisode getNextEpisodeFromShow(long showId) {
		String whereSelection = "show_fk = ?";
		Cursor cursor = db.query(TABLE_NAME, allColumns, whereSelection, new String[] {"" + showId}, null, null,
				null);

		cursor.moveToFirst();
		
		NextEpisode nextEpisode = new NextEpisode();
		nextEpisode.setId(cursor.getInt(0));
		nextEpisode.setSeason(cursor.getInt(1));
		nextEpisode.setNum(cursor.getInt(2));
		nextEpisode.setTitle(cursor.getString(3));
		nextEpisode.setFirst_aired(cursor.getInt(4));
		nextEpisode.setImages(nextEpisode.new Images(cursor.getString(5)));
			
		cursor.close();
		close();
		return nextEpisode;
	}

}
