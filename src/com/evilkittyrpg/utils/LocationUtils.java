package com.evilkittyrpg.utils;

import com.evilkittyrpg.dataobjects.Patron;
import com.evilkittyrpg.dataobjects.Venue;

public class LocationUtils
{
	static final String TAG = "LocationUtils";
	
	// radius of earth in meters
	private static double unit = 6371000.0; //  3444 miles, 6371 kilometers 
	public LocationUtils()
	{
	}

	/**
	 * determine if a venue is withing rage (meters) of a patron
	 * @param venue
	 * @param patron
	 * @param range
	 * @return t/f
	 *
	 *     $dist = sin($lat_from) * sin($lat_to) + cos($lat_from) 
	 *     * cos($lat_to)* cos($long_from - $long_to);
	 */ 
	 
	public static boolean inRadius( Venue from, Patron to, Double hitRadius)
	{	
		if(from.getLat() == 0.0) return(false);
		if(from.getLon() == 0.0) return(false);
		if(to.getLat() == 0.0) return(false);
		if(to.getLon() == 0.0) return(false);
		
		double dist;
		 
		dist =   Math.sin( Math.toRadians(from.getLat()))	* Math.sin( Math.toRadians(to.getLat())) + Math.cos(Math.toRadians(from.getLat()))
		 		* Math.cos(Math.toRadians(to.getLat())) * Math.cos( Math.toRadians(from.getLon()) - Math.toRadians(to.getLon()) ) ;

//		dist =   Math.sin( Math.toRadians(fromLat))	* Math.sin( Math.toRadians(toLat)) + Math.cos(Math.toRadians(fromLat))
// 		* Math.cos(Math.toRadians(toLat)) * Math.cos( Math.toRadians(fromLon) - Math.toRadians(toLon) ) ;
		
		dist = unit * Math.acos(dist);
		
//		Log.d(TAG, "dist meters between " + from.getName_stage() + " and " + to.getName_stage()+ " " + dist );
		
		 if( dist < hitRadius)
		 {
			 return true;
		 }
		 return false;
	}
}
