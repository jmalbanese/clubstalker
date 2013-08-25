package com.evilkittyrpg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.evilkittyrpg.constants.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageUtils
{
	/*
	 * Given a a string, trim to the length val
	 * and append a trailing ...
	 */
	
	public final static String TAG = "ImageUtils";
	
	public static Boolean saveAsJpgToStorage( Bitmap _bitmap, String _filename, Context _context )
	{
		File storageDirectory;
		// bbicon=BitmapFactory.decodeResource(getResources(),R.drawable.bannerd10);
	    //ByteArrayOutputStream baosicon = new ByteArrayOutputStream();
	    //bbicon.compress(Bitmap.CompressFormat.PNG,0, baosicon);
	    //bicon=baosicon.toByteArray();

		if(_context != null)
		{
			storageDirectory = _context.getFilesDir();
			
			if(Debug.LOG)
			{
				Log.d(TAG, "Context Storage directory " + storageDirectory.toString());
			}
		}
		else
		{
			storageDirectory = Environment.getExternalStoragePublicDirectory(Constants.CLIENT_EXTERNAL_DIRECTORY);
			
			if(Debug.LOG)
			{
				Log.d(TAG, "External Storage directory " + storageDirectory.toString());
			}
		}
	    
	    OutputStream outStream = null;
	    File file = new File(storageDirectory, _filename);
	    try 
	    {
	    	 storageDirectory.mkdirs();
		     outStream = new FileOutputStream(file);
		     _bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
		     outStream.flush();
		     outStream.close();
	    }
	    catch(Exception e)
	    {
	    	if(Debug.LOG)
	    	{
	    		Log.d(TAG, _filename + " save to external storage failed" + e.toString());
	    	}
	    	
	    	return(false);
	    }
	    return(true); 
	}

	/**
	 * Return a bitmap for a given file
	 * 
	 * @param _filename - filename
	 * @param _context - use either app private storage or externalstorage if context is null
	 * @return a bitmap
	 */
	
	public static Bitmap getBitmapFromFile( String _filename, Context _context)
	{
		File storageDirectory;
		File f;
		Bitmap bmp = null;
		
		if(_context != null)
		{
			storageDirectory = _context.getFilesDir();
			Log.d(TAG, "Context Storage directory " + storageDirectory.toString());
		}
		else
		{
			storageDirectory = Environment.getExternalStoragePublicDirectory(Constants.CLIENT_EXTERNAL_DIRECTORY); 
			
			if(Debug.LOG)
			{
				Log.d(TAG, "External Storage directory " + storageDirectory.toString());
			}
		}
		
	    f = new File(storageDirectory, _filename);
		try
		{
			bmp = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
		} 
		catch (FileNotFoundException e)
		{
			if(Debug.LOG)
			{
				Log.d(TAG, _filename + "not found - returning null ");
			}
			
			return(null);
		}
    return bmp;
	}
	

	/**
	 * Return a bitmap for a given file
	 * 
	 * @param _filename - filename
	 * @param _context - use either app private storage or externalstorage if context is null
	 * @return a bitmap
	 */
	
	public static Bitmap getBitmapFromImagePath( String _imagePath)
	{
		File f;
		Bitmap bmp = null;
		
	    f = new File(_imagePath);
		try
		{
			bmp = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
		} 
		catch (FileNotFoundException e)
		{
			if(Debug.LOG)
			{
				Log.d(TAG, _imagePath + "not found - returning null ");
			}
			
			return(null);
		}
    return bmp;
	}

}
