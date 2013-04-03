package de.showaddict.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.showaddict.R;
import de.showaddict.entity.MockShow;
import de.showaddict.entity.Show;

public class ShowListAdapter extends ArrayAdapter<MockShow> {
	
	private Context context;
	
	private List<MockShow> mockShows;
	
	public ShowListAdapter(Context context, List<MockShow> mockShows) {
		super(context, R.layout.show_row, mockShows);
		this.context = context;
		this.mockShows = mockShows;
	}
	
	static class ViewHolder {
		TextView titleView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
	
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.show_row, null);
			holder = new ViewHolder();
			//get row elements
			holder.titleView = (TextView) convertView.findViewById(R.id.show_title);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.titleView.setText(mockShows.get(position).getTitle());
		
		return convertView;
	}

}
