package com.evilkittyrpg.dataobjects;

import com.evilkittyrpg.application.AppCommon;

/**
 * @author jmalbanese
 * 
 */
public class Patron extends Entity 
{
	@SuppressWarnings("unused")
	private static final String TAG = "Patron";
	
	public Patron()	// a constructor
	{
		super();
	}
	
	public Patron(Entity entity)
	{
		super(entity);
	}

	@Override
	public void resolveMediaAndRatings()
	{
		AppCommon.getInstance().getRatings().getRatingsByOwner(this);
		AppCommon.getInstance().getPatronMediaElements().getMediaElementsByOwner(this);
	}
	
}
