package de.showaddict.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.showaddict.R;
import de.showaddict.entity.MockShow;
import de.showaddict.entity.NextEpisode;

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
		TextView nextEpisodeNumberView;
		TextView nextEpisodeTitleView;
		TextView nextEpisodeString;
		TextView nextEpisodeDate;
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
			holder.nextEpisodeNumberView = (TextView) convertView.findViewById(R.id.nextEpisodeNumber);
			holder.nextEpisodeTitleView = (TextView) convertView.findViewById(R.id.nextEpisodeTitle);
			holder.nextEpisodeString = (TextView) convertView.findViewById(R.id.nextEpisodeString);
			holder.nextEpisodeDate = (TextView) convertView.findViewById(R.id.nextEpisodeDate);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.titleView.setText(mockShows.get(position).getTitle());
		if(mockShows.get(position).getNextEpisode() != null) {
			holder.nextEpisodeNumberView.setText(prepareEpisodeNumber(mockShows.get(position).getNextEpisode()));
			holder.nextEpisodeTitleView.setText(mockShows.get(position).getNextEpisode().getTitle());
			holder.nextEpisodeString.setText(R.string.show_row_nextEpisode);
			//TODO check locale
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			String date = sdf.format(new Date(mockShows.get(position).getNextEpisode().getFirst_aired() * 1000));
			holder.nextEpisodeDate.setText(date);
		} else {
			holder.nextEpisodeNumberView.setText(R.string.no_more_episode);
			holder.nextEpisodeTitleView.setText("");
			holder.nextEpisodeString.setText("");
			holder.nextEpisodeDate.setText("");
		}
		
		return convertView;
	}

	/**
	 * Baut einen String auf zb.: S01E01
	 * 
	 * @param nextEpisode
	 * @return
	 */
	private CharSequence prepareEpisodeNumber(NextEpisode nextEpisode) {
		Integer seasonNumber = nextEpisode.getSeason();
		Integer episodeNumber = nextEpisode.getNum();
		String seasonAndEpisode = "S";
		if(seasonNumber < 10) {
			seasonAndEpisode += 0;
		}
		seasonAndEpisode += seasonNumber + "E";
		if(episodeNumber < 10) {
			seasonAndEpisode += 0;
		}
		seasonAndEpisode += episodeNumber;
		return seasonAndEpisode;
	}

}
