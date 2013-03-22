package de.showaddict.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.showaddict.R;
import de.showaddict.entity.Show;

public class ShowListAdapter extends ArrayAdapter<Show> {
	
	private Context context;
	
	private List<Show> shows;
	
	public ShowListAdapter(Context context, List<Show> shows) {
		super(context, R.layout.show_row, shows);
		this.context = context;
		this.shows = shows;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.show_row, null);
		
		//get row elements
		TextView titleView = (TextView) rowView.findViewById(R.id.show_title);
		
		titleView.setText(shows.get(position).getTitle());
		
		return rowView;
	}

}
