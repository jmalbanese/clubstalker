package com.evilkittyrpg.utils;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import com.clubzoni.R;

public class Gps
{
	private Context context;

	public Gps(Context val)
	{
		context = val;
	}

	public boolean testGpsActive()
	{
		final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			buildAlertMessageNoGps();
			return false;
		}
		return true;
	}

	private void buildAlertMessageNoGps()
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(context.getString(R.string.GPS_ERROR)).setTitle(context.getString(R.string.GPS_OFF))
				.setCancelable(false)
				.setNegativeButton(context.getString(R.string.OK), new DialogInterface.OnClickListener()
				{
					public void onClick(final DialogInterface dialog, final int id)
					{
						dialog.cancel();
					}
				})
				.setPositiveButton(context.getString(R.string.activate_gps), new DialogInterface.OnClickListener()
				{
					public void onClick( final DialogInterface dialog, final int id)
					{
						dialog.cancel();
						launchGPSOptions();
					}
				}
				);

		final AlertDialog alert = builder.create();
		alert.show();
	}

	
	private void launchGPSOptions() { final ComponentName toLaunch = new 
		ComponentName("com.android.settings","com.android.settings.SecuritySettings"); final
	 
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(toLaunch);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent); }
}
