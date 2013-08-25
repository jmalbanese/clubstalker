package com.evilkittyrpg.dataobjects.xmlparsers;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

import com.evilkittyrpg.dataobjects.*;

 public class ConfigurationParser  extends BaseFeedParser {

	static final String TAG = "ConfigurationParser";
	
	public ConfigurationParser(String feedUrl) 
	{
		super(feedUrl);
	}

	public Configuration parse() {
		final Configuration currentObj = new Configuration();
		RootElement root = new RootElement(BLOCK);

		Element item = root.getChild(ENTRY);
		
		item.setEndElementListener(new EndElementListener(){
			public void end() 
			{}
		});
		
		item.getChild("server_https").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setServer_https( body);
			}
		});

		item.getChild("server_http").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setServer_http( body );
			}
		});

		item.getChild("server_video").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setServer_video(body);
			}
		});

		item.getChild("server_photos").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setServer_photos(body);
			}
		});

		item.getChild("application").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setApplication(body);
			}
		});

		item.getChild("patronUpdateRate").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setPatronUpdateRate(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("userUpdateRate").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setUserUpdateRate(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("locationUpdateMinTime").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setLocationUpdateMinTime(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("hitRadius").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setHitRadius(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("annoucement_url").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setAnnoucementUrl(body);
			}
		});

		item.getChild("show_annoucement").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setShowAnnoucment(body);
			}
		});

		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return currentObj;
	}
}
