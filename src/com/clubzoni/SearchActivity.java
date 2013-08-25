package com.clubzoni;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.clubzoni.R;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.utils.TextUtils;

public class SearchActivity extends ListActivity
{
	private AppCommon appCommon;
	private ArrayAdapter <String> lvAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		appCommon = AppCommon.getInstance(); // application has the search
		appCommon.setSearchResults(null);
		handleIntent(getIntent());
	}
	
	
	@Override
	protected void onNewIntent( Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent)
	{
		// Get the intent, verify the action and get the query
		if (Intent.ACTION_SEARCH.equals(intent.getAction()))
		{
			    String query = intent.getStringExtra(SearchManager.QUERY);
			    if( getSearchContents(query) == true)
			    {
			    	showSearchListView();
			    }
			    else
			    {
					showError(this.getString(R.string.CANT_FIND_ENTITY_BY_NAME));
					finish();
			    }
		}
	}

	private boolean getSearchContents(String q)
	{
		appCommon.setSearchResults(appCommon.getVenues().findByNameStageContains(q)); // get a list of all map elements that match query

		if (appCommon.getSearchResults().isEmpty()) // post not found here
		{
			return(false);
		}
		return(true);
	}

	/*
	 * Display Search list
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void showSearchListView()
	{
		if( AppCommon.getInstance().getSearchResults() != null)
		{
			lvAdapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1, AppCommon.getInstance().getSearchResults());
			setListAdapter(lvAdapter);
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long Id )
	{
//			Toast.makeText(getApplicationContext(), ((TextView) v).getText(),
//			Toast.LENGTH_SHORT).show();
			
			Entity me = AppCommon.getInstance().getSearchResults().get(position);
			AppCommon.getInstance().getSearchResults().clear();
			AppCommon.getInstance().getSearchResults().add(me);		// TODO:return multiple entries here
			finish();
	} ;

	/**
	 * Show error.
	 * 
	 * @param message
	 *            the message
	 */
	private void showError(String message)
	{
		TextUtils.showMessage(this, message);
	}

}
