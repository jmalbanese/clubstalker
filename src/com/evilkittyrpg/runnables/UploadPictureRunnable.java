package com.evilkittyrpg.runnables;

import java.io.ByteArrayOutputStream;
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
import android.graphics.Bitmap;
import android.util.Log;

import com.base64.Base64;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.TextUtils;

/**
 * 
 * @author jmalbanese
 *
 *	given a bitmap and a filename and guidd
 *  upload this picture to the appropriate location on the server
 *  upload
 */

public class UploadPictureRunnable implements Runnable
{
	SharedPreferences preferences;
	String encodedHeadshot;
	
	Bitmap bmp = null;
	String guid = null;
	String filename = null;
	
	String TAG = "UloadPictureRunnable";
	
	public UploadPictureRunnable( Bitmap _bmp, String _filename, String _guid )
	{
		bmp = _bmp;  //
		filename = new String(_filename);
		guid = new String(_guid);
	}
	

	
		@Override
		public void run()
		{
			if(bmp == null)
			{
				if(Debug.LOG)
				{
					Log.d(TAG, "No bitmap");
				}
				
				return;
			}
			
			encodedHeadshot = encodeBitmapJpgBase64(bmp);
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost( AppCommon.getInstance().getConfiguration().getServer_http() + Constants.PUT_CLIENT_IMAGE);
			
			try 
				{
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
					nameValuePairs.add(new BasicNameValuePair("guid", guid));
					nameValuePairs.add(new BasicNameValuePair("filename", filename));
					nameValuePairs.add(new BasicNameValuePair("image", encodedHeadshot));

					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpClient.execute(httpPost);
					
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
		
		private String encodeBitmapJpgBase64(Bitmap bitmap)
		{
			Bitmap bitmapOrg = bitmap;
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte [] ba = bao.toByteArray();
			String sb64 = Base64.encodeBytes(ba);
			return sb64;
		}
		
//		private void upload(Bitmap bitmap)
//		{
//			InputStream is;
//			Bitmap bitmapOrg = bitmap;
//			
//			ByteArrayOutputStream bao = new ByteArrayOutputStream();
//			bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//			byte [] ba = bao.toByteArray();
//			String ba64=Base64.encodeBytes(ba);
//			
//			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//			nameValuePairs.add(new BasicNameValuePair("guid", cd.getUuid()));
//			nameValuePairs.add(new BasicNameValuePair("filename", "headshot.jpg"));
//			nameValuePairs.add(new BasicNameValuePair("image",ba64));
//			
//			try
//			{
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpPost httppost = new
//			HttpPost("http://10.0.2.2:80/android/base.php");
//			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			HttpResponse response = httpclient.execute(httppost);
//			HttpEntity entity = response.getEntity();
//			is = entity.getContent();
//			}
//			catch(Exception e)
//			{
//				if(Debug.LOG)
//				{
//					Log.e("log_tag", "Error in http connection "+e.toString());
//				}
//			}
//		}
//		
		

}
