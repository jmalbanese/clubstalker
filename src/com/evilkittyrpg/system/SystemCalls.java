package com.evilkittyrpg.system;

import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class SystemCalls
{

  static public String getVersionName( Context context)
	{
		String versionName;
		PackageManager manager = context.getPackageManager();
		PackageInfo info;
		info = null;
		versionName = " ";

		try
		{
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e)
		{
			versionName = versionName + "Unknown";
			return versionName;
		}
		versionName = versionName + info.versionName;
		return versionName;
	}
  
  static public boolean aboutApp(Context context)
  {
	 String aboutMessage;
	final AlertDialog.Builder builder = new AlertDialog.Builder(context);
	
	aboutMessage = (context.getString(R.string.ABOUT_TEXT)) + "\n\nDevice ID\n";
	aboutMessage = aboutMessage +  AppCommon.getInstance().getClientData().getEmail_addr();
	
	builder.setMessage(aboutMessage)
			.setTitle(context.getString(R.string.ABOUT) + SystemCalls.getVersionName(context))
			.setCancelable(false)
			.setNegativeButton(context.getString(R.string.OK),
					new DialogInterface.OnClickListener()
					{
						public void onClick(
								final DialogInterface dialog,
								final int id)
						{
							dialog.cancel();
						}
					});

	final AlertDialog alert = builder.create();
	alert.show();
	return true;
  }
}
