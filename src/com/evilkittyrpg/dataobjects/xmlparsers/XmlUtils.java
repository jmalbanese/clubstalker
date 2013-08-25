package com.evilkittyrpg.dataobjects.xmlparsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.evilkittyrpg.utils.Debug;

import android.util.Log;

public class XmlUtils 
{
	static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String TAG = "XmlUtils";
	private static Date _date;

	public static int stringToInt( String v)
	{
		int _id;
		v.trim();
			
		try
		{
			_id = Integer.parseInt(v);
		}
		catch( NumberFormatException e)
		{
			if(Debug.LOG)
			{
				Log.e(TAG, "illegal integer format");
			}
			throw new RuntimeException(e);
		}
	
		return _id;
	}

	public static double stringToDouble(String v)
	{
		double _r;
		v.trim();
			
		try
		{
			_r = Double.parseDouble(v);
		}
		catch( NumberFormatException e)
		{
			if(Debug.LOG)
			{
				Log.e(TAG, "illegal double format");
			}
			throw new RuntimeException(e);
		}
	
		return _r;
	}

	public static Date stringToDate(String date)
	{	// pad the date if necessary

		try {
			 _date = FORMATTER.parse(date.trim());
		} 
		catch (ParseException e)
		{
			if(Debug.LOG)
			{
				Log.e(TAG, "illegal date format");
			}
			_date = new Date(0L);
		}
		return _date;
	}

}
