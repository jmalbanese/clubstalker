package com.clubzoni;

import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataManagers.EscortPreferenceManager;
import com.evilkittyrpg.dataobjects.Entities;
import com.evilkittyrpg.system.SystemCalls;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.TextUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class IndexCommon extends ListActivity
{
	private static String TAG = "IndexCommon";
	
    public AppCommon appcommonInstance;
    public EscortPreferenceManager preferenceManager;
    protected Entities entities;
//    protected Entities shuffledEntities;		// build our scrambled list here
    
   public IndexCommon()
   {
	   appcommonInstance = AppCommon.getInstance();  // so we can look at the data.
   }

   @Override
    public void onCreate(Bundle savedInstanceState) 
   {
        super.onCreate(savedInstanceState);
    	preferenceManager = new EscortPreferenceManager(this);
//    	shuffledEntities = new Entities();
   }

   
	/*
	 * Called when the activity will start interacting with the user. At this
	 * point your activity is at the top of the activity stack , with user input
	 * going to it. Always followed by onPause().
	 */

	@Override
	public void onResume()
	{
		applyPreferences();
		AppCommon.getInstance().getClientData().startLocationManager();
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
	   AppCommon.getInstance().getClientData().stopLocationManager();
		super.onPause();
   }
   
   // menu handlers are here
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.escort_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		menu.findItem(R.id.option_show_all).setChecked(preferenceManager.isCity(Constants.CITY_SELECTOR_WILDCARD));
		menu.findItem(R.id.option_show_los_angeles).setChecked(preferenceManager.isCity(Constants.CITY_NAME_LOS_ANGELES));
		menu.findItem(R.id.option_show_new_york).setChecked(preferenceManager.isCity(Constants.CITY_NAME_NEW_YORK));
		menu.findItem(R.id.option_show_las_vegas).setChecked(preferenceManager.isCity(Constants.CITY_NAME_LAS_VEGAS));
		
		menu.findItem(R.id.option_sat).setChecked(preferenceManager.getSat());
		menu.findItem(R.id.option_traffic).setChecked(preferenceManager.getTraffic());
		menu.findItem(R.id.option_show_me).setChecked(preferenceManager.getGpsLoc());


		return true;
	}

	protected void applyPreferences()
	{
//		mv.setSatellite(preferenceManager.getSat());
//		mv.setTraffic(preferenceManager.getTraffic());
//		enableLocationUpdates(preferenceManager.getGpsLoc());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		switch (item.getItemId())
		{
			case R.id.option_show_all:
			{
				preferenceManager.setCity(Constants.CITY_SELECTOR_WILDCARD);
				return true;
			}
			case R.id.option_show_los_angeles:
			{
				preferenceManager.setCity(Constants.CITY_NAME_LOS_ANGELES);
				return true;
			}

			case R.id.option_show_las_vegas:
			{
				preferenceManager.setCity(Constants.CITY_NAME_LAS_VEGAS);
				return true;
			}

			case R.id.option_show_new_york:
			{
				return true;
			}

			case R.id.menu_about:
			{
				SystemCalls.aboutApp(this);
				return true;
			}
	
			default:
			{
				return super.onOptionsItemSelected(item);
			}
		}
	}

	
	/**
	 * Show error.
	 * 
	 * @param message
	 *            the message
	 */
	protected void showError(String message)
	{
		TextUtils.showMessage(this, message);
	}

	AlertDialog.Builder builder;
	AlertDialog alert;
	Activity activity;
	
	@Override
	public void onBackPressed() 
	{
		if(Debug.LOG)
		{
			Log.d(TAG, "Back Pressed" );
		}
		
		activity = this;
		buildAYSDialog();
		alert = builder.create();
		alert.show();
	}
	
	private void buildAYSDialog()
	{
		builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.exit_clubzoni))
		       .setCancelable(false)
		       .setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           activity.finish();
		           }
		       })
		       .setNegativeButton(getString(R.string.NO), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
	}	
}
