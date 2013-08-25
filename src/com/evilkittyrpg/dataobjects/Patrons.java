package com.evilkittyrpg.dataobjects;

import com.evilkittyrpg.dataobjects.xmlparsers.PatronParser;

public class Patrons extends Entities
{
	private static final long serialVersionUID = 1L;

	private PatronParser parser;
	
	public Patrons()
	{}
	
	public Patrons( String feedUrl)
	{
		parser = new PatronParser(feedUrl);
	}

	@Override
	public void read()
	{
		super.addAll(parser.parse());
	}
	
}
