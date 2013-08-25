package com.clubzoni;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TabHost;

import com.clubzoni.R;
import com.cookbook.register.Register;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.Gps;


public class AppTabActivity extends TabActivity
{
	private static String TAG = "AppTabActivity";
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

	    setContentView(R.layout.escorttab);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Reusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)


	    intent = new Intent().setClass(this, ClubIndexActivity.class);
	    spec = tabHost.newTabSpec(Constants.ENTITY_TYPE_CLUB).setIndicator("Clubs",
	                      res.getDrawable(R.drawable.ic_tab_club))
	                  .setContent(intent);
	    tabHost.addTab(spec);

//
//	    // Do the same for the other tabs
//	    intent = new Intent().setClass(this, PerformerIndexActivity.class);
//	    spec = tabHost.newTabSpec(Constants.ENTITY_TYPE_PERFORMER).setIndicator("Bands",
//	                      res.getDrawable(R.drawable.ic_tab_performer))
//	                  .setContent(intent);
//	    tabHost.addTab(spec);

	    
//	    intent = new Intent().setClass(this, DiscJockeyIndexActivity.class);   // refactor to escortindexactivity
//
//	    // Initialize a TabSpec for each tab and add it to the TabHost
//	    spec = tabHost.newTabSpec(Constants.ENTITY_TYPE_DJ).setIndicator("DJs",
//	                      res.getDrawable(R.drawable.ic_tab_dj))
//	                  .setContent(intent);
//	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, PatronIndexActivity.class);
	    spec = tabHost.newTabSpec(Constants.ENTITY_TYPE_PATRON).setIndicator("Crawlers",
	                      res.getDrawable(R.drawable.ic_tab_patron))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    
	    intent = new Intent().setClass(this, AppMapActivity.class);

	    // TODO - fix up when the data has better shape
		intent.putExtra(Constants.ENTITY_TYPE_UUID, AppCommon.getInstance().getVenues().get(0).getUuid());
		intent.putExtra(Constants.ENTITY_TYPE_ESCORT, Constants.ENTITY_TYPE_ESCORT);	// second arg is a no care
		intent.putExtra(Constants.ENTITY_TYPE_AGENCY, Constants.ENTITY_TYPE_AGENCY);
		intent.putExtra(Constants.ENTITY_TYPE_MASSAGE, Constants.ENTITY_TYPE_MASSAGE);
		intent.putExtra(Constants.ENTITY_TYPE_CLUB, Constants.ENTITY_TYPE_CLUB);
		intent.putExtra(Constants.ENTITY_TYPE_PATRON, Constants.ENTITY_TYPE_PATRON);

	    spec = tabHost.newTabSpec(Constants.MAP).setIndicator("Map",
	                      res.getDrawable(R.drawable.ic_tab_map))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    
	    intent = new Intent().setClass(this, ProfileActivity.class);
	    spec = tabHost.newTabSpec(Constants.PROFILE).setIndicator("Profile",
	                      res.getDrawable(R.drawable.ic_tab_profile))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    tabHost.setCurrentTab(0);
	    
		Gps gps = new Gps(this); // we need the tester and dialog gen
		gps.testGpsActive();
	}

		
	@Override
	public void onPause()
	{ 
		super.onPause();
		AppCommon.getInstance().stopUserUpdateClientUpdate();
	}
	
	@Override
	public void onResume()
	{
		Register.show(this);
		super.onResume();
		AppCommon.getInstance().startUserUpdateClientUpdate();
		AppCommon.getInstance().getClientData().startLocationManager();
	}
	
	@Override
	public void onBackPressed() 
	{
		if(Debug.LOG)
		{
			Log.d(TAG, "Back Pressed" );
		}
	}

}
