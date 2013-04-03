package de.showaddict.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import de.showaddict.entity.Episode;

public class EpisodeDbAdapter extends AbstractDbAdapter {
	
	public static final String TABLE_NAME = "t_episode";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_EPISODE = "episode";
	public static final String COLUMN_WATCHED = "watched";
	public static final String COLUMN_SEASON_FK = "season_fk";
	
	private String[] allColumns = {
			COLUMN_ID, COLUMN_EPISODE, COLUMN_WATCHED
	};

	
	public static final String EPISODE_TABLE_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EPISODE
			+ " INTEGER, " + COLUMN_WATCHED + " INTEGER ," + COLUMN_SEASON_FK + " INTEGER ,"
			+ "FOREIGN KEY(" + COLUMN_SEASON_FK + ") REFERENCES " + SeasonDbAdapter.TABLE_NAME + "(" + SeasonDbAdapter.COLUMN_ID + ")"
			+ ");";
	

	public EpisodeDbAdapter(Context context) {
		super(context);
	}
	
	public void createEpisode(Episode episode, long seasonId) {
		ContentValues values = new ContentValues();
	    values.put(COLUMN_EPISODE, episode.getEpisode());
	    values.put(COLUMN_WATCHED, episode.getWatched() ? 1 : 0);
	    values.put(COLUMN_SEASON_FK, seasonId);
	    
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		Cursor cursor = queryBuilder.query(db, null , COLUMN_EPISODE+" = ? AND " + COLUMN_SEASON_FK + " = ?", 
				new String[] {episode.getEpisode().toString(), "" + seasonId}, null, null, null);
		
		if(! (cursor.moveToFirst()) || cursor.getCount() == 0) {
		    db.insert(TABLE_NAME, null,
		    		values);
		} else {
			boolean watched = false;
			if(cursor.getInt(2) == 1) {
				watched = true;
			}
			if(episode.getWatched() != watched) {
				updateEpisode(values, cursor.getInt(0));
			}
		}
		cursor.close();
	}
	
	private void updateEpisode(ContentValues values, long episodeId) {
		LOGGER.info("STARTED UPDATE EPISODE: " + episodeId);
		String whereClause = COLUMN_ID + " = ?";
		db.update(TABLE_NAME, values, whereClause, new String[] {"" + episodeId});
		LOGGER.info("FINISHED UPDATE EPISODE: " + episodeId);
	}

	public List<Episode> getEpisodesFromSeason(long season) {
		List<Episode> episodes = new ArrayList<Episode>();
		String whereSelection = "season_fk = ?";
		Cursor cursor = db.query(TABLE_NAME, allColumns, whereSelection, new String[] {"" + season}, null, null,
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Episode episode = new Episode();
			episode.setId(cursor.getInt(0));
			episode.setEpisode(cursor.getInt(1));
			Integer watched = cursor.getInt(2);
			if(watched == 1) {
				episode.setWatched(true);
			} else {
				episode.setWatched(false);
			}

			episodes.add(episode);
			cursor.moveToNext();
		}
		cursor.close();
		close();
		
		return episodes;
	}

}
