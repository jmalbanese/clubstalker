package com.evilkittyrpg.mapsupport.overlays;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.clubzoni.R;

import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.constants.MapViewConstants;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.utils.Debug;



/**
 * The Class OverlayIcons.
 */
public class OverlayIcons
{
	
	/** The context. */
	private Context context;
	
	/** The icon map. */
	static private Map<String, Drawable> iconMap;
	
	/**
	 * Instantiates a new overlay icons.
	 *
	 * @param _context the _context of the application
	 */
	public OverlayIcons( Context _context)
	{
		int value;
		context = _context;
		iconMap = new HashMap <String, Drawable>();

		// the silver girl is the found girl
		value = R.drawable.marker_escort;
		iconMap.put(Constants.ENTITY_TYPE_ESCORT, buildMarker(value));

		// the orange girl is the found girl
		value = R.drawable.marker_escort_found;
		iconMap.put(Constants.ENTITY_TYPE_ESCORT_FOUND, buildMarker(value));
		
		value = R.drawable.marker_club;
		iconMap.put(Constants.ENTITY_TYPE_CLUB, buildMarker(value));

		value = R.drawable.marker_club_found;
		iconMap.put(Constants.ENTITY_TYPE_CLUB_FOUND, buildMarker(value));

		value = R.drawable.marker_patron;
		iconMap.put(Constants.ENTITY_TYPE_PATRON, buildMarker(value));
		
		value = R.drawable.marker_patron_male;
		iconMap.put(Constants.ENTITY_TYPE_PATRON_MALE, buildMarker(value));

		value = R.drawable.marker_patron_female;
		iconMap.put(Constants.ENTITY_TYPE_PATRON_FEMALE, buildMarker(value));

		value = R.drawable.marker_patron_found;
		iconMap.put(Constants.ENTITY_TYPE_PATRON_FOUND, buildMarker(value));

		value = R.drawable.marker_massage;
		iconMap.put(Constants.ENTITY_TYPE_MASSAGE, buildMarker(value));

		value = R.drawable.marker_massage_found;
		iconMap.put(Constants.ENTITY_TYPE_MASSAGE_FOUND, buildMarker(value));

		value = R.drawable.foundlocation;
		iconMap.put(MapViewConstants.FOUND_LOCATION, buildMarker(value));
		
		value = R.drawable.starlocation;
		iconMap.put(MapViewConstants.DEFAULT, buildMarker(value));

	}
	
	/**
	 * Builds the marker.
	 *
	 * @param id the id
	 * @return the drawable associated with the key
	 */
	private Drawable buildMarker(int id)
	{
		Drawable marker = null;
		try 
		{
		marker = context.getResources().getDrawable(id);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
		return marker;
		}
		catch (Exception e ) 
		{
			if(Debug.LOG)
			{
				Log.e("Overlay Icons buildMarker failed ", e.toString());
			}
			 e.toString();
		}
		return marker;
	}
	
	/**
	 * Gets the marker.
	 *
	 * key is string value in the type column in the entites db
	 * @param key the key
	 * @return the marker
	 */
	static public Drawable getMarker(Entity me )
	{
		Drawable marker;
		String _sex = me.getSex();
		String key  = me.getType();
		
		if( me.getType().equals(Constants.ENTITY_TYPE_PATRON))  // handle exception for patrons with sex
		{
			if(_sex.equals(Constants.CLIENT_SEX_MALE) ) //|| _sex.equals(Constants.CLIENT_SEX_FEMALE))
			{
				key = me.getType() + _sex;
			}
			if(_sex.equals(Constants.CLIENT_SEX_FEMALE) ) // || _sex.equals(Constants.CLIENT_SEX_FEMALE))
			{
				key = me.getType() + _sex;
			}

		}
		
		marker = iconMap.get(key);
		if( marker == null )
		{
			marker = iconMap.get(Constants.DEFAULT);
		}
		return ( marker );
	}

	/**
	 * Gets the marker.
	 *
	 * key is string value in the type column in the entites db
	 * @param key the key
	 * @return the marker
	 */
	static public Drawable getMarker(String key )
	{
		Drawable marker;
		
		marker = iconMap.get(key);
		if( marker == null )
		{
			marker = iconMap.get(Constants.DEFAULT);
		}
		return ( marker );
	}
}
