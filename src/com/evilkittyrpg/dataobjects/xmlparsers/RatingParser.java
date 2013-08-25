package com.evilkittyrpg.dataobjects.xmlparsers;

import java.util.ArrayList;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

import com.evilkittyrpg.dataobjects.*;

 public class RatingParser  extends BaseFeedParser {

	static final String BLOCK = "block";
	static final String DBRESULT = "dbresult";
	static final String ENTRY = "entry";

	public RatingParser(String feedUrl) 
	{
		super(feedUrl);
	}

	public ArrayList<Rating> parse() {
		final Rating currentObj = new Rating();
		RootElement root = new RootElement(BLOCK);
		final ArrayList<Rating> currentObjs = new ArrayList<Rating>();
		Element item = root.getChild(ENTRY);
		
		item.setEndElementListener(new EndElementListener(){
			public void end() 
			{
				currentObjs.add(currentObj.copy());
			}
		});
		
		item.getChild("id").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setId( XmlUtils.stringToInt(body));
			}
		});

		item.getChild("uuid_escort").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setUuid_escort( body );
			}
		});

		item.getChild("creation_date").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setCreation_date(XmlUtils.stringToDate(body));
			}
		});

		item.getChild("uuid_john").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setUuid_john( body );
			}
		});

		
		item.getChild("location_quality").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setLocation_quality(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("enthusiasm").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setEnthusiasm(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("technique").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setTechnique(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("looks").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setLooks(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("picture_authentic").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setPicture_authentic(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("comments").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setComments(body);
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
