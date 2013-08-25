package com.evilkittyrpg.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.evilkittyrpg.constants.AppState;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.*;
import com.evilkittyrpg.runnables.UpdateUserRunnable;
import com.evilkittyrpg.utils.Debug;
import com.fedorvlasov.lazylist.ImageLoader;

public class AppCommon
{
	private static final String TAG = "AppCommonData";
	private static AppCommon instance;
	public ExecutorService execPool;
	public ScheduledExecutorService schedulerPool;
	public Application application;
	private ClientData clientData;					// updates to name, comments, location etc are here
	public UpdateUserRunnable updateUserRunnable; 	// posts cd contents to web if dirty
	private ScheduledFuture<?> userUpdateHandle;
	private ImageLoader imageLoader;
	private SharedPreferences preferences;
	private Configuration configuration;
	private int webShowCount;

	public static void initInstance(Application app)
	{
		if( instance == null)
		{
			instance = new AppCommon();
			instance.venues = new Venues();		// need empty lists for synchronization to lock to
			instance.patrons = new Patrons();  // same here
			instance.venueMediaElements = new MediaElements();
			instance.patronMediaElements = new MediaElements();
			instance.mapType = AppState.SHOW_MAP;
			instance.execPool = Executors.newCachedThreadPool();
			instance.schedulerPool = Executors.newScheduledThreadPool(5);
			instance.application = app;
			instance.preferences = app.getSharedPreferences(Constants.PREFERENCES_REGISTER, Activity.MODE_PRIVATE);
			instance.clientData = new ClientData();		// set up client data	
			instance.imageLoader = new ImageLoader(app.getApplicationContext());
			instance.configuration = null;
			instance.webShowCount = 0;	// number of times this instance has shown the web page
		}
	}
	
	public Context getAppContext()
	{
		return( instance.application.getApplicationContext());
	}
	
	public static AppCommon getInstance()
	{
		return instance;
	}
	
	// private hidden constructor
	private AppCommon()
	{
	}
	
	public int getWebShowCount()
	{
		return webShowCount;
	}
	
	public void setWebShowCount(int v)
	{
		webShowCount = v;
	}
	
	public Configuration getConfiguration()
	{
		return(configuration);
	}
	
	public void setConfiguration(Configuration c)
	{
		configuration  = c;
	}
	
	public SharedPreferences getSharedPreferences()
	{
		return preferences;
	}

	public ImageLoader getImageLoader()
	{
		return imageLoader;
	}

	public ClientData getClientData()
	{
		return clientData;
	}

//	public void setClientData( ClientData v)
//	{
//		clientData =  v;
//		clientData.startLocationManager();
//		return;
//	}

	private String locationCity;
	
	public String getLocationCity()
	{
		if( locationCity == null || locationCity.equals(""))
		{
			locationCity=Constants.CITY_SELECTOR_WILDCARD;
			
			if(Debug.LOG)
			{
				Log.d("Warning using default location ", locationCity.toString());
			}
		}
		return locationCity;
	}

	public void setLocationCity( String _locationCity)
	{
		locationCity = _locationCity;
	}

	// data is here
	private Venues venues; // these are the entities!

	public Venues getVenues()
	{
        if( venues == null)
        {
        	throw new RuntimeException("Entities not present");
        }
		return venues;
	}

	public void setVenues(Venues _venues)
	{
		synchronized( venues )
		{
			venues = _venues;
		}
	}
	
	// data is here
	private MediaElements venueMediaElements; // these are the entities!

	public MediaElements getVenueMediaElements()
	{
        if( venueMediaElements == null)
        {
        	throw new RuntimeException("Media elements not present");
        }
		return venueMediaElements;
	}

	public void setVenueMediaElements(MediaElements _venueMediaElements)
	{
		synchronized(venueMediaElements)
		{
			venueMediaElements = _venueMediaElements;
		}
	}

	
	// data is here
	private Ratings ratings; // these are the entities!

	public Ratings getRatings()
	{
		if( ratings == null)
		{
			throw new RuntimeException("Ratings not present");
		}
		return ratings;
	}

	public void setRatings(Ratings ratings)
	{
		this.ratings = ratings;
	}

	
	private Entities searchResults;

	public Entities getSearchResults()
	{
		if( searchResults == null)		// always return a valid pointer
		{
			searchResults = new Entities();
		}
		return searchResults;
	}

	public void setSearchResults(Entities searchResults)
	{
		this.searchResults = searchResults;
	}
	
	private AppState mapType;

	public AppState getMapType()
	{
		return mapType;
	}

	public void setMapType(AppState mapType)
	{
		this.mapType = mapType;
	}

/**
 * Patron data is here
 * we keep it separate for speed
 * this data is very dynamic and updated ever n seconds
 * we want to be able to update this with one syc'd
 * operation rather than do a scan on the Entities table
 * 
 */
	private Patrons patrons;
	
	public Patrons getPatrons()
	{
		if( patrons == null)
		{
			throw new RuntimeException("Online Now entities = null");
		}
		return patrons;
	}

	public void setPatrons(Patrons _patrons)
	{ 
		synchronized( _patrons )
		{
			patrons = _patrons;
		}
	}

	// data is here
	private MediaElements patronMediaElements; // these are the entities!

	public MediaElements getPatronMediaElements()
	{
        if( patronMediaElements == null)
        {
        	throw new RuntimeException(" patronMediaElements not present");
        }
		return patronMediaElements;
	}

	public void setPatronMediaElements(MediaElements _mediaElements)
	{
		synchronized( patronMediaElements)
		{
			patronMediaElements = _mediaElements;
		}
	}
	
	/**
	 * Start user update polling.
	 * This is called by AppMapActivity and PatronIndexActivty to 
	 * track users in real time.
	 * 
	 * To prevent overruns in case of delays, getUserUpdate is called
	 * at the completion of each loading of data
	 * 
	 */
	public void startUserUpdateClientUpdate()
	{
		updateUserRunnable = new UpdateUserRunnable();

		if(Debug.LOG)
		{	
			Log.d(TAG, "Starting userUpdateRunnable");
		}

		userUpdateHandle = this.schedulerPool.scheduleAtFixedRate
							(updateUserRunnable, Constants.UPDATE_DELAY, this.getConfiguration().getUserUpdateRate(), TimeUnit.MILLISECONDS);
	}
	
	// called only by AppTabActivity onPause
	
	public void stopUserUpdateClientUpdate()
	{
		if( updateUserRunnable == null)
		{
	       	throw new RuntimeException("user update runnable is null");
		}
		
		if(Debug.LOG)
		{
			Log.d(TAG, "Stopping userUpdateRunnable and clearing client data");
		}
		
		userUpdateHandle.cancel(false); //let it complete it's task
		clientData.stopLocationManager();
	}
	
}
