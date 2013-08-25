package com.evilkittyrpg.mapsupport.overlays;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.Entities;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.interfaces.ActionListener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;


/**
 * The Class StarMapOverlay.
 */
public class EscortMapOverlay extends ItemizedOverlay<OverlayItem>
{
	
	/** The map elements. */
	private Entities mapElements;
	
	/** The map overlay items. */
	private ArrayList<OverlayItem> mapOverlayItems;
	
	/** The listener. */
	private ActionListener listener;


	/**
	 * Instantiates a new clubzoni map overlay.
	 *
	 * @param defaultMarker the default marker
	 * @param _mapElements the _map elements
	 * @param _listener the _listener StarView.java has handler implements onTap()
	 * @param oli the oli
	 */
	public EscortMapOverlay( Entities _mapElements, ActionListener _listener )
	{
		super(OverlayIcons.getMarker(Constants.DEFAULT));
		Drawable marker;
		listener = _listener;
		mapOverlayItems = new ArrayList<OverlayItem>();
		mapElements = _mapElements;
		for (Entity me : _mapElements)
		{
			
			if( me.getEnable_location().equalsIgnoreCase(Constants.ENABLED))
			{

				GeoPoint g = me.getGeoPoint();
				
				OverlayItem oi = new OverlayItem(g, me.getLabel(),
						me.getComments());
	
				marker = OverlayIcons.getMarker(me);
				boundCenterBottom(marker);
				oi.setMarker(marker );
				mapOverlayItems.add(oi);
			}
		}
		synchronized(this)
		{
			populate();
		}
		setLastFocusedIndex(-1);
	}

	
	/**
	 * Instantiates a new star map overlay.
	 * 
	 * This is used to map a set of map elements with
	 * markers to a default as indicated by a search
	 *
	 * @param defaultMarker the default marker
	 * @param _mapElements the _map elements
	 * @param _listener the _listener - StarView.java has handler implements onTap()
	 */
	
	public EscortMapOverlay( String modifier, Entities _mapElements, ActionListener _listener)
	{
		super(OverlayIcons.getMarker(Constants.DEFAULT));
		Drawable marker;
		listener = _listener;
		mapOverlayItems = new ArrayList<OverlayItem>();
		mapElements = _mapElements;
		for (Entity me : _mapElements)
		{
			if(me.getEnable_location().equals(Constants.ENABLED))
			{ 
				GeoPoint g = me.getGeoPoint();
				
				OverlayItem oi = new OverlayItem(g, me.getUuid(),
						me.getComments());
	
				marker = OverlayIcons.getMarker(me.getType()+ modifier); // one modifier 'Found'
				boundCenterBottom(marker);
				oi.setMarker(marker);
				mapOverlayItems.add(oi);
			}
		}
		synchronized(this)
		{
			populate();
		}
	}
	


	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#onTap(int)
	 */
	@Override
	protected boolean onTap(int index)
	{
		listener.onTap(mapElements.get(index));
		return super.onTap(index);
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#createItem(int)
	 */
	@Override
	protected OverlayItem createItem(int i)
	{
		return mapOverlayItems.get(i);
	}

	@Override
	public void draw( Canvas canvas, MapView mapView, boolean shadow)
	{
		super.draw(canvas, mapView, false);
	}
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#size()
	 */
	@Override
	public int size()
	{
		return mapOverlayItems.size();
	}
	
}
