package com.evilkittyrpg.dataobjects;

import java.util.ArrayList;

import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.xmlparsers.*;

public class MediaElements extends ArrayList<MediaElement>
{
	private static final long serialVersionUID = 3479011134006621976L;
	
	private MediaElementParser mediaElementParser;

	public MediaElements()
	{}
	
	public MediaElements(String feedUrl)
	{
		mediaElementParser = new MediaElementParser(feedUrl);
	}

	public void read()
	{
			this.addAll( mediaElementParser.parse());
	}

	
	public MediaElement findByType(String type)
	{
		for(MediaElement mel : this)
		{
			if( mel.getType().equalsIgnoreCase(type))
			{
				return(mel);
			}
		}
		return null;
	}

	public MediaElements getAll()
	{
		MediaElements mels = new MediaElements();
		mels.addAll(this);
		return mels;
	}
	
	public MediaElements getAllByType( String type )
	{
		MediaElements mels = new MediaElements();
		
		for( MediaElement mel : this)
		{
			if( mel.getType().equals(type))
			{
				mels.add(mel);
			}
		}
		return mels;
	}

	public MediaElement findByOwnerUuid(String q)
	{
		String target;

		for (MediaElement e : this)
		{
			target = e.getOwner_uuid();
			if (target.equals(q))
			{
				return e;
			}
		}
		return (null);
	}

	public void getMediaElementsByOwner(Entity owner)
	{
		int counter = 0;
		
		for( MediaElement mel : this)
		{
			if( mel.getOwner_uuid().equals(owner.getUuid()))
			{
				owner.addToMediaElements(mel);

				if( counter++ > Constants.MAX_GALLERYPIX)	// safety valve
				{
					return;
				}
			}
		}
	}
	
	public void destroy()
	{
		clear();
	}
}
