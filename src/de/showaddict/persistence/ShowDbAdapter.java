package de.showaddict.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import de.showaddict.entity.Show;

public class ShowDbAdapter extends AbstractDbAdapter {
	
	public static final String TABLE_NAME = "t_show";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	

	public static final String SHOW_TABLE_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_TITLE + " TEXT "
			+");";
	
	private String[] allColumns = {
			COLUMN_ID, COLUMN_TITLE
	};

	public ShowDbAdapter(Context context) {
		super(context);
	}
	
	public long createShow(Show show) {
		ContentValues values = new ContentValues();
	    values.put(COLUMN_TITLE, show.getTitle());

	    
	    long insertId = db.insert(TABLE_NAME, null,
	        values);
	    
	    return insertId;
		
	}
	
	public Show getShow(long showId) {
		String whereSelection = "id = ?";
		Cursor cursor = db.query(TABLE_NAME, allColumns, whereSelection, new String[] {"" + showId}, null, null,
				null);

		cursor.moveToFirst();
		
		Show show = new Show();
		show.setId(cursor.getInt(0));
		show.setTitle(cursor.getString(0));

			
		cursor.close();
		close();
		return show;
	}
	
	public List<Show> getAllShows() {
		List<Show> shows = new ArrayList<Show>();
		Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null,
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Show show = new Show();
			show.setId(cursor.getInt(0));


			shows.add(show);
			cursor.moveToNext();
		}
		cursor.close();
		close();
		
		return shows;
	}
	
}
