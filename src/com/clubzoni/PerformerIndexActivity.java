package com.clubzoni;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.clubzoni.R;
import com.fedorvlasov.lazylist.ClubIndexAdapter;
import com.evilkittyrpg.constants.Constants;
import android.content.Intent;

import com.evilkittyrpg.dataobjects.Entity;


public class PerformerIndexActivity extends IndexCommon
{
    ListView list;
    ClubIndexAdapter adapter;
    
    public PerformerIndexActivity()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setup our references
        setContentView(R.layout.main);		// shares this with all other lists
        entities = appcommonInstance.getVenues().findByType(Constants.ENTITY_TYPE_MASSAGE);
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

	        Intent intent = new Intent();
	        intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
	        intent.setClass(this,PeformerDetailsActivity.class);
	        startActivity(intent);
	} ;
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
	
	
	/**
	 * Show error.
	 * 
	 * @param message
	 *            the message
	 */
//	private void showError(String message)
//	{
//			TextUtils.showMessage(this, message);
//	}

//    public OnClickListener listener=new OnClickListener(){
//        @Override
//        public void onClick(View arg0) {
//            AppCommon.getInstance().getImageLoader().clearMemoryCache();
//            adapter.notifyDataSetChanged();
//        }
//    };
// 
}