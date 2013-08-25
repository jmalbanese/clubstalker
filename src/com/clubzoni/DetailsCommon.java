package com.clubzoni;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataManagers.EscortPreferenceManager;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.runnables.MetricRunnable;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.ExternalIntent;
import com.evilkittyrpg.utils.TextUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class DetailsCommon extends Activity
{
	protected EscortPreferenceManager preferenceManager;
    protected Entity entity;
	protected ExternalIntent externalIntent;
    protected AppCommon appcommonInstance;
    
    private String TAG = "DetailsCommon";

    public DetailsCommon()
    {
    	appcommonInstance = AppCommon.getInstance();  // so we can look at the data.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        // setup our references
		preferenceManager = new EscortPreferenceManager(this);
		externalIntent = new ExternalIntent(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }    
    
    protected void handleIntent(Intent intent)
    {
    	try
    	{
    	appcommonInstance.execPool.execute( new MetricRunnable( AppCommon.getInstance().getClientData().getUuid(), entity.getUuid(), entity.getName_stage()+ " details viewed"));
    	}
    	catch(Exception e)
    	{
    		if( Debug.LOG)
    		{
    			Log.e(TAG, "Entity probably not present");
    		}
    	}
    }
    
    protected void onMapRequested()
	{
		Intent intent = new Intent();
		intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
		intent.putExtra(Constants.ENTITY_TYPE_ESCORT, Constants.ENTITY_TYPE_ESCORT);	// second arg is a no care
		intent.putExtra(Constants.ENTITY_TYPE_AGENCY, Constants.ENTITY_TYPE_AGENCY);
		intent.putExtra(Constants.ENTITY_TYPE_MASSAGE, Constants.ENTITY_TYPE_MASSAGE);
		intent.putExtra(Constants.ENTITY_TYPE_CLUB, Constants.ENTITY_TYPE_CLUB);
		intent.putExtra(Constants.ENTITY_TYPE_PATRON, Constants.ENTITY_TYPE_PATRON);
		intent.putExtra(Constants.SUPPRESS_EXIT, Constants.SUPPRESS_EXIT);				// tell maps not to post do you want to exit.
		intent.setClass(this, AppMapActivity.class);
		startActivity(intent);
	}

	
	protected void onGalleryRequested()
	{
        Intent intent = new Intent();
        intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
        intent.setClass(this,PortraitActivity.class);
        startActivity(intent);
	}
	
	protected void onVideoRequested()
	{
		Intent intent = new Intent();
		intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
		intent.setClass(this, MediaPlayerVideoActivity.class);
//		intent.setClass(this, VideoActivity.class);
		startActivity(intent);
	}
	
	/**
	 *  replaces smsRequested
	 *  
	 */
	protected void onShareRequested()
	{
		externalIntent.onShareRequested(AppCommon.getInstance().getClientData());
	}
	
	protected void onSmsRequested()
	{
        Intent intent = new Intent();
        intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
        intent.setClass(this,SmsActivity.class);
        startActivity(intent);
	}

	protected void applyPreferences()
	{
		preferenceManager.getSat();
		preferenceManager.getTraffic();
		preferenceManager.getGpsLoc();
	}
	
	/*
	 * Called after your activity has been stopped, prior to it being started
	 * again. Always followed by onStart()
	 */

	@Override
	public void onRestart()
	{
		super.onRestart();
	}

	/*
	 * Called when the activity is becoming visible to the user. Followed by
	 * onResume() if the activity comes to the foreground, or onStop() if it
	 * becomes hidden.
	 */

	@Override
	public void onStart()
	{
		super.onStart();
	}

	/*
	 * Called when the activity will start interacting with the user. At this
	 * point your activity is at the top of the activity stack , with user input
	 * going to it. Always followed by onPause().
	 */

	@Override
	public void onResume()
	{
//		AppCommon.getInstance().startUserUpdate();
		applyPreferences();
 		super.onResume();
	}

	/*
	 * Called when the system is about to start resuming a previous activity.
	 * This is typically used to commit unsaved changes to persistent data, stop
	 * animations and other things that may be consuming CPU, etc.
	 * Implementations of this method must be very quick because the next
	 * activity will not be resumed until this method returns.
	 * 
	 * Followed by either onResume() if the activity returns back to the front,
	 * or onStop() if it becomes invisible to the user.
	 */

	@Override
	public void onPause()
	{
//		AppCommon.getInstance().stopUserUpdate();
		super.onPause();
	}

	/*
	 * Called when the activity is no longer visible to the user, because
	 * another activity has been resumed and is covering this one. This may
	 * happen either because a new activity is being started, an existing one is
	 * being brought in front of this one, or this one is being destroyed.
	 * 
	 * Followed by either onRestart() if this activity is coming back to
	 * interact with the user, or onDestroy() if this activity is going away.
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	public void onStop()
	{
		super.onStop();
	}

	@Override
	public boolean onSearchRequested()
	{
		return super.onSearchRequested();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}


	/**
	 * Show error.
	 * 
	 * @param message
	 *            the message
	 */
	public void showError(String message)
	{
		TextUtils.showMessage(this, message);
	}




}
