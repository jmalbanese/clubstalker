package com.evilkittyrpg.dataobjects;

import java.util.Date;

import android.util.Log;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.utils.Debug;
import com.google.android.maps.GeoPoint;

/**
 * @author jmalbanese
 * 
 */
public class Entity
{
	private String TAG = "Entity";

	private int index;
	private String type;
	private String uuid;
	private String name_legal;
	private String name_stage;
	private String uuid_agency;
	private String addr_0;
	private String addr_1;
	private String city;
	private String state;
	private String postal_code;
	private Date dob;
	private String prefs;
	private String phone_contact;
	private String enable_phone_contact;
	private String email;
	private String sms;
	private String facebook;
	private String online_now;
	private Date creation_date;
	private String status;
	private double lat;
	private double lon;
	private String url;
	private String comments;
	private String chat;
	private String twitter;
	private String enable_chat;
	private String enable_twitter;
	private String phone_work;
	private String enable_location;
	private String enable_sms;
	private String enable_facebook;
	private String enable_address;
	private String enable_email;
	// private String pix_index;
	// private String pix_banner;
	private String show_banner;
	private String region;
	private Date last_update;
	private int hit_count;
	private String nationality;
	private String measurements;
	private int weight;
	private String incall;
	private int display_priority;
	private String sex;

	private int entitiesNear; // number of enties near this entity
	private String privacy;

	protected MediaElements mediaElements;
	protected Ratings ratings;

	public Entity() // a constructor
	{
		init();
		mediaElements = new MediaElements();
		ratings = new Ratings();
	}

	public Entity(Entity entity) {
		mediaElements = new MediaElements();
		ratings = new Ratings();
		this.transfer(entity);
	}

	public MediaElements getMediaElements()
	{
		return mediaElements;
	}

	public void resolveMediaAndRatings()
	{
		if(Debug.LOG)
		{
			Log.d(TAG,	"Entity - resolveMediaRatings overridden should not be called");
		}
	}

	public void addToMediaElements(MediaElement mel)
	{
		mediaElements.add(mel);
	}

	public void addToRatings(Rating rat)
	{
		ratings.add(rat);
	}

	/**
	 * @return the index
	 */
	public synchronized int getIndex()
	{
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public synchronized void setIndex(int index)
	{
		this.index = index;
	}

	/**
	 * @return the type
	 */
	public synchronized String getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public synchronized void setType(String type)
	{
		this.type = type;
	}

	/**
	 * get geopoint
	 */

	public synchronized GeoPoint getGeoPoint()
	{
		return (new GeoPoint((int) (this.getLat() * 1E6),
				(int) (this.getLon() * 1E6)));
	}

	/**
	 * @return the uuid
	 */
	public synchronized String getUuid()
	{
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public synchronized void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * @return the name_legal
	 */
	public synchronized String getName_legal()
	{
		return name_legal;
	}

	/**
	 * @param name_legal
	 *            the name_legal to set
	 */
	public synchronized void setName_legal(String name_legal)
	{
		this.name_legal = name_legal;
	}

	/**
	 * @return name_stage alias
	 * 
	 */

	public synchronized String getLabel()
	{
		return name_stage;
	}

	/**
	 * @return the name_stage
	 */
	public synchronized String getName_stage()
	{
		if (this.isPrivate()) // handle privacy filters
		{
			return (Constants.PRIVATE_NAME_STAGE);
		}

		return name_stage;
	}

	/**
	 * @param name_stage
	 *            the name_stage to set
	 */
	public synchronized void setName_stage(String name_stage)
	{
		this.name_stage = name_stage;
	}

	/**
	 * @return the uuid_agency
	 */
	public synchronized String getUuid_agency()
	{
		return uuid_agency;
	}

	/**
	 * @param uuid_agency
	 *            the uuid_agency to set
	 */
	public synchronized void setUuid_agency(String uuid_agency)
	{
		this.uuid_agency = uuid_agency;
	}

	/**
	 * @return the addr_0
	 */
	public synchronized String getAddr_0()
	{
		return addr_0;
	}

	/**
	 * @param addr_0
	 *            the addr_0 to set
	 */
	public synchronized void setAddr_0(String addr_0)
	{
		this.addr_0 = addr_0;
	}

	/**
	 * @return the addr_1
	 */
	public synchronized String getAddr_1()
	{
		return addr_1;
	}

	/**
	 * @param addr_1
	 *            the addr_1 to set
	 */
	public synchronized void setAddr_1(String addr_1)
	{
		this.addr_1 = addr_1;
	}

	/**
	 * @return the city
	 */
	public synchronized String getCity()
	{
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public synchronized void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public synchronized String getState()
	{
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public synchronized void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return the postal_code
	 */
	public synchronized String getPostal_code()
	{
		return postal_code;
	}

	/**
	 * @param postal_code
	 *            the postal_code to set
	 */
	public synchronized void setPostal_code(String postal_code)
	{
		this.postal_code = postal_code;
	}

	/**
	 * @return the dob
	 */
	public synchronized Date getDob()
	{
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public synchronized void setDob(Date dob)
	{
		this.dob = dob;
	}

	/**
	 * @return the prefs
	 */
	public synchronized String getPrefs()
	{
		return prefs;
	}

	/**
	 * @param prefs
	 *            the prefs to set
	 */
	public synchronized void setPrefs(String prefs)
	{
		this.prefs = prefs;
	}

	/**
	 * @return the phone_cell
	 */
	public synchronized String getPhone_contact()
	{
		return phone_contact;
	}

	/**
	 * @param phone_cell
	 *            the phone_cell to set
	 */
	public synchronized void setPhone_contact(String phone_contact)
	{
		this.phone_contact = phone_contact;
	}

	/**
	 * @return the getEnable_phone_contact
	 */
	public synchronized String getEnable_phone_contact()
	{
		return enable_phone_contact;
	}

	public synchronized void setEnable_phone_contact(String enable_phone_contact)
	{
		this.enable_phone_contact = enable_phone_contact;
	}

	/**
	 * @return the email
	 */
	public synchronized String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            to set
	 */
	public synchronized void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the sms
	 */
	public synchronized String getSms()
	{
		return sms;
	}

	/**
	 * @param sms
	 *            the sms to set
	 */
	public synchronized void setSms(String sms)
	{
		this.sms = sms;
	}

	/**
	 * @return the facebook
	 */
	public synchronized String getFacebook()
	{
		return facebook;
	}

	/**
	 * @param facebook
	 *            the facebook to set
	 */
	public synchronized void setFacebook(String facebook)
	{
		this.facebook = facebook;
	}

	/**
	 * @return the online_now
	 */
	public synchronized String getOnline_now()
	{
		return online_now;
	}

	/**
	 * @param online_now
	 *            the online_now to set
	 */
	public synchronized void setOnline_now(String online_now)
	{
		this.online_now = online_now;
	}

	/**
	 * @return the creation_date
	 */
	public synchronized Date getCreation_date()
	{
		return creation_date;
	}

	/**
	 * @param creation_date
	 *            the creation_date to set
	 */
	public synchronized void setCreation_date(Date creation_date)
	{
		this.creation_date = creation_date;
	}

	/**
	 * @return the status
	 */
	public synchronized String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public synchronized void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * @return the lat
	 */
	public synchronized double getLat()
	{
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public synchronized void setLat(double lat)
	{
		this.lat = lat;
	}

	/**
	 * @return the lon
	 */
	public synchronized double getLon()
	{
		return lon;
	}

	/**
	 * @param lon
	 *            the lon to set
	 */
	public synchronized void setLon(double lon)
	{
		this.lon = lon;
	}

	/**
	 * @return the url
	 */
	public synchronized String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public synchronized void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the comments
	 */
	public synchronized String getComments()
	{
		if (this.isPrivate())
		{
			return (Constants.PRIVATE_COMMENT);
		}

		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public synchronized void setComments(String comments)
	{
		this.comments = comments;
	}

	/**
	 * @return the chat
	 */
	public synchronized String getChat()
	{
		return chat;
	}

	/**
	 * @param chat
	 *            the chat to set
	 */
	public synchronized void setChat(String chat)
	{
		this.chat = chat;
	}

	/**
	 * @return the twitter
	 */
	public synchronized String getTwitter()
	{
		return twitter;
	}

	/**
	 * @param twitter
	 *            the twitter to set
	 */
	public synchronized void setTwitter(String twitter)
	{
		this.twitter = twitter;
	}

	/**
	 * @return the enable_chat
	 */
	public synchronized String getEnable_chat()
	{
		return enable_chat;
	}

	/**
	 * @param enable_chat
	 *            the enable_chat to set
	 */
	public synchronized void setEnable_chat(String enable_chat)
	{
		this.enable_chat = enable_chat;
	}

	/**
	 * @return the enable_twitter
	 */
	public synchronized String getEnable_twitter()
	{
		return enable_twitter;
	}

	/**
	 * @param enable_twitter
	 *            the enable_twitter to set
	 */
	public synchronized void setEnable_twitter(String enable_twitter)
	{
		this.enable_twitter = enable_twitter;
	}

	/**
	 * @return the phone_work
	 */
	public synchronized String getPhone_work()
	{
		return phone_work;
	}

	/**
	 * @param phone_work
	 *            the phone_work to set
	 */
	public synchronized void setPhone_work(String phone_work)
	{
		this.phone_work = phone_work;
	}

	/**
	 * if location is enabled return true location the last good fix from their
	 * gps as opposed from the address
	 * 
	 * @return the enable_location
	 */
	public synchronized String getEnable_location()
	{
		return enable_location;
	}

	/**
	 * @param enable_location
	 *            the enable_location to set
	 */
	public synchronized void setEnable_location(String enable_location)
	{
		this.enable_location = enable_location;
	}

	/**
	 * @return the enable_sms
	 */
	public synchronized String getEnable_sms()
	{
		return enable_sms;
	}

	/**
	 * @param enable_sms
	 *            the enable_sms to set
	 */
	public synchronized void setEnable_sms(String enable_sms)
	{
		this.enable_sms = enable_sms;
	}

	/**
	 * @return the enable_facebook
	 */
	public synchronized String getEnable_facebook()
	{
		return enable_facebook;
	}

	/**
	 * @param enable_facebook
	 *            the enable_facebook to set
	 */
	public synchronized void setEnable_facebook(String enable_facebook)
	{
		this.enable_facebook = enable_facebook;
	}

	/**
	 * @return the enable_address
	 */
	public synchronized String getEnable_address()
	{
		return enable_address;
	}

	/**
	 * @param enable_address
	 *            the enable_address to set
	 */
	public synchronized void setEnable_address(String enable_address)
	{
		this.enable_address = enable_address;
	}

	/**
	 * @return the pix_index
	 */
	// public String getPix_index() {
	// return pix_index;
	// }

	/**
	 * @param pix_index
	 *            the pix_index to set
	 */
	// public void setPix_index(String pix_index) {
	// this.pix_index = pix_index;
	// }

	/**
	 * @return the pix_banner
	 */
	// public String getPix_banner() {
	// return pix_banner;
	// }

	/**
	 * @param pix_banner
	 *            the pix_banner to set
	 */
	// public void setPix_banner(String pix_banner) {
	// this.pix_banner = pix_banner;
	// }

	/**
	 * @return the show_banner
	 */
	public synchronized String getShow_banner()
	{
		return show_banner;
	}

	/**
	 * @param show_banner
	 *            the show_banner to set
	 */
	public synchronized void setShow_banner(String show_banner)
	{
		this.show_banner = show_banner;
	}

	/**
	 * Return the region ie Los Angeles Las Vegas ect
	 * 
	 * @return the region
	 */
	public String getRegion()
	{
		return region;
	}

	/**
	 * @param region
	 *            the region to set
	 */
	public synchronized void setRegion(String region)
	{
		this.region = region;
	}

	/**
	 * @return the update_date
	 */
	public synchronized Date getLastUpdate()
	{
		return last_update;
	}

	/**
	 * @param update_date
	 *            the update_date to set
	 */
	public synchronized void setLastUpdate(Date update_date)
	{
		this.last_update = update_date;
	}

	public synchronized void setEntiesNear(int val)
	{
		entitiesNear = val;
	}

	public synchronized void incEntitesNear()
	{
		entitiesNear++;
	}

	public synchronized int getEntitiesNear()
	{
		return entitiesNear;

	}

	/**
	 * @return the hit_count
	 */
	public synchronized int getHit_count()
	{
		return hit_count;
	}

	/**
	 * @param hit_count
	 *            the hit_count to set
	 */
	public synchronized void setHit_count(int hit_count)
	{
		this.hit_count = hit_count;
	}

	public synchronized Entity copy()
	{
		Entity c = new Entity();

		c.type = type;
		c.uuid = uuid;
		c.name_legal = name_legal;
		c.name_stage = name_stage;
		c.uuid_agency = uuid_agency;
		c.addr_0 = addr_0;
		c.addr_1 = addr_1;
		c.city = city;
		c.state = state;
		c.postal_code = postal_code;
		c.dob = dob;
		c.prefs = prefs;
		c.phone_contact = phone_contact;
		c.enable_phone_contact = enable_phone_contact;
		c.facebook = facebook;
		c.online_now = online_now;
		c.creation_date = creation_date;
		c.status = status;
		c.lat = lat;
		c.lon = lon;
		c.url = url;
		c.comments = comments;
		c.chat = chat;
		c.twitter = twitter;
		c.enable_chat = enable_chat;
		c.enable_twitter = enable_twitter;
		c.phone_work = phone_work;
		c.enable_location = enable_location;
		c.enable_sms = enable_sms;
		c.enable_facebook = enable_facebook;
		c.enable_address = enable_address;
		c.facebook = facebook;
		c.email = email;
		c.enable_email = enable_email;
		c.region = region;
		c.last_update = last_update;
		c.hit_count = hit_count;
		c.nationality = nationality;
		c.measurements = measurements;
		c.weight = weight;
		c.incall = incall;
		c.display_priority = display_priority;
		c.sex = sex;
		c.entitiesNear = entitiesNear;
		c.privacy = privacy;
		return (c);
	}

	/**
	 * 
	 * Used to move entity from the parser to the entity in a superclass such as
	 * a venue or patron
	 * 
	 * @param c
	 *            - transfer contents from this entity to entity c
	 * 
	 */
	public synchronized void transfer(Entity c)
	{
		type = c.type;
		uuid = c.uuid;
		name_legal = c.name_legal;
		name_stage = c.name_stage;
		uuid_agency = c.uuid_agency;
		addr_0 = c.addr_0;
		addr_1 = c.addr_1;
		city = c.city;
		state = c.state;
		postal_code = c.postal_code;
		dob = c.dob;
		prefs = c.prefs;
		phone_contact = c.phone_contact;
		enable_phone_contact = c.enable_phone_contact;
		facebook = c.facebook;
		online_now = c.online_now;
		creation_date = c.creation_date;
		status = c.status;
		lat = c.lat;
		lon = c.lon;
		url = c.url;
		comments = c.comments;
		chat = c.chat;
		twitter = c.twitter;
		enable_chat = c.enable_chat;
		enable_twitter = c.enable_twitter;
		phone_work = c.phone_work;
		enable_location = c.enable_location;
		enable_sms = c.enable_sms;
		enable_facebook = c.enable_facebook;
		enable_address = c.enable_address;
		facebook = c.facebook;
		email = c.email;
		enable_email = c.enable_email;
		region = c.region;
		last_update = c.last_update;
		hit_count = c.hit_count;
		nationality = c.nationality;
		measurements = c.measurements;
		weight = c.weight;
		incall = c.incall;
		display_priority = c.display_priority;
		sex = c.sex;
		entitiesNear = c.entitiesNear;
		privacy = c.privacy;
	}

	/**
	 * @return the enable_email
	 */
	public synchronized String getEnable_email()
	{
		return enable_email;
	}

	/**
	 * @param enable_email
	 *            the enable_email to set
	 */
	public synchronized void setEnable_email(String enable_email)
	{
		this.enable_email = enable_email;
	}

	/**
	 * @return the nationality
	 */
	public synchronized String getNationality()
	{
		return nationality;
	}

	/**
	 * @param nationality
	 *            the nationality to set
	 */
	public synchronized void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	/**
	 * @return the measurements
	 */
	public synchronized String getMeasurements()
	{
		return measurements;
	}

	/**
	 * @param measurements
	 *            the measurements to set
	 */
	public synchronized void setMeasurements(String measurements)
	{
		this.measurements = measurements;
	}

	/**
	 * @return the weight
	 */
	public synchronized int getWeight()
	{
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public synchronized void setWeight(int weight)
	{
		this.weight = weight;
	}

	/**
	 * @return the display_priority
	 */
	public synchronized int getDisplay_priority()
	{
		return display_priority;
	}

	/**
	 * @param display_priority
	 *            the display_priority to set
	 */
	public synchronized void setDisplay_priority(int display_priority)
	{
		this.display_priority = display_priority;
	}

	public synchronized String getSex()
	{
		return (sex);
	}

	public synchronized void setSex(String sex)
	{
		this.sex = sex;
	}

	/**
	 * @return the incall
	 */
	public synchronized String getIncall()
	{
		return incall;
	}

	/**
	 * @param incall
	 *            the incall to set
	 */
	public synchronized void setIncall(String incall)
	{
		this.incall = incall;
	}

	public synchronized String getPrivacy()
	{
		return privacy;
	}

	public synchronized void setPrivacy(String c)
	{
		this.privacy = c;
	}

	/**
	 * @return the ratings
	 */
	public synchronized Ratings getRatings()
	{
		return ratings;
	}

	/**
	 * @param ratings
	 *            the ratings to set
	 */
	public synchronized void setRatings(Ratings ratings)
	{
		this.ratings = ratings;
	}

	/**
	 * @param mediaElements
	 *            the mediaElements to set
	 */
	public synchronized void setMediaElements(MediaElements mediaElements)
	{
		this.mediaElements = mediaElements;
	}

	public synchronized String getPixPath()
	{
		if (this.isPrivate()) // handle privacy filters
		{
			return (new String(AppCommon.getInstance().getConfiguration()
					.getServer_photos()
					+ Constants.GET_ANONYMOUS));
		}

		MediaElement mel;
		// first we get the image...
		mel = this.getMediaElements().findByType(
				Constants.MEDIAELEMENT_TYPE_MAIN);

		if (mel == null)
		{
			mel = this.getMediaElements().findByType(
					Constants.MEDIAELEMENT_TYPE_FULL);
			if(Debug.LOG)
			{
				Log.e(TAG, " no mediaelement tagged MEDIAELEMENT_TYPE_MAIN found.");
			}
		}

		if (mel == null)
		{
			if(Debug.LOG)
			{
				Log.e(TAG, "No media present for "
						+ this.getName_stage()
						+ " uuid "
						+ this.getUuid()
						+ " subbing "
						+ AppCommon.getInstance().getConfiguration()
								.getServer_photos() + Constants.GET_NO_FULL);
			}
			
			return (new String(AppCommon.getInstance().getConfiguration()
					.getServer_photos()
					+ Constants.GET_NO_FULL));
		}
		return (mel.getUri());
	}

	public synchronized Date getPixDate()
	{
		MediaElement mel;
		// first we get the image...
		mel = this.getMediaElements().findByType(
				Constants.MEDIAELEMENT_TYPE_MAIN);

		if (mel == null)
		{
			if(Debug.LOG)
			{
				Log.e(TAG, "no media element tagged MEDIAELEMENT_TYPE_MAIN found."
						+ this.getName_stage() + " uuid " + this.getUuid());
			}
			
			mel = this.getMediaElements().findByType(
					Constants.MEDIAELEMENT_TYPE_FULL);
		}

		if (mel == null)
		{
			Log.e(TAG, "No media present for " + this.getName_stage()
					+ " uuid " + this.getUuid());
			return (new Date(0));
		}
		return (mel.getCreation_date());
	}

	public boolean isPrivate()
	{
		return (isFreezeOut() || isAnonymous() || isNukeMe());
	}

	public boolean isGoodFix()
	{
		return (Math.abs((this.getLat() + this.getLon())) > 10.0D);
	}

	/**
	 * show nothing at all
	 * 
	 * @return
	 */
	public boolean isFreezeOut()
	{
		return (getPrivacy()
				.equalsIgnoreCase(Constants.CLIENT_PRIVACY_FREEZE_OUT));
	}

	/**
	 * just show a dot when I'm 100 yards from a club, no index listing
	 * 
	 * @return
	 */
	public boolean isAnonymous()
	{
		return ((getPrivacy()
				.equalsIgnoreCase(Constants.CLIENT_PRIVACY_ANONYMOUS)) && (getEntitiesNear() > 0));
	}

	/**
	 * show all my stuff within 100 yards from a club or venue
	 */
	public boolean isThousandYardStare()
	{
		return ((getPrivacy()
				.equalsIgnoreCase(Constants.CLIENT_PRIVACY_THOUSAND_YARD_STARE)) && (getEntitiesNear() > 0));
	}

	/**
	 * show all my stuff all the time
	 * 
	 * @return
	 */

	public boolean isShowItAll()
	{
		return (getPrivacy()
				.equalsIgnoreCase(Constants.CLIENT_PRIVACY_SHOW_IT_ALL));
	}

	/**
	 * nuked show nothing
	 */

	public boolean isNukeMe()
	{
		return (getPrivacy().equalsIgnoreCase(Constants.CLIENT_PRIVACY_NUKE_ME));
	}

	private synchronized void init()
	{
		index = 0;
		type = "";
		uuid = "";
		name_legal = "";
		name_stage = "";
		uuid_agency = "";
		addr_0 = "";
		addr_1 = "";
		city = "";
		state = "";
		postal_code = "";
		dob = new Date();
		prefs = "";
		phone_contact = "";
		enable_phone_contact = "";
		email = "";
		sms = "";
		facebook = "";
		online_now = "";
		creation_date = new Date();
		status = "";
		lat = 0.0;
		lon = 0.0;
		url = "";
		comments = "";
		chat = "";
		twitter = "";
		enable_chat = "n";
		enable_twitter = "n";
		phone_work = "";
		enable_location = "n";
		enable_sms = "n";
		enable_facebook = "n";
		enable_address = "n";
		enable_email = "n";
		// private String pix_index;
		// private String pix_banner;
		show_banner = "n";
		region = "";
		last_update = new Date();
		hit_count = 0;
		;
		nationality = "";
		measurements = "";
		weight = 0;
		incall = "";
		display_priority = 0;
		sex = "";
		entitiesNear = 0;
		privacy = "z";
	}

}
