package de.showaddict.activities;

import java.util.List;
import java.util.logging.Logger;

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
import de.showaddict.fragments.LoginFragment;
import de.showaddict.fragments.MovieListFragment;
import de.showaddict.fragments.ShowListFragment;
import de.showaddict.persistence.Dao;
import de.showaddict.trakt.TraktFunctions;

public class ListActivity extends FragmentActivity implements ActionBar.OnNavigationListener {
	
	public static Logger LOGGER = Logger.getLogger(ListActivity.class.getName());
	
	public static final String INITIAL_BUNDLE_ARG = "initial";
	
	/**
	 * only stored for initial loading
	 */
	private List<Show> shows;
	private Boolean initialLoading = false;

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
        initialLoading = true;
    }
    
    /**
     * wird in fragment_login durch login aufgerufen
     * 
     * @param view
     */
    public void downloadShows(View view) {
    	LOGGER.info("LOGIN BUTTON PRESSED");
    	TraktFunctionsService tfs = new TraktFunctionsService(getApplicationContext());
    	tfs.execute();
    }
    
    public void displayShows(List<Show> shows) {
    	this.shows = shows;
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
    	//TODO make sure you cant go back to login fragment with back key
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
	        //TODO who should use this?
	        args.putInt("position", position);
	        if(initialLoading && fragment instanceof ShowListFragment && shows != null) {
	        	args.putBoolean(INITIAL_BUNDLE_ARG, true);
	        	int i = 0;
	        	for(Show show : shows) {
	        		args.putSerializable("Show" + i, show);
	        		++i;
	        	}
	        }
	        fragment.setArguments(args);
	        return true;
    	}
    	return false;
    }
    
    private class TraktFunctionsService extends AsyncTask<Object, Object, List<Show>> {
    	Context context;
    	
    	public TraktFunctionsService(Context context) {
    		this.context = context;
    	}

    	@Override
    	protected List<Show> doInBackground(Object... arg0) {
    		LOGGER.info("STARTED DOWNLOADING SHOWS");
    		List<Show> shows = TraktFunctions.getAllWatchedShowsFromLibrary(context);
    		LOGGER.info("FINISHED DOWNLOADING SHOWS");
    		DatabaseService dbService = new DatabaseService(context);
    		dbService.execute(shows);
    		return shows;
    	}
    	
		protected void onPostExecute(List<Show> shows) {
			//TODO there has to be another way to push the shows to the list activity
    		displayShows(shows);
        }
    }
    
    private class DatabaseService extends AsyncTask<List<Show>, Void, Boolean> {
    	Context context;
    	
    	public DatabaseService(Context context) {
    		this.context = context;
    	}

    	@Override
    	protected Boolean doInBackground(List<Show>... arg0) {
    		LOGGER.info("STARTED STORE SHOWS TO DATABASE");
    		Dao dao = new Dao(context);
    		dao.createShows(arg0[0]);
    		LOGGER.info("FINISHED STORE SHOWS TO DATABASE");
    		return true;
    	}
    	
		protected void onPostExecute(Boolean stored) {
			//TODO is anything to do here?
        }
    }
}
