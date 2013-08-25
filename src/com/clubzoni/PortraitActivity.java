package com.clubzoni;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import com.clubzoni.R;

import com.fedorvlasov.lazylist.PortraitAdapter;
import com.evilkittyrpg.constants.Constants;

import android.content.Intent;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.dataManagers.EscortPreferenceManager;
//import com.evilkittyrpg.dataobjects.Entities;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.dataobjects.MediaElements;


public class PortraitActivity extends Activity
{
	private EscortPreferenceManager preferenceManager;

    Gallery galleryView;
    PortraitAdapter adapter;
    MediaElements portraits;
//    Entities entities;
    Entity entity;
    AppCommon appcommonInstance;
    
    public PortraitActivity()
    {
    	appcommonInstance = AppCommon.getInstance();  // so we can look at the data.
    }

    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // setup our references
		preferenceManager = new EscortPreferenceManager(this);
        setContentView(R.layout.portrait);
        handleIntent(getIntent());
    }

/*
	    // Create the adView
	    adView = new AdView(this, AdSize.BANNER, "a14e9a1705a58f1");

	    // Lookup your LinearLayout assuming it’s been given
	    // the attribute android:id="@+id/mainLayout"
	    LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout99);

	    // Add the adView to it
	    layout.addView(adView);

	    // Initiate a generic request to load it with an ad
	    adView.loadAd(new AdRequest());
*/
    
 
	@Override
	public void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent)
	{
		String uuid;
		
		Bundle extras =	intent.getExtras();

		if(extras != null)
		{
			uuid = extras.getString(Constants.ENTITY_TYPE_UUID);
			
			entity = appcommonInstance.getVenues().findByUuid(uuid);
			
			if( entity == null)	// not a venue must be a patron
			{
				entity = appcommonInstance.getPatrons().findByUuid(uuid);
			}
			portraits = entity.getMediaElements().getAllByType(Constants.MEDIAELEMENT_TYPE_FULL);
			portraits.addAll(entity.getMediaElements().getAllByType( Constants.MEDIAELEMENT_TYPE_MAIN));
	        adapter=new PortraitAdapter(this, portraits);
	        galleryView =(Gallery)findViewById(R.id.portrait);
	        galleryView.setAdapter(adapter);
	        galleryView.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	            {
	            //    Toast.makeText(HelloGridView.this, "" + position, Toast.LENGTH_SHORT).show();
	            }
	        });

		}
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
		super.onResume();
		applyPreferences();
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

	/*
	 * The final call you receive before your activity is destroyed. This can
	 * happen either because the activity is finishing (someone called finish()
	 * on it, or because the system is temporarily destroying this instance of
	 * the activity to save space. You can distinguish between these two
	 * scenarios with the isFinishing() method.
	 */

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public boolean onSearchRequested()
	{
		return super.onSearchRequested();
	}
	
	private void applyPreferences()
	{
		preferenceManager.getSat();
		preferenceManager.getTraffic();
		preferenceManager.getGpsLoc();
	}

	
	/**
	 * Show error.
	 * 
	 * @param message
	 *            the message
	 */
//	private void showError(String message)
//	{
//		Toast t = Toast.makeText(this, message, Toast.LENGTH_LONG);
//		t.show();
//	}

//    public OnClickListener listener=new OnClickListener(){
//        @Override
//        public void onClick(View arg0) {
//            AppCommon.getInstance().getImageLoader().clearMemoryCache();
//            adapter.notifyDataSetChanged();
//        }
//    };

}