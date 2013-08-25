package com.evilkittyrpg.utils;

import java.util.Calendar;
import java.util.Date;

import android.util.Log;

@SuppressWarnings("unused")
public class DateUtils
{
	static public int getAge(Date dateOfBirth) {
	    int age = 0;
	    Calendar born = Calendar.getInstance();
	    Calendar now = Calendar.getInstance();
	    if(dateOfBirth!= null) {
	        now.setTime(new Date());
	        born.setTime(dateOfBirth);  
	        if(born.after(now)) {
	            throw new IllegalArgumentException("Can't be born in the future");
	        }
	        age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);             
	        if(now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR))  {
	            age-=1;
	        }
	    }  
	    if(age>99)
	    {
	    	age = 99; 
	    	if(Debug.LOG)
	    	{
//	    		Log.e("DateUtils", "Invalid Age Detected");
	    	}
	    }
	    return age;
	}
}
