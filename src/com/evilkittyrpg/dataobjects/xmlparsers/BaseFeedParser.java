package com.evilkittyrpg.dataobjects.xmlparsers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseFeedParser
{
	static final String BLOCK = "block";
	static final String DBRESULT = "dbresult";
	static final String ENTRY = "entry";
	static final String ID = "id";

//	static final String CHANNEL = "channel";
//	static final String PUB_DATE = "pubDate";
//	static final String DESCRIPTION = "description";
//	static final String LINK = "link";
//	static final String TITLE = "title";
//	static final String ITEM = "item";

	private final URL feedUrl;

	protected BaseFeedParser(String feedUrl){
		try {
			this.feedUrl = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() {
		try {
			return  new BufferedInputStream(feedUrl.openConnection().getInputStream(), 12000);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}