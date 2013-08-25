package com.evilkittyrpg.dataobjects;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.utils.LocationUtils;

/**
 * @author jmalbanese
 * 
 */
public class Venue extends Entity 
{
	@SuppressWarnings("unused")
	private static final String TAG = "Venue";
	
	private int patronsNear;		// # of patrons in range	
	
	public Venue()	// a constructor
	{
		super();
		patronsNear = 0;
	}

	public Venue(Entity entity)	// a constructor
	{
		super(entity);
	}

	public int getPatronsNear()
	{
		return patronsNear;
	}
	
	public synchronized void setPatronsNear( Patrons patronlist )
	{
		patronsNear = 0;

		if(patronlist == null || patronlist.isEmpty())
		{
			return;
		}
		
		double hitRadius = AppCommon.getInstance().getConfiguration().getHitRadius();
		
		for( Entity patron : patronlist)
		{
			if( LocationUtils.inRadius( this, (Patron) patron, hitRadius ))
			{
				patronsNear++;				// we really don't need this one but it's cheap
				patron.incEntitesNear();		// tell patrons to show themselves
			}
		}
	//	Log.d(TAG, "Venue " + this.getName_stage() + " has patronsNear " + patronsNear );	
	}
	
	@Override
	public void resolveMediaAndRatings()
	{
		AppCommon.getInstance().getRatings().getRatingsByOwner(this);
		AppCommon.getInstance().getVenueMediaElements().getMediaElementsByOwner(this);
	}

}
