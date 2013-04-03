package de.showaddict.activities;

import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import de.showaddict.R;
import de.showaddict.entity.Show;
import de.showaddict.entity.ShowInfo;
import de.showaddict.fragments.LoginFragment;
import de.showaddict.fragments.MovieListFragment;
import de.showaddict.fragments.ShowListFragment;
import de.showaddict.persistence.Dao;
import de.showaddict.trakt.TraktFunctions;

public class ListActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static boolean isLoggedIn; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[] {
                                getString(R.string.dropdown_series),
                                getString(R.string.dropdown_movies),
                        }),
                this);
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
        .add(R.id.container, loginFragment).commit();
        isLoggedIn = false;
    }
    
    /**
     * wird in fragment_login durch login aufgerufen
     * 
     * @param view
     */
    public void downloadShows(View view) {
    	TraktFunctionsService tfs = new TraktFunctionsService(getApplicationContext());
    	tfs.execute();
    }
    
    public void displayShows() {
    	isLoggedIn = true;
    	onNavigationItemSelected(0, 0);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }
    
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
    	Fragment fragment;
    	if(isLoggedIn) {
	    	if(position == 0) {
	    		fragment = new ShowListFragment();
	    	}
	    	else {
	    		fragment = new MovieListFragment();
	    	}
	    	
	    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	        transaction.replace(R.id.container, fragment);
	    	transaction.addToBackStack(null);
	    	transaction.commit();
	        Bundle args = new Bundle();
	        args.putInt("position", position);
	        fragment.setArguments(args);
	        return true;
    	}
    	return false;
    }
    
    private class TraktFunctionsService extends AsyncTask<Object, Object, List<Show>> {
    	List<Show> shows;
    	Context context;
    	
    	public TraktFunctionsService(Context context) {
    		this.context = context;
    	}

    	@Override
    	protected List<Show> doInBackground(Object... arg0) {
//    		shows = TraktFunctions.getAllShowsFromLibrary();
    		shows = TraktFunctions.getAllWatchedShowsFromLibrary();
    		
    		Dao dao = new Dao(context);
    		dao.createShows(shows);
    		return shows;
    	}
    	
    	protected void onPostExecute(List<Show> shows) {
//            showDialog("Downloaded " + result + " bytes");
    		displayShows();
        }
    }
}
