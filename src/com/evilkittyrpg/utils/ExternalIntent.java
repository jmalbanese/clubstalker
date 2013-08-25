package com.evilkittyrpg.utils;

import com.clubzoni.R;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.ClientData;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.runnables.MetricRunnable;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class ExternalIntent
{
	private Context context;
	private String TAG = "ExternalIntent";
	private Thread metricThread;
	
	public ExternalIntent(Context _context)
	{
		context = _context;
	}

	public void onPhoneRequested(Entity entity)
	{	
		String phone = entity.getPhone_contact();
		
		if(phone.trim().length() == 0)
		{
			TextUtils.showMessage(context, context.getString(R.string.NO_PHONE_NUMBER));
			return;
		}
 		
		metricThread = new Thread (new MetricRunnable( entity.getUuid(), this.getClass().getName(), entity.getName_legal() + " called " + phone));
		metricThread.start();

		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		Integer phoneType = tm.getPhoneType();
		
		if( phoneType == TelephonyManager.PHONE_TYPE_NONE)
		{
			Toast t = Toast.makeText(context,R.string.NO_PHONE, Toast.LENGTH_LONG);
			t.show();
			return;
		}
		
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            context.startActivity(callIntent);
        } catch (ActivityNotFoundException e) 
        {
        	if(Debug.LOG)
        	{
        		Log.e(TAG, "Call failed", e);
        	}
        }
		
	}

	public void onChatRequested()
	{
	}

	public void onEmailRequested( Entity entity )
	{
		String emailAddr = entity.getEmail();
		
		metricThread = new Thread(new MetricRunnable( entity.getUuid(), this.getClass().getName(), entity.getName_legal() + " emailed " + emailAddr));
		metricThread.start();

		try
		{
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		String subject = Constants.EMAIL_SUBJECT;
		String body = Constants.EMAIL_BODY;
		String [] extra = new String[] { emailAddr };
		
		emailIntent.putExtra(Intent.EXTRA_EMAIL, extra);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, body);
		emailIntent.setType(Constants.EMAIL_TYPE);
		
		context.startActivity(emailIntent);
		}
		catch(ActivityNotFoundException e)
		{
			if(Debug.LOG)
			{
				Log.e( TAG, "Activity not found" + e);
			}
		}
	}
	
	public void onShareRequested(ClientData cd )
	{
		try
		{
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, cd.getComments());
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ClubZoni");
			context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
		}
		catch (ActivityNotFoundException e) 
		{
			if(Debug.LOG)
			{
				Log.e(TAG, "Share failed", e);
			}
        }
	}
}
