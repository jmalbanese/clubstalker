package com.evilkittyrpg.runnables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.SharedPreferences;
import android.util.Log;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.TextUtils;


public class UpdateUserRunnable implements Runnable
{
	SharedPreferences preferences;
	
	String TAG = "UpdateUserRunnable";
	Boolean forceUpdate;

	public UpdateUserRunnable()
	{
		forceUpdate = false;
	}
	
	public UpdateUserRunnable( Boolean v)
	{
		forceUpdate = v;
	}
	
	/**
	 * Send Data - send user update to server
	 */
	
		@Override
		public void run()
		{
			if(AppCommon.getInstance().getClientData() == null)
			{
				if(Debug.LOG)
				{
					Log.d(TAG, "ClientData is null");
				}
				return;
			}
			
			if( AppCommon.getInstance().getClientData().getDirty()== false)	// if there is nothing new to report return immediately
			{
				if(Debug.LOG)
				{
					Log.d(TAG,"ClientData -  has no new data - exiting");
				}
				return;
			}
	
			if( forceUpdate == false)  // when we Unregister we can't update because we are unregistered - this is the override
			{
				if( AppCommon.getInstance().getClientData().getIsRegistered() == false )
				{
					if(Debug.LOG)
					{
						Log.d(TAG,"ClientData cd -  not registered - exiting");
					}
					return;
				}
			}
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost( AppCommon.getInstance().getConfiguration().getServer_http() + Constants.PUT_CLIENT_DATA);
			
			try 
				{
					List<NameValuePair> nvp = new ArrayList<NameValuePair>(8);

					nvp.add( new BasicNameValuePair("email_address", AppCommon.getInstance().getClientData().getEmail_addr()));
					nvp.add( new BasicNameValuePair("password", AppCommon.getInstance().getClientData().getPassword()));
					nvp.add( new BasicNameValuePair("name_legal", AppCommon.getInstance().getClientData().getName_legal()));
					nvp.add( new BasicNameValuePair("name_stage", AppCommon.getInstance().getClientData().getName_stage()));
					nvp.add( new BasicNameValuePair("lon", AppCommon.getInstance().getClientData().getLonAsString()));
					nvp.add( new BasicNameValuePair("lat", AppCommon.getInstance().getClientData().getLatAsString()));
					nvp.add( new BasicNameValuePair("comment", AppCommon.getInstance().getClientData().getComments()));
					nvp.add( new BasicNameValuePair("type", AppCommon.getInstance().getClientData().getType()));
					nvp.add( new BasicNameValuePair("phone_contact", AppCommon.getInstance().getClientData().getPhoneContact()));
					nvp.add( new BasicNameValuePair("sex", AppCommon.getInstance().getClientData().getSex()));
					nvp.add( new BasicNameValuePair("twitter", AppCommon.getInstance().getClientData().getTwitter()));
					nvp.add( new BasicNameValuePair("privacy", AppCommon.getInstance().getClientData().getPrivacy()));
					
					nvp.add( new BasicNameValuePair("phone_contact", AppCommon.getInstance().getClientData().getPhoneContact()));
					nvp.add( new BasicNameValuePair("enable_phone_contact", AppCommon.getInstance().getClientData().getEnablePhone()));
					nvp.add( new BasicNameValuePair("enable_twitter",AppCommon.getInstance().getClientData().getEnableTwitter()));

					
			        httpPost.setEntity(new UrlEncodedFormEntity(nvp));
				
			//		@SuppressWarnings("unused")
					HttpResponse response = httpClient.execute(httpPost);
					
					StringBuilder sb = TextUtils.inputStreamToString( response.getEntity().getContent());
					String dork = sb.toString();
					
					if(Debug.LOG)
					{
						Log.d(TAG, "httpResponse " + dork);
					}
					
					AppCommon.getInstance().getClientData().setDirty(false);			// successful post, clear dirty flag
					
					if(Debug.LOG)
					{
						Log.d(TAG, response.getStatusLine().toString() ); // send 200 ok
					}
					
				} 
					catch (ClientProtocolException e) 
					{
						if(Debug.LOG)
						{
							Log.d(TAG, e.toString());
						}
				    }
					
					catch (IOException e) 
				    {
						if(Debug.LOG)
						{
							Log.d(TAG, e.toString());
						}
				    }
		};

		// Fast Implementation
//		private StringBuilder inputStreamToString(InputStream is) {
//		    String line = "";
//		    StringBuilder total = new StringBuilder();
//		    
//		    // Wrap a BufferedReader around the InputStream
//		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//
//		    // Read response until the end
//		    try
//			{
//				while ((line = rd.readLine()) != null) { 
//				    total.append(line); 
//				}
//			} catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		    
//		    // Return full string
//		    return total;
//		}

/*		
		private void upload(Bitmap bitmap)
		{
			InputStream is;
			Bitmap bitmapOrg = bitmap;
			
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte [] ba = bao.toByteArray();
			String ba1=Base64.encodeBytes(ba);
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("owneruuid", "xyz"));
			
			nameValuePairs.add(new BasicNameValuePair("image",ba1));
			
			try
			{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new
			HttpPost("http://10.0.2.2:80/android/base.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			}
			catch(Exception e)
			{
				if(Debug.LOG)
				{
					Log.e("log_tag", "Error in http connection "+e.toString());
				}
			}
		}
		
*/		
}
