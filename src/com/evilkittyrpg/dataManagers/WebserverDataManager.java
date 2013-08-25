package com.evilkittyrpg.dataManagers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;
import android.util.Log;

import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.Configuration;
import com.evilkittyrpg.dataobjects.Entities;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.dataobjects.MediaElements;
import com.evilkittyrpg.dataobjects.Ratings;
import com.evilkittyrpg.dataobjects.Venues;
import com.evilkittyrpg.interfaces.WebserverDataManagerListener;
import com.evilkittyrpg.utils.Debug;

/**
 * @author jmalbanese
 * 
 */
public class WebserverDataManager
{
	private String TAG = "WebserverDataManager";
	
	private WebserverDataManagerListener listener;
    private AppCommon appcommonInstance;
    private String reqActiveClients;
    private String reqActiveClientsMediaElements;
    private String reqActiveClientsRatings;
    private String locationCity;

	public WebserverDataManager(WebserverDataManagerListener _eml)
	{
		listener = _eml;   // _eml implements webserverDataManagerError and newData methods
    	appcommonInstance = AppCommon.getInstance();  // so we can look at the data.
	}

	private void resolveSelectors()
	{
		locationCity = appcommonInstance.getLocationCity();
		try
		{
			locationCity = URLEncoder.encode( locationCity, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		reqActiveClients = getConfiguraion().getServer_http() + Constants.GET_ACTIVE_CLUBS.replaceAll(Constants.CITY_SELECTOR, locationCity);
		reqActiveClientsMediaElements = getConfiguraion().getServer_http() + Constants.GET_ACTIVE_CLUBS_MEDIA_ELEMENTS.replaceAll(Constants.CITY_SELECTOR, locationCity);
		reqActiveClientsRatings = getConfiguraion().getServer_http() + Constants.GET_ACTIVE_CLUBS_RATINGS.replaceAll(Constants.CITY_SELECTOR, locationCity);
	}
	
	/**
	 * Load data. pull in the data for entities and their locations and their
	 * etc etc... throws exception on bad read
	 */

	public void loadData()
	{
		try
		{
			setConfiguraion( new Configuration(Constants.CONFIGURATION_URL) );   // create a new configuration object in Appcommon
			this.getConfiguraion().read();
			

			resolveSelectors();
			setVenues(new Venues(reqActiveClients));
			setMediaElements(new MediaElements(reqActiveClientsMediaElements));
			setRatings(new Ratings(reqActiveClientsRatings));

			this.getMediaElements().clear();
			this.getMediaElements().read();
			
//			this.getRatings().clear();
//			this.getRatings().read();

// order is important here, entities extract their media and ratings after construction;
			
			this.getEntities().clear();
			this.getEntities().read();
			
			if( this.getEntities().size() == 0)
			{
				if(Debug.LOG)
				{
					Log.d(TAG, "Error No entities returned");
				}
			}
			
			for( Entity e  : AppCommon.getInstance().getVenues())
			{
				e.resolveMediaAndRatings();
			}

			listener.newData(); // tell the boss it's ok
		}

		catch (Exception e)
		{
			if(Debug.LOG)
			{
				Log.d("WebserverDataManager", e.toString());
			}
			this.getEntities().clear();
			this.getMediaElements().clear();
//			this.getRatings().clear();
			Context context = (Context)listener;
			listener.webserverDataManagerError( context.getString(R.string.SERVER_DOWN));
		}
	}

	public boolean isDataValid()
	{
		if (getEntities() != null )
		{
			return (true);
		}
		return (false);
	}

	private Configuration getConfiguraion()
	{
		return AppCommon.getInstance().getConfiguration();
	}

	public void setConfiguraion(Configuration c)
	{
		AppCommon.getInstance().setConfiguration(c);
	}

	public Entities getEntities()
	{
		return AppCommon.getInstance().getVenues();
	}

	private void setVenues(Venues venues)
	{
		AppCommon.getInstance().setVenues(venues);
	}
	
	public MediaElements getMediaElements()
	{
		return AppCommon.getInstance().getVenueMediaElements();
	}

	public void setMediaElements(MediaElements mediaElements)
	{
		AppCommon.getInstance().setVenueMediaElements(mediaElements);
	}

	public Ratings getRatings()
	{
		return AppCommon.getInstance().getRatings();
	}

	public void setRatings(Ratings ratings)
	{
		AppCommon.getInstance().setRatings(ratings);
	}

}
