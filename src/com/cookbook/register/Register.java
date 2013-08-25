package com.cookbook.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import java.io.IOException;
import java.util.UUID;

import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.runnables.CreateUserRunnable;
import com.evilkittyrpg.runnables.UpdateUserRunnable;


 * his/her stuff to internet
 * 
 * .
 *  application ALWAYS auto registers with server regardless of broadcast preference

{
    static SharedPreferences preferences;
   	static String email_address;
   	static String client_uuid;		// our uuid

    public Register()
    {
    }
    /**
     * Destroy the user
     * 
     */
    
    public static void UnRegister()
    {
    	// first force back to login in prefs
        preferences.edit().putBoolean(Constants.PREFERENCE_REGISTER_ACCEPTED, false).commit();
        preferences.edit().putString(Constants.CLIENT_PRIVACY, Constants.CLIENT_PRIVACY_NUKE_ME).commit() ;
        AppCommon.getInstance().getClientData().updateFromPrefs();			//
        UpdateUserRunnable uur = new UpdateUserRunnable(true);		// true =  override test if we are registered
        AppCommon.getInstance().execPool.execute(uur);  			// send stuff to server
        AppCommon.getInstance().getImageLoader().deleteAllFiles();											// get rid of all the files
        AppCommon.getInstance().getImageLoader().deleteCacheDir(AppCommon.getInstance().getAppContext()); 	// kill cache directory too
    }
    
    
    /**
    {
      	preferences = AppCommon.getInstance().getSharedPreferences();
   	    	
//to test: force false for reg

  
// these are the initial preferences - if CreateUserRunnable succeeds they are set in preferences and isRegistered == true
// if CreateUserRunnable cannot create a user, this will all fail and the user will get a repeat of the dialog
   	
        if (!preferences.getBoolean(Constants.PREFERENCE_REGISTER_ACCEPTED, false))  // not already registered 
        {
        	// build the dialog now
      	   
        	final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
           
           	builder.setPositiveButton(R.string.registration_allow, 
           		new DialogInterface.OnClickListener() 
           		{
           			{
           	);
            
            	{
                	{
            );
            
                {
                	{
           );

            builder.setMessage(read(activity));
    {
        CreateUserRunnable cur = new CreateUserRunnable(
				preferences,
				UUID.randomUUID().toString(),	// email_address
				Constants.REGISTER_STOCK_PASSWORD,
				1, 1, 1980,
				Constants.CLIENT_TYPE_DEFAULT,
				UUID.randomUUID().toString(),	// client uuid 
				Constants.CLIENT_SEX_MALE,
				Constants.REGISTER_DEFAULT_TWITTER,
				Constants.CLIENT_PRIVACY_THOUSAND_YARD_STARE,
				Constants.CLIENT_PHONE_CONTACT_DEFAULT,  // 555-555-5555
				Constants.DISABLED,				// phone is off by default
				Constants.DISABLED				// twitter is off by default
	);

        // info -> server -> preferences
    		AppCommon.getInstance().execPool.execute(cur);  // send stuff to server, register if success

    }
    {
    	activity.finish();
    }
     