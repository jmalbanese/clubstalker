/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clubzoni;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
// import android.widget.Button;
// import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataManagers.EscortPreferenceManager;
import com.evilkittyrpg.dataobjects.Entities;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.interfaces.ActionListener;
import com.evilkittyrpg.interfaces.DataListener;
import com.evilkittyrpg.mapsupport.overlays.OverlayIcons;
import com.evilkittyrpg.mapsupport.overlays.EscortMapOverlay;
import com.evilkittyrpg.runnables.PatronUpdateRunnable;
import com.evilkittyrpg.system.SystemCalls;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.Gps;
import com.evilkittyrpg.utils.TextUtils;
import com.fedorvlasov.lazylist.ImageLoader;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

/**
 * The Class EscortView.
 */
public class AppMapActivity extends MapActivity implements LocationListener,
		ActionListener, DataListener
{
	private static final String TAG = "AppMapActivity";
	private boolean retriggerPatronUpdate;

//	private double START_LAT = 33.987723;
//	private double START_LNG = -118.468253;
	private int START_ZOOM = 15;
	private MapController mc;
	private MapView mv;
	private LocationManager lm;
	private MyLocationOverlay lo;
	private EscortMapOverlay escortMapOverlay;
	private EscortMapOverlay patronOnlineMapOverlay;
	private EscortMapOverlay escortFoundMapOverlay;
	private List<Overlay> overlays; // map overlays live here
    private AppCommon appcommonInstance;
	private EscortPreferenceManager preferenceManager;
	private ImageLoader imageLoader; 
	private TextView textViewName;
	private TextView textViewRegion;
	private TextView textViewComments;
	private ImageView imageView;
//	private Button buttonMapPhone;
//	private Button buttonMapSms;
//	private Button buttonMapEmail;
	private LinearLayout header;
	
//	private ExternalIntent externalIntent;
	private OverlayIcons oi;
	Bundle extras;
	
	@SuppressWarnings("unused")
	private String currentEntityLabel;
	
	private Entities venues;	// list of venues to post to the map
	private Entity entity;		// whatever we are showing on top

	private Entities patrons;
	
	@SuppressWarnings("unused")
	private Entity  patron;
    
//	private GeoCodeUtils geoCodeUtils;
	
	// private AdView adView;

	public AppMapActivity()
	{
		appcommonInstance = AppCommon.getInstance();  // so we can look at the data.
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
	 */
	/*
	 * Called when the activity is first created. This is where you should do
	 * all of your normal static set up: create views, bind data to lists, etc.
	 * This method also provides you with a Bundle containing the activity's
	 * previously frozen state, if there was one. Always followed by onStart().
	 */

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.map);
		preferenceManager = new EscortPreferenceManager(this);
//		externalIntent = new ExternalIntent(this);
        imageLoader = new ImageLoader(this);
        entity = null;
        venues = null;
		extras = null;

		initOverlays();
		initControls();
		
		initMap();
		handleIntent(getIntent());

		/*
		 * // Create the adView adView = new AdView(this, AdSize.BANNER,
		 * "a14e9a1705a58f1"); // Lookup your LinearLayout assuming it’s been
		 * given // the attribute android:id="@+id/mainLayout" LinearLayout
		 * layout = (LinearLayout)findViewById(R.id.linearLayout99);
		 * 
		 * // Add the adView to it layout.addView(adView);
		 * 
		 * showStarMapOverlay(); showSearchResults();
		 * 
		 * 
		 * // Initiate a generic request to load it with an ad adView.loadAd(new
		 * AdRequest());
		 */
	}

	@Override
	public void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent)
	{
		if (Intent.ACTION_VIEW.equals(intent.getAction()))
		{
			String query = intent.getData().toString();
			getTapList(query);
		}

		// extras may contain the following keys
		// Constants.ENTITY_TYPE_UUID = the uuid of the entity to display in the found state
		//
		// and all the types we want to display on the map
		
		extras = intent.getExtras();	// we need the found entity

		// init the found icon
		if( extras.containsKey(Constants.ENTITY_TYPE_UUID))	// highight this entity
		{
			String uuid = extras.getString(Constants.ENTITY_TYPE_UUID);  // fetch the uuid 
			entity = resolveFoundEntity( uuid ); // a found entity, we use found qualifier on the graphic
		}
		
		pushMapPinEntities();				// sets up found entity and venues for map
		showVenueOverlay(venues);			// all static venues

		pushMapPinPatrons();				// get the patrons initial call - this happens on onData updates after ths		
		showPatronOnlineOverlay(patrons);	// show them
		
		showFocusOverlay(entity);			// show the found focused overlay
		postToDetailsBar(entity);			// gets all the venues we are interested in put highlighted in entity

		if( extras.containsKey(Constants.SUPPRESS_EXIT) )	// called from a detail activity - use current entity
		{
			mc.animateTo(entity.getGeoPoint());
		}
		else
		{
			if( AppCommon.getInstance().getClientData().getGeoPoint() != null ) // if user has a valid last point use it
			{
				mc.animateTo(AppCommon.getInstance().getClientData().getGeoPoint());
			}
			else
			{
				mc.animateTo(entity.getGeoPoint());
			}
		}
		mv.invalidate();
	}

	
	private Entity resolveFoundEntity( String uuid)
	{
		Entity e;
		e = appcommonInstance.getVenues().findByUuid(uuid); // this gets a star and is in the top details view
		if( e != null) 
		{			return( e );		}

		e = appcommonInstance.getPatrons().findByUuid(uuid); // this gets a star and is in the top details view
		{			return( e );		}
	}
	
////////////// background update of data is here /////////////////////////////
	
	private PatronUpdateRunnable patronUpdateRunnable;
	private ScheduledFuture<?> patronHandle;
	private ProgressDialog dialog;

	private void startUpdates()
	{
		if(Debug.LOG)
		{
			Log.d(TAG, "Starting patronUpdateRunnable");
		}
		
		postDialog();
		patronUpdateRunnable = new PatronUpdateRunnable(this);
		retriggerPatronUpdate = true;
		patronHandle = appcommonInstance.schedulerPool.schedule
								(patronUpdateRunnable, Constants.UPDATE_DELAY, TimeUnit.MILLISECONDS);

		appcommonInstance.startUserUpdateClientUpdate();
		appcommonInstance.getClientData().startLocationManager();
	}

	
	private void stopUpdates()
	{
		if( dialog != null)
		{
			dialog.dismiss();
			dialog = null;
		}

		if( patronUpdateRunnable == null)
		{
	       	throw new RuntimeException( TAG + " patronUpdateRunnable null");
		}
		
		if(Debug.LOG)
		{
			Log.d(TAG, "Halting patronUpdateRunnable");
		}
		
		retriggerPatronUpdate = false;
		patronHandle.cancel(false);
		appcommonInstance.stopUserUpdateClientUpdate();
		appcommonInstance.getClientData().stopLocationManager();
	}
	
	private void refreshPatronUpdate()
	{
		if(retriggerPatronUpdate == false)
		{
			return;
		}
		
		try
		{
			if(Debug.LOG)
			{
				Log.d(TAG, "refreshPatronUpdate polling server for patrons");
			}
			
			patronHandle = appcommonInstance.schedulerPool.schedule
						(patronUpdateRunnable, appcommonInstance.getConfiguration().getPatronUpdateRate(), TimeUnit.MILLISECONDS);
		}
		catch( NullPointerException e)
		{
			if(Debug.LOG)
			{
				Log.d(TAG, "AppMapActivity attempted to access null patronUpdateRunnable");
			}
		}
	}

	private void postDialog()
	{	
		if( venues.size() > 0 )
		{
			return;		// warm start we have stuff to show.
		}
		
		if( dialog != null )
		{
			if( dialog.isShowing())
			{
				return;
			}
		}
		
		String locationCity = appcommonInstance.getLocationCity();
		
		if( locationCity == Constants.CITY_SELECTOR_WILDCARD)
		{
			locationCity = getString(R.string.OPTIONS_SHOW_ALL); 
		}
		dialog = new ProgressDialog(this);
		dialog.setTitle(getString(R.string.DOWNLOADTITLE) + " " + locationCity);
		dialog.setMessage(getString(R.string.GETTING_PATRONS));
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.show();
	}
	

	// provide implementation of DataListener interface
	// called by PatronUpdateRunnable on completion of duties
	
	@Override
	public void onData()
	{
			if( dialog != null)
			{
				dialog.dismiss();
			}

			if(Debug.LOG)
			{
				Log.d(TAG, "onData called from PatronUpdateRunnable" + " patron count " + appcommonInstance.getPatrons().size() + " media count " + appcommonInstance.getPatronMediaElements().size());
			}
			
			refreshPatronUpdate();			// we got our first batch of data that was triggered by startUserUpdate, ask for more.
			handler.sendEmptyMessage(RESULT_OK);
	}

	@Override
	public void onError(String message)
	{
		if(Debug.LOG)
		{
			Log.d(TAG, "onError called " + message);
		}
		refreshPatronUpdate();			// we got our first batch of data that was triggered by startUserUpdate, ask for more.
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
		startUpdates();
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
		enableLocationUpdates(false);
		stopUpdates();
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
		enableLocationUpdates(false);
		stopUpdates();
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
		enableLocationUpdates(false);
		stopUpdates();
	}

	// post the entity to the top bar...
	
	private void postToDetailsBar(final Entity entity)
	{
		String txt = null;
		if( entity != null)
		{
			// first we get the image...
			imageLoader.DisplayImage(entity.getPixPath(), imageView, Constants.MAPVIEW_SIZE);	
			textViewName.setText(entity.getName_stage());
			String location = TextUtils.formRegion(entity);
			textViewRegion.setText(location);
			txt = TextUtils.truncateWithDots(entity.getComments(), Constants.MAP_TEXT_NUM_CHARS);
			textViewComments.setText(txt);
			
			header.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					onDetailsViewRequested(entity);
				}
			});
			header.setEnabled(true);

/*			
				buttonMapPhone.setEnabled(true);
				
				if(!entity.getEnable_phone_contact().equals(Constants.ENABLED))
				{
					buttonMapPhone.setEnabled(false);
				}

				buttonMapPhone.setOnClickListener(new View.OnClickListener()
				{	
					@Override
					public void onClick(View v)
					{
						externalIntent.onPhoneRequested(entity.getPhone_work());
					}
				});

					
				buttonMapEmail.setEnabled(true);

				if(!entity.getEnabled_email().equals(Constants.ENABLED))
				{
					buttonMapEmail.setEnabled(false);
				}

				buttonMapEmail.setOnClickListener(new View.OnClickListener()
				{
					public void onClick( View v)
					{
						externalIntent.onEmailRequested(entity.getEmail());
					}
				});
				
				// sms
				buttonMapShare.setEnabled(true);

//				if(!entity.getEnable_sms().equals(Constants.ENABLED))
//				{
//					buttonMapSms.setEnabled(false);
//				}

				buttonMapSms.setOnClickListener( new View.OnClickListener()
				{
					public void onClick( View v)
					{
						onSmsRequested(entity);
					}
				});
*/	
		}
	} // end init controls

	//todo major f**k up here note DiskJockeyDetails activity is target.
		private void onDetailsViewRequested(Entity entity)
		{
			if(entity.isPrivate())
			{
				TextUtils.showMessage(this, this.getString(R.string.SORRY_PRIVATE));
				return;
			}
			
	        Intent intent = new Intent();
	        intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
	        
	        if( entity.getType().equals(Constants.ENTITY_TYPE_CLUB))
	        {
	        	intent.setClass(this,ClubDetailsActivity.class); //debugjma
	        }
	        
	        if( entity.getType().equals(Constants.ENTITY_TYPE_PATRON))
	        {
	        	intent.setClass(this, PatronDetailsActivity.class);
	        }
	        startActivity(intent);
		}

/*		
		private void onSmsRequested(Entity entity)
		{
	        Intent intent = new Intent();
	        intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
	        intent.setClass(this,SmsActivity.class);
	        startActivity(intent);
		}
*/

// given an intent, push the appropriate venues to display points on the map
// see AppTabActivity for the extras pushed to this activity
// 
	
	private void pushMapPinEntities()
	{
		Entities retEntities = new Entities(); // build our filtered map venues here

		if( extras.containsKey(Constants.ENTITY_TYPE_ESCORT))	// we are pushing all escorts
		{
			// flag override show address req
			retEntities.addAll(appcommonInstance.getVenues().findByType(Constants.ENTITY_TYPE_ESCORT, false)); // no uuid
		}

		if( extras.containsKey(Constants.ENTITY_TYPE_AGENCY))	// we are pushing all agencies
		{
			retEntities.addAll(appcommonInstance.getVenues().findByType(Constants.ENTITY_TYPE_AGENCY, true )); // no uuid
		}

		if( extras.containsKey(Constants.ENTITY_TYPE_MASSAGE))	// we are pushing all agenies
		{
			retEntities.addAll(appcommonInstance.getVenues().findByType(Constants.ENTITY_TYPE_MASSAGE, false)); // no uuid
		}
		
		if( extras.containsKey(Constants.ENTITY_TYPE_CLUB))	// we are pushing all agencies
		{
			retEntities.addAll(appcommonInstance.getVenues().findByType(Constants.ENTITY_TYPE_CLUB, false )); // no uuid
		}

		// this is for testing the patron m/f icons and trying to figure out wft is wrong with the maps
//		if( extras.containsKey(Constants.ENTITY_TYPE_PATRON))	// we are pushing all agencies
//		{
//			retEntities.addAll(appcommonInstance.getVenues().findByType(Constants.ENTITY_TYPE_PATRON, true )); // no uuid
//		}

		if(entity != null)
		{
			retEntities.remove(entity); 
		}
		
		venues = retEntities;
		
		// at this point venues contains null or the 'found' entity
		return;
	}

	
	// here we handle the updatable entites that are 
	// live - online_now = 'y' - patrons and djs
	// 
	// see public void onData() #259
	// this is done on a data received from the patron runnable on
	// given an intent, push the appropriate venues to display points on the map
	// 
		
		private void pushMapPinPatrons()
		{
			Entities retEntities = new Entities(); // build our filtered map venues here

			if( extras.containsKey(Constants.ENTITY_TYPE_PATRON))	// we are pushing all patrons that are online_now = 'y'
			{
				retEntities.addAll(appcommonInstance.getPatrons().findForMapActivity()); // filter patrons by privacy settings
			}
			patrons = retEntities;
			
			return;
		}
	
	@Override
	public boolean onSearchRequested()
	{
		return super.onSearchRequested();
	}

	private void showVenueOverlay(Entities _entities)
	{
		if( _entities == null)		{	return;		}
		if( _entities.isEmpty())		{  return;		}
		
		overlays = mv.getOverlays();

		if (overlays.contains(escortMapOverlay))
		{
			overlays.remove(escortMapOverlay);
		}
		
		escortMapOverlay = new EscortMapOverlay(_entities, this);
		overlays.add(escortMapOverlay);
	}


	private void showPatronOnlineOverlay(Entities _entities)
	{
		if( _entities == null)		
		{ return; }
		
		if( _entities.isEmpty())	
		{ return; }
		
		overlays = mv.getOverlays();

		if (overlays.contains(patronOnlineMapOverlay))
		{
			overlays.remove(patronOnlineMapOverlay);
		}
		
		patronOnlineMapOverlay = new EscortMapOverlay(_entities, this);
		
		mv.getOverlays().add(patronOnlineMapOverlay);
	}

	// overlay for focused entity only
	private void showFocusOverlay(Entity fentity)
	{
		Entities found_entities;
		found_entities = new Entities();
		
		if (fentity == null)
		{
			// showError(this.getString(R.string.CANT_FIND_ENTITY_BY_NAME));
			return;
		}

//		if( fentity.getEnable_address().equals("n"))
//		{
//			showError("Sorry private address.");
//			return;
//		}

		found_entities.add(fentity);
		
		overlays = mv.getOverlays();

		if (overlays.contains(escortFoundMapOverlay))
		{
			overlays.remove(escortFoundMapOverlay);
		}

		escortFoundMapOverlay = new EscortMapOverlay(Constants.FOUND_MODIFIER, found_entities, this);
		overlays.add(escortFoundMapOverlay);

		this.currentEntityLabel = fentity.getLabel();
		postToDetailsBar(fentity);
	}

	private void getTapList(String name) // absolute
	{
		venues = appcommonInstance.getVenues().findByNameStageContains(name); // get a list of all entities that match query

		if (venues.isEmpty()) // post not found here
		{
			showError(this.getString(R.string.CANT_FIND_ENTITY_BY_NAME));
			return;
		}

		if (venues.size() == 1) // only one item returned
		{
			onTap(venues.get(0));
			return;
		}
		onTap(venues.get(0)); // debug we will display more if more
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed()
	{
		return false;
	}

	public OverlayIcons getOverLayIcons()
	{
		return oi;
	}

	private void initOverlays()
	{
		oi = new OverlayIcons(this);
	}

	/**
	 * Inits the controls. Search button is wired to loadData for test.
	 */
	private void initControls()
	{
		header = (LinearLayout) findViewById(R.id.header);
		header.setEnabled(false);
/*		
		buttonMapPhone = (Button) findViewById(R.id.buttonMapPhone);
		buttonMapPhone.setEnabled(false);

		buttonMapShare = (Button) findViewById(R.id.buttonMapShare;
		buttonMapShsare.setEnabled(false);
			
		buttonMapEmail = (Button) findViewById(R.id.buttonMapEmail);
		buttonMapEmail.setEnabled(false);
*/		
		textViewName = (TextView) findViewById(R.id.name_stage);
		textViewRegion = (TextView) findViewById(R.id.region);
		textViewComments = (TextView) findViewById(R.id.comments);
		imageView = (ImageView) findViewById(R.id.image);
		
		mv = (MapView) findViewById(R.id.map);
		mc = mv.getController();
	}
	// end init controls


	/**
	 * Inits the map.
	 */
	private void initMap()
	{
		mv.setBuiltInZoomControls(true);
		mc.setZoom(START_ZOOM);
//		mc.setCenter(new GeoPoint((int) (AppCommon.getInstance().getClientData().getLat()* 1E6), (int) (AppCommon.getInstance().getClientData().getLon()* 1E6))); 
//		mc.setCenter(new GeoPoint((int) (START_LAT * 1E6),	(int) (START_LNG * 1E6)));
		mv.setSatellite(false);
		mv.setStreetView(false);
	}

	/**
	 * Show error.
	 * 
	 * @param message
	 *            the message
	 */
	private void showError(String message)
	{
		Toast t = Toast.makeText(this, message, Toast.LENGTH_LONG);
		t.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.evilkittyrpg.interfaces.ActionListener#onTap(com.evilkittyrpg.dataobjects
	 * .MapElement)
	 */
	// @Override
	public void onTap(Entity me)
	{
		entity = me;		// this is now the found icon
		currentEntityLabel = me.getLabel();

		pushMapPinEntities();			// sets up found entity and venues for map
		showVenueOverlay(venues);		// show the all overlay
		showFocusOverlay(entity);
		postToDetailsBar(entity);

		if(entity.isGoodFix())
		{
			mc.animateTo( me.getGeoPoint());
		}
	}


	/** The handler called from the update patron runnable on rx of new posits from server. */
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(msg.what == RESULT_OK)				// handler says data ready for patrons
			{	
				if(Debug.LOG)
				{
					Log.d(TAG, "Handler patrons update message RESULT_OK");
				}
				
				pushMapPinPatrons();				// get the patrons		
				showPatronOnlineOverlay(patrons);	// show them
				mv.invalidate();
			}
		}
	};

	/**
	 * Inits the location.
	 * 
	 * @param val
	 *            true == start, false == stop
	 * @return true, if successful
	 */

	private boolean enableLocationUpdates(boolean val)
	{
		if (val == true) // we want to turn it on
		{
			Gps gps = new Gps(this); // we need the tester and dialog gen

			if (gps.testGpsActive() == false) // warning dialog is up if not
												// active
			{
				val = false; // force false if not available
			}
		}

		if (val == true)
		{
			lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.MAP_UPDATE_MINTIME, Constants.MAP_UPDATE_RANGE, this);
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constants.MAP_UPDATE_MINTIME, Constants.MAP_UPDATE_RANGE, this);

//			List<String> providers = lm.getProviders(true);
			overlays = mv.getOverlays();
			lo = new MyLocationOverlay(this, mv);
			overlays.add(lo);
			lo.enableMyLocation();
			return true;
		}

		if (lm != null)
		{
			lm.removeUpdates(this);
			lo.disableMyLocation();
			overlays = mv.getOverlays();
			overlays.remove(lo);
		}
		return false;
	}

	/**
	 * We implement LocationListener so we will get updates targeting this f().
	 * 
	 * @param location
	 *            the location
	 */
	public void onLocationChanged(Location location)
	{
		// overlays = mv.getOverlays();
		// lo = new MyLocationOverlay(this, mv);
		// overlays.add(lo);
		// lo.enableMyLocation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	// @Override
	public void onProviderDisabled(String provider)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	// @Override
	public void onProviderEnabled(String provider)
	{
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String,
	 * int, android.os.Bundle)
	 */
	// @Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		// TODO Auto-generated method stub
	}

	// menu handlers are here
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		menu.findItem(R.id.option_sat).setChecked(preferenceManager.getSat());
		menu.findItem(R.id.option_traffic).setChecked(
				preferenceManager.getTraffic());
		menu.findItem(R.id.option_show_me).setChecked(
				preferenceManager.getGpsLoc());
		return true;
	}

	private void applyPreferences()
	{
		mv.setSatellite(preferenceManager.getSat());
		mv.setTraffic(preferenceManager.getTraffic());
		enableLocationUpdates(preferenceManager.getGpsLoc());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		switch (item.getItemId())
		{
		case R.id.option_sat:
		{
			item.setChecked(!item.isChecked());
			mv.setSatellite(item.isChecked());
			preferenceManager.setSat(item.isChecked());
			return true;
		}

		case R.id.option_traffic:
		{
			item.setChecked(!item.isChecked());
			mv.setTraffic(item.isChecked());
			preferenceManager.setTraffic(item.isChecked());
			return true;
		}

		case R.id.option_show_me:
		{
			if (!item.isChecked()) // are we off?
			{
				if (enableLocationUpdates(true) == true)
				{
					item.setChecked(true); // service is available, set check
				}
				else
				{
					item.setChecked(false); // service is not available
				}
			}
			else
			{
				item.setChecked(false); // was on, now set off
				enableLocationUpdates(false);
			}
			preferenceManager.setGpsLoc(item.isChecked());
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

		if( extras.containsKey(Constants.SUPPRESS_EXIT) )	// called from a detail activity
		{
			super.onBackPressed();
			return;
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
