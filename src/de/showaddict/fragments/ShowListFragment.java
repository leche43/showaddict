package de.showaddict.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.showaddict.R;
import de.showaddict.adapter.ShowListAdapter;
import de.showaddict.entity.MockShow;
import de.showaddict.persistence.Dao;

public class ShowListFragment extends Fragment {

    public ShowListFragment() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showlist, container, false);
        
        Dao dao = new Dao(getActivity());
        List<MockShow> mockShows = dao.getAllMockShows();
    	
    	ShowListAdapter adapter = new ShowListAdapter(getActivity().getApplicationContext(), mockShows);
    	ListView listView = (ListView) rootView.findViewById(R.id.showList);
    	listView.setAdapter(adapter);
        
        return rootView;
    }
}
