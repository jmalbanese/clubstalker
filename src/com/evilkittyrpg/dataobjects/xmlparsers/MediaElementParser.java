package com.evilkittyrpg.dataobjects.xmlparsers;

import java.util.ArrayList;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

import com.evilkittyrpg.dataobjects.*;

 public class MediaElementParser  extends BaseFeedParser {

	static final String BLOCK = "block";
	static final String DBRESULT = "dbresult";
	static final String ENTRY = "entry";
	static final String TAG = "MediaElementParser";
	
	public MediaElementParser(String feedUrl) 
	{
		super(feedUrl);
	}

	public ArrayList<MediaElement> parse() {
		final MediaElement currentObj = new MediaElement();
		RootElement root = new RootElement(BLOCK);
		final ArrayList<MediaElement> currentObjs = new ArrayList<MediaElement>();
		Element item = root.getChild(ENTRY);
		
		item.setEndElementListener(new EndElementListener(){
			public void end() 
			{
				currentObjs.add(currentObj.copy());
			}
		});
		
		item.getChild("index").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setIndex( XmlUtils.stringToInt(body));
			}
		});

		item.getChild("uri").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setUri( body );
			}
		});

		item.getChild("comment").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setComment(body);
			}
		});

		item.getChild("position").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setPosition(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("status").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setStatus(body);
			}
		});

		item.getChild("featured").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setFeatured(XmlUtils.stringToInt(body));
			}
		});

		item.getChild("owener_uuid").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setOwner_uuid(body);
			}
		});


		item.getChild("owner_uuid").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setOwner_uuid(body);
			}
		});

		item.getChild("type").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setType(body);
			}
		});

		item.getChild("creation_date").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) 
			{
				currentObj.setCreation_date(XmlUtils.stringToDate(body));
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
