package com.cookbook.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import java.io.IOException;import java.io.BufferedReader;import java.io.InputStreamReader;import java.io.Closeable;
import java.util.UUID;

import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.runnables.CreateUserRunnable;
import com.evilkittyrpg.runnables.UpdateUserRunnable;

/** * Displays an Registration Dialog - user must explicitly select broadcast allowed to send
 * his/her stuff to internet
 * 
 * .
 *  application ALWAYS auto registers with server regardless of broadcast preference */
public class Register 
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
    
    
    /**     * Displays the Registration if necessary.     */    public static boolean show(final Activity activity) 
    {
      	preferences = AppCommon.getInstance().getSharedPreferences();
   	    	
//to test: force false for reg//       preferences.edit().putBoolean(Constants.PREFERENCE_REGISTER_ACCEPTED, true).commit();

  
// these are the initial preferences - if CreateUserRunnable succeeds they are set in preferences and isRegistered == true
// if CreateUserRunnable cannot create a user, this will all fail and the user will get a repeat of the dialog
   	
        if (!preferences.getBoolean(Constants.PREFERENCE_REGISTER_ACCEPTED, false))  // not already registered 
        {         	
        	// build the dialog now
      	   
        	final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                      	builder.setTitle(R.string.registration_title);           	builder.setCancelable(false);
           	builder.setPositiveButton(R.string.registration_allow, 
           		new DialogInterface.OnClickListener() 
           		{           			public void onClick(DialogInterface dialog, int which) 
           			{           				accept(preferences);           			}           		}
           	);
                        builder.setNegativeButton(R.string.registration_dont_allow,            	new DialogInterface.OnClickListener() 
            	{                	public void onClick(DialogInterface dialog, int which) 
                	{                		refuse(activity);                	}            	}
            );
                        builder.setOnCancelListener(                new DialogInterface.OnCancelListener() 
                {                	public void onCancel(DialogInterface dialog) 
                	{                		refuse(activity);                	}                }
           );

            builder.setMessage(read(activity));            builder.create().show();            return false;        }        return true;    }    private static void accept(SharedPreferences preferences) 
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

    }    private static void refuse(Activity activity) 
    {
    	activity.finish();
    }
         private static CharSequence read(Activity activity) {        BufferedReader in = null;        try {            in = new BufferedReader(new                InputStreamReader(activity.getAssets().open(Constants.ASSET_REGISTER)));            String line;            StringBuilder buffer = new StringBuilder();            while ((line = in.readLine()) != null)                buffer.append(line).append('\n');            return buffer;        } catch (IOException e) {            return "";        } finally {            closeStream(in);        }    }    /**     * Closes the specified stream.     */    private static void closeStream(Closeable stream) {        if (stream != null) {            try {                stream.close();            } catch (IOException e) {                // Ignore            }        }    }}