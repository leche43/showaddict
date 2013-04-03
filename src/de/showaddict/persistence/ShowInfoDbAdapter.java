package de.showaddict.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import de.showaddict.entity.ShowInfo;
import de.showaddict.entity.ShowInfo.Images;

public class ShowInfoDbAdapter extends AbstractDbAdapter {
	
	public static final String TABLE_NAME = "t_show_info";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TVDB_ID = "tvdb_id";
	public static final String COLUMN_IMBD_ID = "imdb_id";
	public static final String COLUMN_TVRAGE_ID = "tvrage_id";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_PLAYS = "plays";
	public static final String COLUMN_YEAR = "year";
	public static final String COLUMN_POSTER= "poster";
	public static final String COLUMN_FANART = "fanart";
	public static final String COLUMN_BANNER = "banner";
	public static final String COLUMN_GENRE = "genre";
	public static final String COLUMN_SHOW_FK = "show_fk";
	
	private String[] allColumns = { COLUMN_ID, COLUMN_TVDB_ID, COLUMN_IMBD_ID, COLUMN_TVRAGE_ID, COLUMN_URL,
									COLUMN_PLAYS, COLUMN_YEAR, COLUMN_POSTER, COLUMN_FANART, COLUMN_BANNER, 
									COLUMN_GENRE};
	
	public static final String SHOW_INFO_TABLE_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TVDB_ID
			+ " TEXT, " + COLUMN_IMBD_ID + " INTEGER, " + COLUMN_TVRAGE_ID
			+ " INTEGER, " + COLUMN_URL + " TEXT, "
			+ COLUMN_PLAYS + " INTEGER," 
			+ COLUMN_YEAR + " INTEGER," 
			+ COLUMN_POSTER + " TEXT," 
			+ COLUMN_FANART + " TEXT," 
			+ COLUMN_BANNER + " TEXT," 
			+ COLUMN_GENRE + " TEXT," 
			+ COLUMN_SHOW_FK + " INTEGER, "
			+ "FOREIGN KEY(" + COLUMN_SHOW_FK + ") REFERENCES " + ShowDbAdapter.TABLE_NAME + "(" + ShowDbAdapter.COLUMN_ID + ")"
			+ ");";
	
	
	public ShowInfoDbAdapter(Context context) {
		super(context);
	}
	
	
	public void createShowInfo(ShowInfo showInfo, long showId) {
		ContentValues values = new ContentValues();
		
	    values.put(COLUMN_TVDB_ID, showInfo.getTvdb_id());
	    values.put(COLUMN_IMBD_ID, showInfo.getImdb_id());
	    values.put(COLUMN_TVRAGE_ID, showInfo.getTvrage_id());
	    values.put(COLUMN_URL, showInfo.getUrl());
	    values.put(COLUMN_PLAYS, showInfo.getPlays());
	    values.put(COLUMN_YEAR, showInfo.getYear());
	    Images image = showInfo.getImages();
	    values.put(COLUMN_POSTER, image.getPoster());
	    values.put(COLUMN_FANART, image.getFanart());
	    values.put(COLUMN_BANNER, image.getBanner());
	    String genres = null;
	    for(String genre : showInfo.getGenres()) {
	    	genres += genre + ",";
	    }
	    values.put(COLUMN_GENRE, genres);
	    values.put(COLUMN_SHOW_FK, showId);
	    
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		Cursor cursor = queryBuilder.query(db, null , COLUMN_SHOW_FK + " = ?", 
				new String[] {"" + showId}, null, null, null);
		
		if(! (cursor.moveToFirst()) || cursor.getCount() == 0) {
		    db.insert(TABLE_NAME, null,
		    		values);
		} else {
			ShowInfo showInfoDb = buildShowInfoAllColumns(cursor);
			if(showInfo.equals(showInfoDb)) {
				updateShowInfo(values, cursor.getInt(0));
			}
		}
		cursor.close();
	}
	private void updateShowInfo(ContentValues values, long showInfoId) {
		LOGGER.info("STARTED UPDATE SHOW INFO: " + showInfoId);
		String whereClause = COLUMN_ID + " = ?";
		db.update(TABLE_NAME, values, whereClause, new String[] {"" + showInfoId});
		LOGGER.info("FINISHED UPDATE SHOW INFO: " + showInfoId);
	}
	
	/**
	 * Find all show infos in the database
	 * 
	 * @return a list of all found show infos
	 */
	public ShowInfo getShowInfoFromShow(long showId) {
		String whereSelection = "show_fk = ?";
		Cursor cursor = db.query(TABLE_NAME, allColumns, whereSelection, new String[] {"" + showId}, null, null,
				null);

		cursor.moveToFirst();
		
		ShowInfo showInfo = buildShowInfoAllColumns(cursor);
			
		cursor.close();
		return showInfo;
	}

	/**
	 * method to build a new show info object from db
	 * 
	 * requires all columns
	 * 
	 * @param cursor
	 * @return
	 */
	private ShowInfo buildShowInfoAllColumns(Cursor cursor) {
		ShowInfo showInfo = new ShowInfo();
		showInfo.setId(cursor.getInt(0));
		showInfo.setTvdb_id(cursor.getInt(1));
		showInfo.setImdb_id(cursor.getString(2));
		showInfo.setTvrage_id(cursor.getInt(3));
		showInfo.setUrl(cursor.getString(4));
		showInfo.setPlays(cursor.getInt(5));
		showInfo.setYear(cursor.getInt(6));
		Images image = showInfo.new Images();
		image.setPoster(cursor.getString(7));
		image.setFanart(cursor.getString(8));
		image.setBanner(cursor.getString(9));
		showInfo.setImages(image);
		List<String> genreList = new ArrayList<String>();
		String genres = cursor.getString(10);
		genreList = Arrays.asList(genres.split(","));
		showInfo.setGenres(genreList);
		return showInfo;
	}


}
