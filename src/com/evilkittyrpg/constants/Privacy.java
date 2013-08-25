package com.evilkittyrpg.constants;


public enum Privacy
{
	FREEZE_OUT('f'),				// show nothing at all
	ANONYMOUS('a'),					// just show a dot when I'm at a club
	THOUSAND_YARD_STARE('1'),		// show all my stuff within 1000 yards of a club or venue
	SHOW_IT_ALL('s'),				// show all my stuff all the time
	NUKE_ME('n');					// destroy every trace of my clubZoni existence
	
	private final char val;
	Privacy( char v)
	{
		this.val = v;
	}
	
	public char getPrivacyCode()
	{
		return(val);
	}
}
