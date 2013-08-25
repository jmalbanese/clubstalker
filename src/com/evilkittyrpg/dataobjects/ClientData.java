package com.evilkittyrpg.dataobjects;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.utils.Debug;
import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class ClientData implements LocationListener
{
	private static String TAG = "ClientData";
	
	
	private String email_addr;
	private String password;
	private String name_legal;
	private String name_stage;
	private String phone_contact;
	private String comments;
	private String type;
	private Boolean dirty;
	private Boolean isRegistered;
	private String uuid;
	private String sex;
	private String enableTwitter;
	private String twitter;
	private String enablePhone;
	private String privacy;
	
	
	private LocationManager locationManager;
	private Location lastLocation;	// last good known location

	public ClientData()
	{
		lastLocation = null;
		uuid = null;
		locationManager = (LocationManager) AppCommon.getInstance().application.getSystemService(Context.LOCATION_SERVICE);
		lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		updateFromPrefs();
		dirty = false;			// only called from start of app - 
	}
	

	public void updateFromPrefs()
	{
		setIsRegisterd(AppCommon.getInstance().getSharedPreferences().getBoolean(Constants.PREFERENCE_REGISTER_ACCEPTED, false));

		setEmail_addr(AppCommon.getInstance().getSharedPreferences().getString(Constants.REGISTER_EMAIL_ADDRESS, Constants.REGISTER_EMAIL_ADDRESS_DEFAULT));
		setPassword(AppCommon.getInstance().getSharedPreferences().getString(Constants.REGISTER_PASSWORD, Constants.REGISTER_PASSWORD_DEFAULT));
		setType(AppCommon.getInstance().getSharedPreferences().getString(Constants.REGISTER_TYPE, Constants.REGISTER_TYPE_DEFAULT));
		setName_legal(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_NAME_LEGAL, Constants.CLIENT_NAME_LEGAL_DEFAULT));
		setName_stage(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_NAME_STAGE, Constants.CLIENT_NAME_STAGE_DEFAULT));
		setComments(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_COMMENT, Constants.CLIENT_COMMENTS_DEFAULT));
		setPhoneContact(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_PHONE_CONTACT, Constants.CLIENT_PHONE_CONTACT_DEFAULT));
		setUuid(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_UUID, Constants.CLIENT_UUID_DEFAULT));
		setSex(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_SEX, Constants.CLIENT_SEX_MALE));
		setTwitter(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_TWITTER, Constants.REGISTER_DEFAULT_TWITTER));
		setPrivacy(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_PRIVACY, Constants.CLIENT_PRIVACY_FREEZE_OUT));

		setEnableTwitter(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_ENABLE_TWITTER, Constants.DISABLED));
		setEnablePhone(AppCommon.getInstance().getSharedPreferences().getString(Constants.CLIENT_ENABLE_PHONE, Constants.DISABLED));

		dirty = true;
	}

	public GeoPoint getGeoPoint()
	{
		if( lastLocation == null )
		{
			return null; 
		}
			
		return( (new GeoPoint( (int) (lastLocation.getLatitude() * 1E6), (int) (lastLocation.getLongitude() * 1E6))) );
	}
////// location stuff starts here

	/**
	 * Used by on resume to start the location manager, to save battery
	 */
	
	public void startLocationManager()
	{
		int updateMinTime = Constants.CLIENT_DATA_UPDATE_MINTIME;
		
		if( AppCommon.getInstance().getConfiguration()!= null)	// config has loaded
		{
			updateMinTime = AppCommon.getInstance().getConfiguration().getLocationUpdateMinTime();
			
		}
//		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, updateMinTime, Constants.CLIENT_DATA_UPDATE_RANGE, this);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, updateMinTime, Constants.CLIENT_DATA_UPDATE_RANGE, this);
	}
	
	/**
	 * Stop location manager on Pause
	 */
	public void stopLocationManager()
	{
		if(locationManager != null)
		{
			locationManager.removeUpdates(this);
		}
	}

	@Override
	public void onLocationChanged(Location _location)
	{
		if(Debug.LOG)
		{
			Log.d(TAG, "Lat " + ((Double) _location.getLatitude()).toString() + " Lon " + ((Double)_location.getLongitude()).toString() );
		}
		
		if( isBetterLocation(_location, this.lastLocation )== true)	// we think this new one is better
		{
			lastLocation = _location;
			dirty = true;
		}
	}

	private Boolean isBetterLocation(Location latestLocation, Location lastLocation )
	{
		long timeDelta;
		
		if( lastLocation == null) // no location, use this one
		{ 
			return(true);
		}
		timeDelta =  latestLocation.getTime() - lastLocation.getTime();
		
		if( timeDelta < Constants.UPDATE_DELTA ) // if less than 2 minutes diff, ignore
		{
//			Log.d( TAG, "isBetterLocation reports FALSE : time since last update : " + timeDelta );
			return(true);  // debugjma
		}
		return(true);
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		// TODO Auto-generated method stub
	}
	
	public void setEnableTwitter(String _v)
	{
		enableTwitter = _v;
	}
	
	public String getEnableTwitter()
	{
		return enableTwitter;
	}

	public void setEnablePhone(String _v)
	{
		enablePhone = _v; 
	}
	
	public String getEnablePhone()
	{
		return enablePhone;
	}
	private void setIsRegisterd(Boolean _v)
	{
		isRegistered = _v;
	}
	
	public Boolean getIsRegistered()
	{
		return(isRegistered);
	}
	
	public synchronized Double getLat()
	{
		if( this.lastLocation != null)
		{
			return( lastLocation.getLatitude());
		}
		return(0.0);
	}
	
	public synchronized Double getLon()
	{
		if( this.lastLocation != null)
		{
			return( lastLocation.getLongitude());
		}
		return( 0.0);
	}
	
	
	public synchronized String getUuid()
	{
		return(uuid);
	}
	
	public synchronized void setUuid( String v)
	{
		if( v.equals(uuid))
		{	return;		}
		else
		{
			uuid = v;
			dirty = true;
		}
	}

	public synchronized void setPhoneContact( String v)
	{
			phone_contact = v;
	}
	
	public synchronized String getPhoneContact()
	{
		return(phone_contact);
	}
	
	private synchronized void setPassword( String v)
	{
		password = v;
	}


	private synchronized void setType( String v)
	{
		type = v;
		dirty = true;
	}

	private synchronized void setSex( String v)
	{
		if( v.equals(sex))
		{ return; }

		sex = v;
		dirty = true;
	}

	public synchronized String getSex()
	{
		return(sex);
	}
	
	private synchronized void setTwitter( String v)
	{
		if( v.equals(twitter))
		{ return; }

		twitter = v;
		dirty = true;
	}

	public synchronized String getTwitter()
	{
		return(twitter);
	}

	private synchronized void setPrivacy( String v )
	{
		if( v.equals(privacy) )
		{ return; }

		privacy = v;
		dirty = true;
	}

	public synchronized String getPrivacy()
	{
		return(privacy);
	}
	
	/**
	 * @return the email_addr
	 */
	public synchronized String getEmail_addr()
	{
		return email_addr;
	}

	/**
	 * @param email_addr the email_addr to set
	 */
	public synchronized void setEmail_addr(String email_addr)
	{
		this.email_addr = email_addr;
	}

	public synchronized Boolean getDirty()
	{
		return dirty;
	}

	public synchronized void setDirty(Boolean _v)
	{
		dirty = _v;
	}
	/**
	 * @return the pwd
	 */
	public synchronized String getPassword()
	{
		return password;
	}

	/**
	 * @param pwd the password to set
	 */
	public synchronized void setPwd(String _password)
	{
		this.password = _password;
	}

	/**
	 * @return the name_legal
	 */
	public synchronized String getName_legal()
	{
		return name_legal;
	}


	/**
	 * @param name_legal the name_legal to set
	 */
	public synchronized void setName_legal(String name_legal)
	{
		this.name_legal = name_legal;
	}


	/**
	 * @return the name_stage
	 */
	public synchronized String getName_stage()
	{
		return name_stage;
	}


	/**
	 * @param name_stage the name_stage to set
	 */
	public synchronized void setName_stage(String name_stage)
	{
		this.name_stage = name_stage;
	}


	public synchronized String getLatAsString()
	{
		if( lastLocation != null)
		{
			return((Double)lastLocation.getLatitude()).toString();
		}
		return( ((Double) 0.0).toString());
	}
	

	public synchronized String getLonAsString()
	{
		if( lastLocation != null)
		{
			return( ((Double) lastLocation.getLongitude()).toString());			
		}
		return ((Double) 0.0 ).toString();
	}


	/**
	 * @return the comment
	 */
	public synchronized String getComments()
	{
		return comments;
	}


	/**
	 * @param comment the comment to set
	 */
	public synchronized void setComments(String v)
	{
		this.comments = v;
	}


	public synchronized String getType()
	{
		// TODO Auto-generated method stub
		return type;
	}

}
