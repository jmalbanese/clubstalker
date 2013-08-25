package com.evilkittyrpg.runnables;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.util.Log;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.Configuration;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.dataobjects.MediaElement;
import com.evilkittyrpg.dataobjects.Patrons;
import com.evilkittyrpg.dataobjects.MediaElements;
import com.evilkittyrpg.interfaces.DataListener;
import com.evilkittyrpg.utils.Debug;

public class PatronUpdateRunnable implements Runnable
{

	private final String  TAG = "PatronUpdateRunnable";
    private AppCommon appcommonInstance;
    private DataListener listener;
    private String reqEntitiesOnline;				// we want these guys
    private String reqEntitiesOnlineMediaElements;	// and their associated graphics if any
    private String locationCity;
    
    private Patrons patrons;
    private MediaElements patronMediaElements;

    public PatronUpdateRunnable(DataListener _listener)  // listener implements onData, onError
    {
    	listener = _listener;
    	appcommonInstance = AppCommon.getInstance();  // so we can look at the data.
    }

	@Override
	public void run()
	{
		resolveSelectors();  // get the selector for patrons into the patrons object
		patrons = new Patrons(reqEntitiesOnline); // tell Patrons where they get their data from set up parser etc
		patronMediaElements = new MediaElements(reqEntitiesOnlineMediaElements);

		try
		{
// order is important here, entities extract their media and ratings after construction;
	
			if(Debug.LOG)
			{
				Log.d(TAG, "Called from scheduler");
			}
			
			patrons.read();							// tell reader to get active online patrons
			appcommonInstance.setPatrons(patrons);	// post results

			patronMediaElements.read();										// get their associated media elements
			appcommonInstance.setPatronMediaElements(patronMediaElements); 	// post it to common data pool

			for( Entity e  : AppCommon.getInstance().getPatrons())
			{
				e.resolveMediaAndRatings();	// get the media elements for this entity
			}

			// now test to see if any cached files are out of date

			TimeZone tz = TimeZone.getDefault();	
			Calendar cal = new GregorianCalendar(tz);
			cal.set(Calendar.DST_OFFSET, 0);
			
			long offsetFromUtc = tz.getOffset( new Date().getTime());
			
//			Log.d(TAG, "TZ " + tz.toString() + " offset hours this device minutes" + offsetFromUtc / 60000);

			for( MediaElement mel : AppCommon.getInstance().getPatronMediaElements())
			{
				Date arf = mel.getCreation_date();
//				Log.d(TAG, "Raw incoming date from db " + arf.toString());
				arf = new Date( arf.getTime() + offsetFromUtc + 60000L); // sometimes the times are off by 3 or 5 seconds add 60 sec bias
//				Log.d(TAG, "Adjusted incoming date from db " + arf.toString());

				if( mel.getCreation_date() != new Date(0) )	// looks like a real picture, normalize date for dst
				{
					mel.setCreation_date(arf);
					AppCommon.getInstance().getImageLoader().updateCache( mel );	// deletes from all caches if date is newer than file ( if present ) in filecache
				}
			}
//			AppCommon.getInstance().getImageLoader().clearMemoryCache();	// updateCache takes care of this as well

			AppCommon.getInstance().getVenues().getPatronsNear();
			
			listener.onData();			// call implemented method in listener to announce the aqu of data
		}

		catch (Exception e)
		{
			listener.onError( e.getMessage());
		}
	}

    private void resolveSelectors()
	{
		locationCity = appcommonInstance.getLocationCity();
		try
		{
			locationCity = URLEncoder.encode( locationCity, "UTF-8" );
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		// build the url by resolving current location 
		reqEntitiesOnline = getConfiguration().getServer_http() + Constants.GET_ACTIVE_PATRONS.replaceAll(Constants.CITY_SELECTOR, locationCity);
		reqEntitiesOnlineMediaElements = getConfiguration().getServer_http() + Constants.GET_ACTIVE_PATRONS_MEDIA_ELEMENTS.replaceAll(Constants.CITY_SELECTOR, locationCity);
	}
	
    private Configuration getConfiguration()
    {
    	return( appcommonInstance.getConfiguration());
    }
}
