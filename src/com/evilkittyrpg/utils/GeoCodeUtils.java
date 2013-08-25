package com.evilkittyrpg.utils;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.evilkittyrpg.dataobjects.Entity;
import com.google.android.maps.GeoPoint;

public class GeoCodeUtils
{
	private Geocoder geoCoder;
	
	public GeoCodeUtils(Context context)
	{
		geoCoder = new Geocoder(context);
	}

	private GeoPoint getGeoPoint( String locationName)
	{
		List<Address> list = null;
		GeoPoint geopt = new GeoPoint( 0,0);
		try
		{
			list = geoCoder.getFromLocationName(locationName, 5);
		} catch (IOException e)
			{
					e.printStackTrace();
			}
			
		if( list != null && list.size()> 0)
		{
			int lat = (int) list.get(0).getLatitude();
			int lon = (int) list.get(0).getLongitude();
			geopt = new GeoPoint( lat, lon);
		}
		return(geopt);
	}
	
	public void aquireLatLongFromAddress(Entity entity)
	{
		// a lat long override an text address, we assume if they want that precision
		// if we do mobile hookups we will have to use the lat long as posted by the
		// bait mobile to the server

		if( entity.getLat() + entity.getLon() == 0.0) // we have no location
		{
			String address = TextUtils.formGeoAddress(entity);
			GeoPoint geop = getGeoPoint(address);
			entity.setLat(geop.getLatitudeE6());
			entity.setLon(geop.getLongitudeE6());
		}
	}
}
