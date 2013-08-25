package com.evilkittyrpg.dataobjects;

import com.evilkittyrpg.dataobjects.xmlparsers.ConfigurationParser;

/**
 * Configuration class contains configuration variables from the config
 * table on the server.
 * 
 * things like server urls are here etc.
 * 
 * @author jmalbanese
 *
 */

public class Configuration
{
	private String 	server_https;
	private String 	server_http;
	private String 	server_video;
	private String 	server_photos;
	private String 	application;
	private int		patronUpdateRate; 		// how long between polls to the server to update patron list ms
	private int		userUpdateRate;			// how often do we post our location to the server?
	private int 	locationUpdateMinTime;	// how often does the location manager update?
	private int		hitRadius;				// how close to a club to score a hit?  meters
	private String	annoucementUrl;
	private String	showAnnoucement;
	
	ConfigurationParser parser;
	
	public Configuration()
	{
		hitRadius = 100;
	}
	
	public Configuration( String feedUrl )
	{
		parser = new ConfigurationParser(feedUrl);
		hitRadius = 100;
	}


	public void read()
	{
		Configuration c = parser.parse();
		server_https = c.server_https;
		server_http = c.server_http;
		server_video = c.server_video;
		server_photos = c.server_photos;
		application = c.application;
		patronUpdateRate = c.patronUpdateRate;
		userUpdateRate = c.userUpdateRate;
		locationUpdateMinTime = c.locationUpdateMinTime;
		hitRadius = c.hitRadius;
		annoucementUrl = c.annoucementUrl;
		showAnnoucement = c.showAnnoucement;
	}
	
	public String getShowAnnoucment()
	{
		return showAnnoucement;
		
	}
	
	public void setShowAnnoucment(String v)
	{
		showAnnoucement = v;
	}
	public String getAnnoucementUrl()
	{
		return annoucementUrl;
	}

	public void setAnnoucementUrl(String v)
	{
		annoucementUrl = v;
	}
	/**
	 * @return the userUpdateRate
	 */
	public int getUserUpdateRate()
	{
		return userUpdateRate;
	}

	/**
	 * @param userUpdateRate the userUpdateRate to set
	 */
	public void setUserUpdateRate(int userUpdateRate)
	{
		this.userUpdateRate = userUpdateRate;
	}

	/**
	 */
	public int getLocationUpdateMinTime()
	{
		return locationUpdateMinTime;
	}

	/**
	 * @param locationUpdateRate the locationUpdateRate to set
	 */
	public void setLocationUpdateMinTime(int locationUpdateMinTime)
	{
		this.locationUpdateMinTime = locationUpdateMinTime;
	}

	/**
	 * @return the server_https
	 */
	public String getServer_https()
	{
		return server_https;
	}
	/**
	 * @param server_https the server_https to set
	 */
	public void setServer_https(String server_https)
	{
		this.server_https = server_https;
	}
	/**
	 * @return the server_http
	 */
	public String getServer_http()
	{
		return server_http;
	}
	/**
	 * @param server_http the server_http to set
	 */
	public void setServer_http(String server_http)
	{
		this.server_http = server_http;
	}
	/**
	 * @return the server_video
	 */
	public String getServer_video()
	{
		return server_video;
	}
	/**
	 * @param server_video the server_video to set
	 */
	public void setServer_video(String server_video)
	{
		this.server_video = server_video;
	}
	/**
	 * @return the server_photos
	 */
	public String getServer_photos()
	{
		return server_photos;
	}
	/**
	 * @param server_photos the server_photos to set
	 */
	public void setServer_photos(String server_photos)
	{
		this.server_photos = server_photos;
	}
	/**
	 * @return the application
	 */
	public String getApplication()
	{
		return application;
	}
	/**
	 * @param application the application to set
	 */
	public void setApplication(String application)
	{
		this.application = application;
	}

	public int getPatronUpdateRate()
	{
		return patronUpdateRate;
	}

	public void setPatronUpdateRate(int ms)
	{
		patronUpdateRate = ms;
	}
	
	public void setHitRadius(int r)
	{
		hitRadius = r;
	}
	
	public int getHitRadius()
	{
		return hitRadius;
	}
	
}
