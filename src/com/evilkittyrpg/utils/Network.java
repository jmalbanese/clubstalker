package com.evilkittyrpg.utils;

import com.clubzoni.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network
{
	private Context context;

	public Network(Context val)
	{
		context = val;
	}

	public boolean testNetworkActive()
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

		if (activeNetworkInfo == null)
		{
			buildAlertMessage();
			return false;
		}
		return true;
	}

	private void buildAlertMessage()
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(context.getString(R.string.NET_ERROR_AIRPLANE_MODE))
				.setTitle(context.getString(R.string.NET_ERROR_CANT_CONNECT))
				.setCancelable(false)
				.setNegativeButton(context.getString(R.string.OK), new DialogInterface.OnClickListener()
				{
					public void onClick(final DialogInterface dialog, final int id)
					{
						dialog.cancel();
					}
				});

		final AlertDialog alert = builder.create();
		alert.show();
	}

	/*
	 * private void launchGPSOptions() { final ComponentName toLaunch = new
	 * ComponentName
	 * ("com.android.settings","com.android.settings.SecuritySettings"); final
	 * Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	 * intent.addCategory(Intent.CATEGORY_LAUNCHER);
	 * intent.setComponent(toLaunch);
	 * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 * context.startActivity(intent); }
	 */
}
