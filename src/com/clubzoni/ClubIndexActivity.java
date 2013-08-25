package com.clubzoni;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.clubzoni.R;
import com.fedorvlasov.lazylist.ClubIndexAdapter;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;

import android.content.Intent;

import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.runnables.MetricRunnable;
import com.evilkittyrpg.utils.WebViewDialog;

public class ClubIndexActivity extends IndexCommon
{
	@SuppressWarnings("unused")
	private static String TAG = "ClubIndexActivity";
    ListView list;
    ClubIndexAdapter adapter;
    private WebViewDialog wvd;
    
    public ClubIndexActivity()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setup our references
        setContentView(R.layout.main);		// shares this with all other lists
        entities = appcommonInstance.getVenues().findByType(Constants.ENTITY_TYPE_CLUB);
        adapter=new ClubIndexAdapter(this, entities);   // each list adapter is unique for the data
        this.setListAdapter(adapter);
		handleIntent(getIntent());
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
//			String query = intent.getData().toString();
			// what we do here? - ignore
		}
	}

	// click controls here
	@Override
	protected void onListItemClick(ListView l, View v, int position, long Id )
	{
			Entity entity = entities.get(position);
			
			if( entity.isPrivate())
			{
				return;
			}
			
	        Intent intent = new Intent();
	        intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
	        intent.setClass(this,ClubDetailsActivity.class);  // shares with massage
	        startActivity(intent);
	} ;

	

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
	public void onResume()
	{
		super.onResume();

		if( AppCommon.getInstance().getWebShowCount() > 0)
		{
			return;
		}

		if(AppCommon.getInstance().getConfiguration().getShowAnnoucment().equalsIgnoreCase(Constants.ENABLED))
		{
			String url = AppCommon.getInstance().getConfiguration().getAnnoucementUrl();
			wvd = new WebViewDialog(this, url);
			if( wvd.open() == true)
			{ 
			    AppCommon.getInstance().setWebShowCount( 1 );
		    	appcommonInstance.execPool.execute( new MetricRunnable( AppCommon.getInstance().getClientData().getUuid(), url,  " url posted"));
				wvd.show(); 
			}
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		if(wvd != null)
		{
			if(wvd.getDialog() != null)
			{
				if( wvd.getDialog().isShowing())
				{
					wvd.destroy();
					wvd = null;
				}
			}
		}
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
 
}