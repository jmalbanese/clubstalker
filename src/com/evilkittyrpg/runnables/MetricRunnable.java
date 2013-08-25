package com.evilkittyrpg.runnables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Formatter;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.utils.Debug;


public class MetricRunnable implements Runnable
{
	String uuidPatron;
	String uuidClub;
	String extra;
	String tag = "Metric";
	
	public MetricRunnable( String _uuidPatron, String _uuidClub, String _extra)
	{
		uuidPatron = _uuidPatron;
		uuidClub = _uuidClub;
		extra = _extra;
	}
	
	public String formatAsXml()
	{
		StringBuilder sb = new StringBuilder();
		Formatter fm = new Formatter(sb, Locale.US);
		fm.format("<ENTRY><uuid_patron>%s</uuid_patron><uuid_club>%s</uuid_club><extra>%s</extra></ENTRY>", uuidPatron,uuidClub,extra);
		return sb.toString();
	}
	
	/**
	 * Send Data - given a metric object
	 * Tell the metric object to format and then
	 * Post to the server
	 * 
	 */
	
		@Override
		public void run()
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost( AppCommon.getInstance().getConfiguration().getServer_http() + Constants.PUT_METRIC );
			
			try 
				{
					List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
					nvp.add( new BasicNameValuePair("ACTION", "InsertMetricData"));
					nvp.add( new BasicNameValuePair("USERID", "callme"));
					nvp.add( new BasicNameValuePair("SELECTOR", "selector"));
					nvp.add( new BasicNameValuePair("DATA", formatAsXml()));
			        httpPost.setEntity(new UrlEncodedFormEntity(nvp));
				
			//		@SuppressWarnings("unused")
					HttpResponse response = httpClient.execute(httpPost);
					
					if(Debug.LOG)
					{
						Log.d(tag, response.getStatusLine().toString() );
					}
				} 
					catch (ClientProtocolException e) 
					{
						if(Debug.LOG)
						{
							Log.d(tag, e.toString());
						}
				    }
						catch (IOException e) 
				    	{
							if(Debug.LOG)
							{
								Log.d(tag, e.toString());
							}
						}
		};

}
