package de.showaddict.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.showaddict.R;
import de.showaddict.activities.ListActivity;
import de.showaddict.adapter.ShowListAdapter;
import de.showaddict.entity.MockShow;
import de.showaddict.entity.Show;
import de.showaddict.persistence.Dao;

public class ShowListFragment extends Fragment {

    public ShowListFragment() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showlist, container, false);
        Bundle bundle = getArguments();
        
        List<MockShow> mockShows = null;
        
        //this action is only necessary on initial loading
        if(bundle.containsKey(ListActivity.INITIAL_BUNDLE_ARG) && bundle.getBoolean("initial")) {
        	int i = 0;
        	String showKey = "Show";
        	List<Show> shows = new ArrayList<Show>();
        	while(bundle.containsKey(showKey + i)) {
        		Show show = (Show) bundle.getSerializable(showKey + i);
        		shows.add(show);
        		++i;
        	}
        	mockShows = transformShowToMockShow(shows);
        	
        } else {
        	Dao dao = new Dao(getActivity());
        	mockShows = dao.getAllMockShows();
        }
        
    	
    	ShowListAdapter adapter = new ShowListAdapter(getActivity().getApplicationContext(), mockShows);
    	ListView listView = (ListView) rootView.findViewById(R.id.showList);
    	listView.setAdapter(adapter);
        
        return rootView;
    }
    
    /**
     * method to prepare mock shows to display in the show list adapter
     * @param shows
     * @return
     */
    private List<MockShow> transformShowToMockShow(List<Show> shows) {
    	List<MockShow> mockShows = new ArrayList<MockShow>();
    	for(Show show : shows) {
    		MockShow mockShow = new MockShow();
    		mockShow.setBannerUri(show.getBannerUri());
    		mockShow.setTitle(show.getShowInfo().getTitle());
    		mockShow.setNextEpisode(show.getNextEpisode());
    		mockShows.add(mockShow);
    	}
    	return mockShows;
    }
}
