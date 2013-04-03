package de.showaddict.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.showaddict.R;
import de.showaddict.adapter.ShowListAdapter;
import de.showaddict.entity.Show;
import de.showaddict.entity.ShowInfo;
import de.showaddict.persistence.Dao;
import de.showaddict.persistence.ShowDatabaseHelper;

public class ShowListFragment extends Fragment {

    public ShowListFragment() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_showlist, container, false);
        
        Dao dao = new Dao(getActivity());
        List<Show> shows = dao.getAllShows();
    	
    	ShowListAdapter adapter = new ShowListAdapter(getActivity().getApplicationContext(), shows);
    	ListView listView = (ListView) rootView.findViewById(R.id.showList);
    	listView.setAdapter(adapter);
        
        return rootView;
    }
}
