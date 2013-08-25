package com.clubzoni;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.clubzoni.R;

import com.fedorvlasov.lazylist.PatronIndexAdapter;
import com.evilkittyrpg.constants.Constants;

import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.interfaces.DataListener;
import com.evilkittyrpg.runnables.PatronUpdateRunnable;
import com.evilkittyrpg.utils.Debug;

public class PatronIndexActivity extends IndexCommon implements DataListener
{
	private static String TAG = "PatronIndexActivity";
	private boolean retriggerPatronUpdate;
    ListView list;
    PatronIndexAdapter adapter; 
    Thread shuffleThread;
    ProgressDialog dialog;
    
    
    public PatronIndexActivity()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // setup our references
        setContentView(R.layout.main);
        entities = appcommonInstance.getPatrons().findForPatronIndexActivity();
        
//		if(Debug.LOG)
//      {
//       	 Log.d(TAG, "find by type returned oncreate  " + entities.size());
//		}
        
		adapter=new PatronIndexAdapter(this, entities);
        this.setListAdapter(adapter);

        handleIntent(getIntent());
		
//		Eula.show(this);
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
    }
    

	@Override
	public void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent)
	{
	}

	// click controls here
	@Override
	protected void onListItemClick(ListView l, View v, int position, long Id )
	{
			Entity entity = this.entities.get(position);
			if(entity.isPrivate())
			{
				return;
			}
				
	        Intent intent = new Intent();
	        intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
	        intent.setClass(this,PatronDetailsActivity.class);
	        startActivity(intent);
	} ;



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
		stopPatronUpdate();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		startPatronUpdate();
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
	

//    public OnClickListener listener=new OnClickListener(){
//        @Override
//        public void onClick(View arg0) {
//            AppCommon.getInstance().getImageLoader().clearMemoryCache();
//            adapter.notifyDataSetChanged();
//        }
//    };
	
    
//	// runnables
//	private class shuffleEntities implements Runnable
//	{
//			public void run()
//			{
//				shuffledEntities.clear();
//				shuffledEntities.addAll(entities);		//  make a local copy
//				shuffledEntities.shuffleEntities();		//  shuffle
//				Message m = new Message();
//				Bundle b = new Bundle();
//				b.putString("shuffler", "ok");
//				m.setData(b);
//				handler.sendMessage(m);
//			}
//	};
//	
//	private void swapEntities()
//	{
//		entities.clear();
//		entities.addAll(shuffledEntities);
//		adapter=new PatronIndexAdapter(this, entities);
//        this.setListAdapter(adapter);
//	}
//	
	
	private void processUpdate()
	{
//        entities = appcommonInstance.getPatrons().findByType(Constants.ENTITY_TYPE_PATRON);
        entities = appcommonInstance.getPatrons().findForPatronIndexActivity();
        
        if(Debug.LOG)
        {
        	Log.d(TAG, "findForPatronIndexActivity returned oncreate  " + entities.size());
        }

        adapter.setEntities(entities);
		adapter.notifyDataSetChanged();
		
//		adapter=new PatronIndexAdapter(this, entities);
//       this.setListAdapter(adapter);
	}
	
	
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(msg.what == RESULT_OK)				// handler says data ready for patrons
			{
				if(Debug.LOG)
				{
//					Log.d( TAG, "Patron Data downloaded and ready");
				}
				
				processUpdate();
			}
		}
	};
	
////////////// background update of data is here /////////////////////////////
	
	private PatronUpdateRunnable patronUpdateRunnable;
	private ScheduledFuture<?> patronHandle;

	private void startPatronUpdate()
	{
		retriggerPatronUpdate = true;
		
		if(Debug.LOG)
		{
			Log.d(TAG, "Starting patronUpdateRunnable");
		}
		
		postDialog();
		patronUpdateRunnable = new PatronUpdateRunnable(this);

		patronHandle = appcommonInstance.schedulerPool.schedule
								(patronUpdateRunnable, Constants.UPDATE_DELAY, TimeUnit.MILLISECONDS);
	}

	
	private void stopPatronUpdate()
	{
		if( dialog != null)
		{
			dialog.dismiss();
			dialog = null;
		}

		if( patronUpdateRunnable == null)
		{
	       	throw new RuntimeException("patronUpdateRunnable null");
		}
		
		if(Debug.LOG)
		{
			Log.d(TAG, "Halting patronUpdateRunnable");
		}
		
		retriggerPatronUpdate = false;
		patronHandle.cancel(false);	// kill task immediately (true)
	}
	
	private void refreshPatronUpdate()
	{
		if(retriggerPatronUpdate == false)  // we don't know how to kill this process, do not restart
		{
			return;
		}
		
		try
		{
			if(Debug.LOG)
			{
				Log.d(TAG, "resked patronUpdateRunnable to fire in " + appcommonInstance.getConfiguration().getPatronUpdateRate());
			}
			
			patronHandle = appcommonInstance.schedulerPool.schedule
						(patronUpdateRunnable, appcommonInstance.getConfiguration().getPatronUpdateRate(), TimeUnit.MILLISECONDS);
		}
		catch( NullPointerException e)
		{
			if(Debug.LOG)
			{
				Log.d(TAG, "getUserUpdate attempted to access null patronUpdateRunnable");
			}
		}
	}

	private void postDialog()
	{	
		if( entities.size() > 0 )
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
	
  
}