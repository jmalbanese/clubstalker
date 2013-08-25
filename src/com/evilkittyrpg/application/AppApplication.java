package com.evilkittyrpg.application;

import android.app.Application;

public class AppApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		initSingletons();
	}

	private void initSingletons()
	{
		AppCommon.initInstance(this);
	}
 	
}
