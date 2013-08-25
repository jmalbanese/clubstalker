package com.evilkittyrpg.dataobjects;

import java.util.ArrayList;

import com.evilkittyrpg.dataobjects.xmlparsers.RatingParser;

public class Ratings extends ArrayList<Rating> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8332246299355267277L;

	private RatingParser ratingParser;

	public Ratings()
	{}
	
	public Ratings(String feedUrl)
	{
		ratingParser = new RatingParser(feedUrl);
	}

	public void read()
	{
			this.addAll( ratingParser.parse());
	}
	
	public Rating calculateRatingbyUuidEscort(String uuid)
	{
		String target;

		for (Rating e : this)
		{
			target = e.getUuid_escort();
			if (target.equals(uuid))
			{
				return e;
			}
		}
		return (null);
	}

	public void getRatingsByOwner(Entity owner)
	{
		for( Rating rat : this)
		{
			if( rat.getUuid_escort().equals(owner.getUuid()))
			{
				owner.addToRatings(rat);
			}
		}
	}


	public void destroy()
	{
		clear();
	}
}
