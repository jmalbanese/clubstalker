package com.evilkittyrpg.dataobjects.xmlparsers;

import java.util.ArrayList;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
//import android.util.Log;
import android.util.Xml;

import com.evilkittyrpg.dataobjects.*;

 public class VenueParser  extends BaseFeedParser {

//	private String TAG = "VenueParser";
	
//	static final String BLOCK = "block";
//	static final String DBRESULT = "dbresult";
//	static final String ENTRY = "entry";

	public VenueParser(String feedUrl) 
	{
		super(feedUrl);
	}

	public ArrayList<Venue> parse() {
		final Entity currentObj = new Entity();
		RootElement root = new RootElement(BLOCK);
		final ArrayList<Venue> currentObjs = new ArrayList<Venue>();
		Element item = root.getChild(ENTRY);
		
		item.setEndElementListener(new EndElementListener(){
			public void end() 
			{
//				Log.d(TAG, "adding new Venue Object");
				currentObjs.add( new Venue(currentObj) );
			}
		});
		
		item.getChild("index").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setIndex( XmlUtils.stringToInt(body));
			}
		});
		
		item.getChild("type").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setType(body);
			}
		});

		item.getChild("uuid").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setUuid(body);
			}
		});

		item.getChild("name_legal").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setName_legal(body);
			}
		});

		item.getChild("name_stage").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setName_stage(body);
			}
		});

		item.getChild("uuid_agency").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setUuid_agency(body);
			}
		});

		item.getChild("addr_0").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setAddr_0(body);
			}
		});

		item.getChild("addr_1").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setAddr_1(body);
			}
		});

		item.getChild("city").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setCity(body);
			}
		});
		item.getChild("state").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setState(body);
			}
		});
		
		
		item.getChild("postal_code").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setPostal_code(body);
			}
		});

		item.getChild("dob").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setDob(XmlUtils.stringToDate(body));
			}
		});

		item.getChild("prefs").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setPrefs(body);
			}
		});
		
		item.getChild("phone_contact").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setPhone_contact(body);
			}
		});

		item.getChild("email").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEmail(body);
			}
		});

		item.getChild("sms").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setSms(body);
			}
		});

		item.getChild("facebook").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setFacebook(body);
			}
		});


		item.getChild("creation_date").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setCreation_date(XmlUtils.stringToDate(body));
			}
		});

		item.getChild("status").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setStatus(body);
			}
		});

		item.getChild("lat").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setLat(XmlUtils.stringToDouble(body));
			}
		});

		item.getChild("lon").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setLon(XmlUtils.stringToDouble(body));
			}
		});

		item.getChild("url").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setUrl(body);
			}
		});

		item.getChild("comments").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setComments(body);
			}
		});

		item.getChild("chat").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setChat(body);
			}
		});

		item.getChild("twitter").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setTwitter(body);
			}
		});


		item.getChild("enable_chat").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEnable_chat(body);
			}
		});

		item.getChild("enable_twitter").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEnable_twitter(body);
			}
		});

		item.getChild("phone_work").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setPhone_work(body);
			}
		});

		item.getChild("enable_phone_contact").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEnable_phone_contact(body);
			}
		});

		item.getChild("enable_location").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEnable_location(body);
			}
		});
		
		item.getChild("enable_sms").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEnable_sms(body);
			}
		});
		
		item.getChild("enable_facebook").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEnable_facebook(body);
			}
		});

		
		item.getChild("enable_address").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEnable_address(body);
			}
		});

		item.getChild("enable_email").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEnable_email(body);
			}
		});

		
//		item.getChild("pix_index").setEndTextElementListener(new EndTextElementListener(){
//			public void end(String body) 
//			{
//				currentObj.setPix_index(body);
//			}
//		});

//		item.getChild("pix_banner").setEndTextElementListener(new EndTextElementListener(){
//			public void end(String body) 
//			{
//				currentObj.setPix_banner(body);
//			}
//		});


		item.getChild("show_banner").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setShow_banner(body);
			}
		});


		item.getChild("region").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setRegion(body);
			}
		});

		item.getChild("last_update").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setLastUpdate(XmlUtils.stringToDate(body));
			}
		});
		
		item.getChild("hit_count").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setHit_count(XmlUtils.stringToInt(body));
			}
		});
		
		item.getChild("nationality").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setNationality(body);
			}
		});
		
		item.getChild("measurements").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setMeasurements(body);
			}
		});
		item.getChild("weight").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setWeight(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("incall").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setIncall(body);
			}
		});  

		item.getChild("display_priority").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setDisplay_priority(XmlUtils.stringToInt(body));
			}
		});		
		
		item.getChild("sex").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body)
			{
				currentObj.setSex(body);
			}
		});

		item.getChild("privacy").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body)
			{
				currentObj.setPrivacy(body);
			}
	});

		
		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return currentObjs;
	}
}
