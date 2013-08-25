package com.evilkittyrpg.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.Entity;

public class TextUtils
{
	
	/**
	 * Show messager.
	 * 
	 * @param message
	 *            the message
	 */
	public static void showMessage(Context ctx, String message)
	{
		Toast t = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
		t.setGravity(Gravity.CENTER_VERTICAL , 0, 0);
		t.show();
	}
	/*
	 * Given a a string, trim to the length val
	 * and append a trailing ...
	 */
	
	public static String truncateWithDots(String val, int cut)
	{
		String hold = val;
		
		if (hold != null && hold.length() > cut)
		{
			hold = hold.substring(0, cut);
		}
		
		hold = hold + "...";
		
		return hold;
	}
	
	// codes are
	// ic = in call
	// oc = out call
	// icoc = both
	// others may follow
	
	public static String translateInCall(String code)
	{
		if( code.equals(Constants.INCALL))
		{	return(new String("In Call")); 	}
		if( code.equals(Constants.OUTCALL))
		{	return(new String("Out Call")); 	}
		if( code.equals(Constants.INCALL_OUTCALL))
		{	return(new String("In Call Out Call")); }
		return(new String(""));
	}
	
	public static String formRegion( Entity entity)
	{
		String line0 = "";
		String line1 = "";
		String output = "";

		if( entity.getAddr_0() == null)
		{ return output; }
		
		if(entity.isPrivate())
		{
			return(Constants.PRIVATE_LOCATION);
		}
		
		if( entity.getEnable_address().equals(Constants.ENABLED)) // do we shows street address?
		{
			line0 = entity.getAddr_0();
			if( line0.length() > 1) { line0 = line0 + "\n"; }
			if( line1.length() > 1) { line1 = line1 + "\n"; }
		}	
		output = output + line0 + line1 + entity.getCity();
		return output;
	}

	public static String formGeoAddress( Entity entity)
	{
		String line0 = "";
		String line1 = "";
		String output = "";

		if( entity.getEnable_address().equals(Constants.ENABLED)) // do we shows street address?
		{
			line0 = entity.getAddr_0();
			if( line0.length() > 1) { line0 = line0 + " "; }
			if( line1.length() > 1) { line1 = line1 + " "; }
		}	
		output = output + line0 + line1 + " " + entity.getCity() + " " + entity.getState()+ " " + entity.getPostal_code();
		return output;
	}

	public static String formatWeight(Integer lbs)
	{
		if( lbs < 70)
		{
			return(new String("---"));
		}
		else
		{
			return(lbs.toString());
		}
	}
	
	// Fast Implementation
	public static StringBuilder inputStreamToString(InputStream is) {
	    String line = "";
	    StringBuilder total = new StringBuilder();
	    
	    // Wrap a BufferedReader around the InputStream
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));

	    // Read response until the end
	    try
		{
			while ((line = rd.readLine()) != null) { 
			    total.append(line); 
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	    
	    // Return full string
	    return total;
	}

}
