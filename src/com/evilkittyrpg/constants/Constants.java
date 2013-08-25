package com.evilkittyrpg.constants;

public class Constants   
{
	public final static String	SUPPRESS_EXIT = "supressExit";
	public final static int		TOP_ENTITY = 100;		// db magic number put all entites with this prority at the top regardless of shuffle
//	public final static String  STARVIEW_BASE_URL = "http://clubzoni.com";
	
	public final static int		MAX_PIX_STORAGE = 100000000;
	public final static long	MAX_PIX_AGE_DAYS = 24 * 60 * 60 * 1000 * 30; // force refresh every 30 days
	public final static int		MAX_GALLERYPIX = 12;
	
//	public final static int		PATRON_UPDATE_RATE = 10000;
	public static final int 	PATRON_DELAY = 500;
	
//	public final static int 	USER_UPDATE_RATE = 10000;
	public final static int		UPDATE_DELAY = 100;
	
	public final static int 	MAP_UPDATE_MINTIME = 60000;
	public final static int		MAP_UPDATE_RANGE	= 100;
	
	public final static int		CLIENT_DATA_UPDATE_MINTIME = 60000;
	public final static int 	CLIENT_DATA_UPDATE_RANGE = 100;  // only report diffs in 100 meter resolution

	public final static int 	UPDATE_DELTA = 1000 * 60 * 2;


	public final static String CITY_SELECTOR = "city_selector";	
	public final static String CITY_SELECTOR_WILDCARD = "*";

	public final static String CONFIGURATION_URL = "http://clubzoni.com/flashbinding_read.php?ACTION=GetConfiguration";
	
	// the clubs
	public final static String GET_ACTIVE_CLUBS ="flashbinding_read.php?ACTION=GetActiveClubsByRegion&LABEL=" + CITY_SELECTOR;
	public final static String GET_ACTIVE_CLUBS_MEDIA_ELEMENTS="flashbinding_read.php?ACTION=GetActiveClubsMediaElementsByRegion&LABEL=" + CITY_SELECTOR;
	public final static String GET_ACTIVE_CLUBS_RATINGS="flashbinding_read.php?ACTION=GetActiveClubsRatingsByRegion&LABEL=" + CITY_SELECTOR;
	
	// the patrons
	public final static String GET_ACTIVE_PATRONS="flashbinding_read.php?ACTION=GetPatronsOnLineNowLatLonByRegion&LABEL=" + CITY_SELECTOR;
	public final static String GET_ACTIVE_PATRONS_MEDIA_ELEMENTS="flashbinding_read.php?ACTION=GetPatronsOnLineNowMediaElementsByRegion&LABEL=" + CITY_SELECTOR;
	
// http call to send xml metrics data to db

	public final static String PUT_METRIC="flashbinding_write.php";

// http call to send registration via value pairs

	public final static String PUT_MOBILE_REGISTRATION="login/mobile_register-exec.php";
	public final static String PUT_CLIENT_DATA="login/update_client.php";
	public final static String PUT_CLIENT_IMAGE="login/mobile_media-exec.php";
	
	public final static String GET_NO_MAIN = "media_client/main/nomain.jpg";
	public final static String GET_NO_FULL = "media_client/full/nofull.jpg";
	public final static String GET_ANONYMOUS = "media_client/full/private.jpg";

//	public final static String WIKIPEDIA_EN_URL = "http://en.wikipedia.org/wiki/";
//	public final static String IMDB_EN_URL = "http://www.imdb.com/find?s=all&q=";
	
	public final static String CITY_NAME_LOS_ANGELES ="Los Angeles";
	public final static String CITY_NAME_NEW_YORK ="New York";
	public final static String CITY_NAME_LAS_VEGAS ="Las Vegas";
	
	public final static String DEFAULT_ENTITY_LABEL = "Lindsay Lohan";
	
	public final static String PRIVATE_NAME_STAGE = "Private";
	public final static String PRIVATE_LOCATION = "Private";
	public final static String PRIVATE_COMMENT = "Comments Private";
	
	public static final String CLIENT_NAME_LEGAL_DEFAULT = "my legal name";
	public static final String CLIENT_NAME_STAGE_DEFAULT = "my fake name";
	public static final String CLIENT_COMMENTS_DEFAULT = "comments go here";
	public static final String CLIENT_PHONE_CONTACT_DEFAULT = "555-555-5555";
	public static final String CLIENT_UUID_DEFAULT = "0";
	
	public static final String CLIENT_ENABLE_PHONE = "clientEnablePhone";
	public static final String CLIENT_ENABLE_TWITTER = "clientEnableTwitter";
	
	public static final String CLIENT_NAME_LEGAL = "clientNameLegal";
	public static final String CLIENT_NAME_STAGE = "clientNameStage";
	public static final String CLIENT_LON = "clientLog";
	public static final String CLIENT_LAT = "clientLat";
	public static final String CLIENT_COMMENT = "clientComment";
	public static final String CLIENT_TYPE = "clientType";
	public static final String CLIENT_TYPE_DEFAULT = "patron";
	public static final String CLIENT_PHONE_CONTACT = "clientPhoneContact";
	public static final String CLIENT_UUID = "clientUuid";
	public static final String CLIENT_HEADSHOT_FILENAME = "headshot.jpg";
	public static final String CLIENT_EXTERNAL_DIRECTORY = "clubzoni";
    public static final String CLIENT_POST_UPDATES = "postUpdates";
	public static final String CLIENT_SEX = "sex";
	public static final String CLIENT_TWITTER = "twitter";
	public static final String CLIENT_PRIVACY = "privacy";
	public static final String CLIENT_SEX_MALE = "m";
	public static final String CLIENT_SEX_FEMALE = "f";
	
	public static final String CLIENT_PRIVACY_FREEZE_OUT = "z";				// show nothing at all
	public static final String CLIENT_PRIVACY_ANONYMOUS = "a";				// just show a dot when I'm 100 yards from a club, no index listing
	public static final String CLIENT_PRIVACY_THOUSAND_YARD_STARE = "1";	// show all my stuff within 100 yards from a club or venue
	public static final String CLIENT_PRIVACY_SHOW_IT_ALL = "s";			// show all my stuff all the time
	public static final String CLIENT_PRIVACY_NUKE_ME = "n";				// destroy every trace of my clubZoni existence
	
	// general icons start with found
	
	public final static String FOUND_LOCATION = "fl";
	public final static String DEFAULT = "default";
	

	public final static String EXTRA_TAG_POSITION = "position";
	
	public final static String MEDIAELEMENT_TYPE_MAIN = "m";
	public final static String MEDIAELEMENT_TYPE_VIDEO = "v";
	public final static String MEDIAELEMENT_TYPE_THUMB = "t";
	public final static String MEDIAELEMENT_TYPE_FULL = "f";
	
	// these match table entries in db
	
	public final static String ENTITY_TYPE_UUID = "uuid"; // if uuid it is the individual we want to map
	
	public final static String FOUND_MODIFIER = "Found";

// refactor constants
	public final static String ENTITY_TYPE_PERFORMER = "performer";
	public final static String ENTITY_TYPE_PERFORMER_FOUND = "performerFound"; //"performerFound";

	public final static String ENTITY_TYPE_DJ = "discjockey";
	public final static String ENTITY_TYPE_DJ_FOUND = "discjockeyFound"; //"discJockeyFound";
	
	public final static String ENTITY_TYPE_CLUB ="club";
	public final static String ENTITY_TYPE_CLUB_FOUND ="clubFound";

	public final static String ENTITY_TYPE_PATRON ="patron"; 			// a patron, a visitor to an entity
	public final static String ENTITY_TYPE_PATRON_FOUND ="patronFound";
	
	public final static String ENTITY_TYPE_PATRON_MALE = "patronm";
	public final static String ENTITY_TYPE_PATRON_FEMALE = "patronf";

	public final static String MAP ="map"; 					// a patron, a visitor to an entity
	public final static String PROFILE ="profile"; 			// a patron, a visitor to an entity

// remove when done	
	
	public final static String ENTITY_TYPE_ESCORT ="escort";
	public static final String ENTITY_TYPE_ESCORT_FOUND = "escortFound";
	
	public final static String ENTITY_TYPE_AGENCY ="agency";
	public final static String ENTITY_TYPE_AGENCY_FOUND ="agencyFound";
	
	public final static String ENTITY_TYPE_JOHN ="john"; //  removed, now patron
	public static final String ENTITY_TYPE_JOHN_FOUND = "johnFound";

	public final static String ENTITY_TYPE_MASSAGE ="massage";
	public final static String ENTITY_TYPE_MASSAGE_FOUND ="massageFound";
	
	public final static String ENTITY_TYPE_PHONESEX ="phonesex";
	

	
	public final static String ENABLED = "y";
	public final static String DISABLED = "n";
	public final static String FORCE = "f";

	public final static int	INDEX_TEXT_NUM_CHARS = 125;
	public final static int MAP_TEXT_NUM_CHARS = 256;
	public final static int MASSAGE_INDEX_NUM_CHARS = 112;
	
	public static final String MEDIA = "media";
	public static final int LOCAL_AUDIO = 1;
	public static final int STREAM_AUDIO = 2;
	public static final int RESOURCES_AUDIO = 3;
	public static final int LOCAL_VIDEO = 4;
	public static final int STREAM_VIDEO = 5;
	
	public static final String INCALL = "ic";
	public static final String OUTCALL = "oc";
	public static final String INCALL_OUTCALL = "icoc";
	
//	public static final int MAPVIEW_SIZE = 30;
//	public static final int	THUMBNAIL_SIZE = 70;
//	public static final int DETAIL_SIZE = 100;
//	public static final int DETAIL_LANDSCAPE_SIZE = 300;
//	public static final int PORTRIAT_SIZE = 1000;

	public static final int MAPVIEW_SIZE = 50;			// sizing for image above map and video
	public static final int	THUMBNAIL_SIZE = 100;		// Club, Patron and Thumbnail index adapters - overridden in xml
	public static final int DETAIL_SIZE = 100;
	public static final int DETAIL_LANDSCAPE_SIZE = 100;
	public static final int PORTRIAT_SIZE = 1000;

	public static final String EMAIL_SUBJECT = "Hi!";
	public static final String EMAIL_BODY = "You are hot!";
	public static final String EMAIL_TYPE = "message/rfc822";
	
// auto registration constants	
    public static final String ASSET_REGISTER = "REGISTER";
    public static final String PREFERENCE_REGISTER_ACCEPTED = "register.accepted";
    
    public static final String REGISTER_EMAIL_ADDRESS_DEFAULT = "default email address";
    public static final String REGISTER_TYPE_DEFAULT = "default type";
    
    public static final String PREFERENCES_REGISTER = "register";
	public static final String REGISTER_STOCK_PASSWORD = "online";
	public static final String REGISTER_EMAIL_ADDRESS ="registerEmailAddress";
	public static final String REGISTER_PASSWORD = "registerPassword";
	public static final String REGISTER_TYPE = "registerType";

	public static final String REGISTER_DEFAULT_TWITTER = "twitter.com/";
	public static final String REGISTER_PASSWORD_DEFAULT = "default password";


}
