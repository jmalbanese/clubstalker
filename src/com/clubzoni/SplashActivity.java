package com.clubzoni;

import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataManagers.EscortPreferenceManager;
import com.evilkittyrpg.dataManagers.WebserverDataManager;
import com.evilkittyrpg.interfaces.WebserverDataManagerListener;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.Network;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;


public class SplashActivity extends Activity
							implements WebserverDataManagerListener
{
	private String TAG = "SpashActivity";
	private ProgressDialog dialog;
	private WebserverDataManager em;
	private EscortPreferenceManager preferenceManager;
    AppCommon appcommonInstance;
    
	public SplashActivity()
	{
		em = new WebserverDataManager(this);			// pass us to the manager, SplashActivty (this) implements the call backs
    	appcommonInstance = AppCommon.getInstance();  // so we can look at the data.
    	
    	if(Debug.LOG)
    	{
    		Log.d(TAG, "Device heap available mb :" + Runtime.getRuntime().maxMemory()/1024);
    	}
		
	}


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
   	    super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
 //       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
   	    
		preferenceManager = new EscortPreferenceManager(this);
		appcommonInstance.setLocationCity(preferenceManager.getCity());
		
        // set the layout for this activity
        setContentView(R.layout.splash);
        testNetwork();
        initEntities();
    }    
     
    
	/*
	 * testNetwork
	 * 
	 * Ask Network.java if we have IP
	 * Network.java is responsible for posting
	 * the error db.
	 * 
	 */
	
    boolean netActive = false;
    
	private void testNetwork()
	{
		netActive = false;
		Network net = new Network(this);
		try
		{
			netActive = net.testNetworkActive();
		}
		catch (Exception e)
		{
			e.toString();
		}
	}

	/**
	 * Gets the entity data from database.
	 * 
	 * First ask the entitymanager (em) if we have the data already.
	 * 
	 * Pass a reference to ourselves as an EntityManagerListener so we can get
	 * callbacks to the functions newEntities - to tell us stuff has been loaded
	 * newError - to tell us stuff went wrong
	 */
	private void initEntities()
	{
			loadData();
	}

	/**
	 * Load data.
	 */
	private void loadData()
	{
		String locationCity = appcommonInstance.getLocationCity();
		
		if( locationCity == Constants.CITY_SELECTOR_WILDCARD)
		{
			locationCity = getString(R.string.OPTIONS_SHOW_ALL); 
		}
			
		if (netActive == true) // do we have a connection?
		{
			dialog = new ProgressDialog(this);
			dialog.setTitle(getString(R.string.DOWNLOADTITLE) + " " + locationCity);
			dialog.setMessage(getString(R.string.GETTING_DATA));
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
			LoadingThread process = new LoadingThread(em);
			process.start();
		}
	}

	/**
	 * The Class LoadingThread.
	 * 
	 * Loads all the data and sets up the map element object so we can place
	 * entities at their location(s)
	 * 
	 */
	private class LoadingThread extends Thread
	{
		/** The em. */
		private WebserverDataManager em;

		/**
		 * Instantiates a new loading thread.
		 * @param _em
		 * the _em loads entities, locations and builds the entity list
		 */

		public LoadingThread(WebserverDataManager _em)
		{
			em = _em;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		public void run()
		{
			em.loadData();
		}

	}
// end private class
	
	// callback from Loader when all data is loaded
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.evilkittyrpg.interfaces.EntityManagerListener#newEntities()
	 */

	public void newData() // interface called when WebserverDataManager has completed it's task
	{
		dialog.dismiss();
		// config is loaded, we can init ClientData now.
		
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this,AppTabActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.evilkittyrpg.interfaces.EntityManagerListener#newError(java.lang.
	 * String)
	 */
	
	public void webserverDataManagerError(String message) // interface called if webserverDatatManager throws an error
	{
		Message m = new Message();
		Bundle b = new Bundle();
		b.putString("entity_manager_error", message);
		m.setData(b);
		handler.sendMessage(m);
	}

	/** The handler. */
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if (msg.getData().containsKey("error"))
			{
				showError(msg.getData().getString("error"));
			}
			if (msg.getData().containsKey("entity_manager_error"))
			{
				dialog.dismiss();
				showError(msg.getData().getString("entity_manager_error"));
			}

//			mv.invalidate(); // tell list to refresh here?
		}
	};
	
	/**
	 * Show error.
	 * 
	 * @param message
	 *            the message
	 */
	private void showError(String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		       .setCancelable(false)
		       .setPositiveButton("Close App", new DialogInterface.OnClickListener() 
		       		{
		    	   		public void onClick(DialogInterface dialog, int id) 
		    	   		{
		    	   			finish();
		    	   		}
		       		}
		       )
		       
		        .setNegativeButton("Activate HotSpot", new DialogInterface.OnClickListener() 
		        	{
		        		public void onClick(DialogInterface dialog, int id) 
		        		{
		        			String url = "http://www.google.com";
		        			Intent i = new Intent(Intent.ACTION_VIEW);
		        			i.setData(Uri.parse(url));
		        			startActivity(i);
		        			finish();
		        		}
		        	}
		       );
		AlertDialog alert = builder.create();
		alert.show();
	}
	
// end class   
}
