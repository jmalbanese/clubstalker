package com.evilkittyrpg.dataManagers;

import com.evilkittyrpg.constants.Constants;

import android.content.Context;
import android.content.SharedPreferences;

public class EscortPreferenceManager
{
	private	static	final String SHOW_SAT			= "showSat";			// nz = show satellite
	private	static	final String SHOW_GPS_LOC		= "showGps";			// nz = show gps
	private	static	final String SHOW_TRAFFIC		= "showTraffic";		// nz = show traffic
	private static	final String PREF_FILE 			= "evilkittyrpgPrefs";
	
	private static  final String LOCATION_CITY  	= "locationCity";		// string city
	
	private SharedPreferences settings;
	
	public EscortPreferenceManager(Context context )
	{
	    settings = context.getSharedPreferences(PREF_FILE, 0);
	}

	public String  getCity()
	{
		String val = settings.getString(LOCATION_CITY, Constants.CITY_SELECTOR_WILDCARD);
		return val;
	}
	
	public boolean isCity(String city)
	{
		String val = settings.getString(LOCATION_CITY, Constants.CITY_SELECTOR_WILDCARD);
		if( city.equals(val))
		{
			return true; 
		}
		return false;
	}

	public void  setCity(String city)
	{
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(LOCATION_CITY, city);
		editor.commit();
	}

	public boolean getSat()
	{
       boolean val = settings.getBoolean(SHOW_SAT, false);
       return val;
	}

	public void setSat( boolean val )
	{
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(SHOW_SAT, val);
		editor.commit();
	}
		
	public boolean getGpsLoc()
	{
		boolean val = settings.getBoolean(SHOW_GPS_LOC, false);
		return val;
	}

	public void setGpsLoc( boolean val )
	{
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(SHOW_GPS_LOC, val);
		editor.commit();
	}

	public boolean getTraffic()
	{
		boolean val = settings.getBoolean(SHOW_TRAFFIC, false);
		return val;
	}
	
	public void setTraffic( boolean val )
	{
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(SHOW_TRAFFIC, val);
		editor.commit();
	}
}
