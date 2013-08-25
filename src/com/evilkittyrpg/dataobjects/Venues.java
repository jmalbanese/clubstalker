package com.evilkittyrpg.dataobjects;

import java.util.Iterator;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.dataobjects.xmlparsers.VenueParser;

public class Venues extends Entities
{
	private static final long serialVersionUID = 1L;
	
	private VenueParser parser;

	public Venues()
	{}
	
	public Venues( String feedUrl )
	{
		parser = new VenueParser(feedUrl);
	}

	@Override
	public void read()
	{
		super.addAll( parser.parse());
	}
	
	public void getPatronsNear()
	{
		// first clear the patrons isEntityNear to zero
		AppCommon.getInstance().getPatrons().clearIntitiesNear();
		
		Iterator<Entity> iterator = iterator();

		while( iterator.hasNext() )
		{
			Venue v = (Venue)iterator.next();
			v.setPatronsNear(AppCommon.getInstance().getPatrons());
		}
	}
	
}
