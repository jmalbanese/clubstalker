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
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.TextUtils;


public class CreateUserRunnable implements Runnable
{
	private SharedPreferences preferences;
	private String email_address;
	private String password;
	private Integer dobDay;
	private Integer dobMonth;
	private Integer dobYear;
	private String type;
	private String uuid;
	private String sex;
	private String phoneContact;
	private String twitter;
	private String privacy;
	private String enablePhone;
	private String enableTwitter;
	
	String TAG = "CreatUserRunnable";
	
	public CreateUserRunnable( SharedPreferences _preferences, String _email_address, String _password, 
										int _dobMonth, int _dobDay, int _dobYear, String _type,
										String _uuid, String _sex, String _twitter, String _privacy, String _phoneContact, String _enablePhone, String _enableTwitter)
	{
		email_address = _email_address;
		password = _password;
		dobDay = _dobDay;
		dobMonth = _dobMonth;
		dobYear = _dobYear;
		type = _type;
		preferences = _preferences;
		uuid = _uuid;
		sex = _sex;
		phoneContact = _phoneContact;
		twitter = _twitter;
		privacy = _privacy;
		enablePhone = _enablePhone;
		enableTwitter = _enableTwitter;
	}
	
	/**
	 * Post to the server
	 * 
	 */
	
		@Override
		public void run()
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost( AppCommon.getInstance().getConfiguration().getServer_http() + Constants.PUT_MOBILE_REGISTRATION);
			
			try 
				{
					List<NameValuePair> nvp = new ArrayList<NameValuePair>(8);
					
					nvp.add( new BasicNameValuePair("email_address", email_address));
					nvp.add( new BasicNameValuePair("cemail_address", email_address));
					nvp.add( new BasicNameValuePair("password", password));
					nvp.add( new BasicNameValuePair("cpassword", password));
					nvp.add( new BasicNameValuePair("dobday", dobDay.toString()));
					nvp.add( new BasicNameValuePair("dobmonth", dobMonth.toString()));
					nvp.add( new BasicNameValuePair("dobyear", dobYear.toString()));
					nvp.add( new BasicNameValuePair("type", type));
					nvp.add( new BasicNameValuePair("sex", sex));
					nvp.add( new BasicNameValuePair("twitter", twitter));
					nvp.add( new BasicNameValuePair("uuid", uuid));
					nvp.add( new BasicNameValuePair("privacy", privacy));

					nvp.add( new BasicNameValuePair("phone_contact", phoneContact));
					nvp.add( new BasicNameValuePair("enable_phone_contact", enablePhone));
					nvp.add( new BasicNameValuePair("enable_twitter", enableTwitter));
					
			        httpPost.setEntity(new UrlEncodedFormEntity(nvp));
				
			//		@SuppressWarnings("unused")
					HttpResponse response = httpClient.execute(httpPost);
					
					// if response fails we will have to try again PREFERENCE_REGISTER_ACCEPTED will = false if the post above fails
					Editor editor = preferences.edit();
			        editor.putBoolean(Constants.PREFERENCE_REGISTER_ACCEPTED, true);
			        editor.putString(Constants.CLIENT_PRIVACY, privacy);
			        
			        editor.putString(Constants.REGISTER_EMAIL_ADDRESS, email_address);
			        editor.putString(Constants.REGISTER_PASSWORD, password);
			        editor.putString(Constants.REGISTER_TYPE, type);
					editor.putString(Constants.CLIENT_NAME_LEGAL, Constants.CLIENT_NAME_LEGAL_DEFAULT);
					editor.putString(Constants.CLIENT_NAME_STAGE, Constants.CLIENT_NAME_STAGE_DEFAULT);
					editor.putString(Constants.CLIENT_COMMENT, Constants.CLIENT_COMMENTS_DEFAULT);
					editor.putString(Constants.CLIENT_UUID, uuid);
					editor.putString(Constants.CLIENT_SEX, sex);
					editor.putString(Constants.CLIENT_PHONE_CONTACT, phoneContact);
					editor.putString(Constants.CLIENT_TWITTER, twitter);
		    		editor.putString(Constants.CLIENT_PRIVACY, privacy) ;
		    		editor.putString(Constants.CLIENT_ENABLE_TWITTER, Constants.DISABLED) ;
		    		editor.putString(Constants.CLIENT_ENABLE_PHONE, Constants.DISABLED);

			        editor.commit();
			        
			        AppCommon.getInstance().getClientData().updateFromPrefs();		// tell app common to refresh, setting up location services to run etc
			        
					StringBuilder sb = TextUtils.inputStreamToString( response.getEntity().getContent());
					String dork = sb.toString();
					
					if(Debug.LOG)
					{
						Log.d(TAG, "httpResponse " + dork);
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

}
