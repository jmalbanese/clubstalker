/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clubzoni;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.clubzoni.R;

import com.evilkittyrpg.constants.Constants;
import android.content.Intent;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.dataManagers.EscortPreferenceManager;
import com.evilkittyrpg.dataobjects.Entity;
import com.evilkittyrpg.dataobjects.MediaElements;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.ExternalIntent;
import com.fedorvlasov.lazylist.ImageLoader;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MediaPlayerVideoActivity extends Activity implements OnBufferingUpdateListener, OnCompletionListener,
		OnPreparedListener, OnVideoSizeChangedListener, OnErrorListener, SurfaceHolder.Callback
{

	private static final String TAG = "MediaPlayerActivity";
	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;
	private String path;
	private Bundle extras;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;

	private ImageLoader imageLoader;
	private TextView textViewName;
	// private TextView textViewRegion;
	private ImageView imageView;
	private Button buttonMapPhone;
	private Button buttonMapShare;
	private Button buttonMapEmail;
	private ProgressDialog progressDialog;
	private Button buttonPlay;

	private EscortPreferenceManager preferenceManager;
	private ExternalIntent externalIntent;
	MediaElements mels;
	Entity entity;
	AppCommon appcommonInstance;

	private LinearLayout surfaceLayout;
	private SurfaceView surfaceView;

	public MediaPlayerVideoActivity() {
		appcommonInstance = AppCommon.getInstance(); // so we can look at the
														// data.
	}

	/**
	 * 
	 * Called when the activity is first created.
	 */

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mediaplayervideo);

		// mPreview = (SurfaceView) findViewById(R.id.mediaPlayerView);
		externalIntent = new ExternalIntent(this);
		imageLoader = new ImageLoader(this);
		preferenceManager = new EscortPreferenceManager(this);
		postProgressDialog();
		handleIntent(getIntent());
	}

	/*
	 * // Create the adView adView = new AdView(this, AdSize.BANNER,
	 * "a14e9a1705a58f1");
	 * 
	 * // Lookup your LinearLayout assuming it’s been given // the attribute
	 * android:id="@+id/mainLayout" LinearLayout layout =
	 * (LinearLayout)findViewById(R.id.linearLayout99);
	 * 
	 * // Add the adView to it layout.addView(adView);
	 * 
	 * // Initiate a generic request to load it with an ad adView.loadAd(new
	 * AdRequest());
	 */

	@Override
	public void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent)
	{
		String uuid;

		extras = intent.getExtras();

		if (extras != null)
		{
			uuid = extras.getString(Constants.ENTITY_TYPE_UUID);
			entity = appcommonInstance.getVenues().findByUuid(uuid);
			mels = entity.getMediaElements().getAllByType(Constants.MEDIAELEMENT_TYPE_VIDEO);
			path = mels.get(0).getUri();
			initControls();
			postToDetailsBar(entity);
		}
	}

	private void postProgressDialog()
	{
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setTitle(getString(R.string.DOWNLOADVIDEO));
		progressDialog.setMessage(getString(R.string.GETTING_VIDEO));
		progressDialog.setIndeterminate(true);
		progressDialog.show();
	}

	/**
	 * Inits the controls. Search button is wired to loadData for test.
	 */
	private void initControls()
	{
		buttonPlay = (Button) findViewById(R.id.play);
		buttonPlay.setEnabled(true);

		buttonMapPhone = (Button) findViewById(R.id.buttonMapPhone);
		buttonMapPhone.setEnabled(false);

		buttonMapShare = (Button) findViewById(R.id.buttonMapShare);
		buttonMapShare.setEnabled(false);

		buttonMapEmail = (Button) findViewById(R.id.buttonMapEmail);
		buttonMapEmail.setEnabled(false);

		textViewName = (TextView) findViewById(R.id.name_stage);
		// textViewRegion = (TextView) findViewById(R.id.region);

		imageView = (ImageView) findViewById(R.id.image);

	} // end init controls

	// post the entity to the top bar...

	private void postToDetailsBar(final Entity entity)
	{
		if (entity != null)
		{
			// first we get the image...
			imageLoader.DisplayImage(entity.getPixPath(), imageView, Constants.MAPVIEW_SIZE + 10);
			textViewName.setText(entity.getName_stage());
			// String location = TextUtils.formRegion(entity);
			// textViewRegion.setText(location);

			buttonPlay.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					onPlayAgain();
				}
			});

			// mPreview.setOnClickListener(new View.OnClickListener()
			// {
			// public void onClick(View v)
			// {
			// onPlayAgain();
			// }
			// });

			buttonPlay.setEnabled(true);
			// mPreview.setEnabled(true);

			// header.setOnClickListener(new View.OnClickListener()
			// {
			// @Override
			// public void onClick(View v)
			// {
			// onDetailsViewRequested(entity);
			// }
			// });
			// header.setEnabled(true);

			buttonMapPhone.setEnabled(true);

			if (!entity.getEnable_phone_contact().equals(Constants.ENABLED))
			{
				buttonMapPhone.setEnabled(false);
			}

			buttonMapPhone.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					externalIntent.onPhoneRequested(entity);
				}
			});

			buttonMapEmail.setEnabled(true);

			if (!entity.getEnable_email().equals(Constants.ENABLED))
			{
				buttonMapEmail.setEnabled(false);
			}

			buttonMapEmail.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					externalIntent.onEmailRequested(entity);
				}
			});

			// sms
			buttonMapShare.setEnabled(true);

			// if(!entity.getEnable_sms().equals(Constants.ENABLED))
			// {
			// buttonMapSms.setEnabled(false);
			// }

			buttonMapShare.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					onShareRequested();
				}
			});

		}
	} // end init controls

	// private void onDetailsViewRequested(Entity entity)
	// {
	// Intent intent = new Intent();
	// intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
	// intent.setClass(this,PatronDetailsActivity.class);
	// startActivity(intent);
	// }

	protected void onShareRequested()
	{
		externalIntent.onShareRequested(AppCommon.getInstance().getClientData());
	}

	// private void onSmsRequested(Entity entity)
	// {
	// Intent intent = new Intent();
	// intent.putExtra(Constants.ENTITY_TYPE_UUID, entity.getUuid());
	// intent.setClass(this,SmsActivity.class);
	// startActivity(intent);
	// }

	private void onPlayAgain()
	{

		if (mMediaPlayer.isPlaying()) // we are playing toggle to pause
		{
			mMediaPlayer.pause();
			return;
		}

		if (this.mIsVideoReadyToBePlayed == false)
		{
			return;
		}

		startVideoPlayback();
	}

	private void playVideo()
	{
		doCleanUp();
		try
		{
			// Create a new media player and set the listeners
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnErrorListener(this);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		} catch (Exception e)
		{
			if (Debug.LOG)
			{
				Log.e(TAG, "error: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent)
	{
		// if(Debug.LOG)
		// {
		// Log.d(TAG, "onBufferingUpdate percent:" + percent);
		// }
	}

	public void onCompletion(MediaPlayer arg0)
	{
		if (Debug.LOG)
		{
			Log.d(TAG, "onCompletion called");
		}
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
	{
		if (Debug.LOG)
		{
			Log.v(TAG, "onVideoSizeChanged called");
		}

		if (width == 0 || height == 0)
		{
			if (Debug.LOG)
			{
				Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
			}
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;

		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown)
		{
			startVideoPlayback();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer)
	{
		if (Debug.LOG)
		{
			Log.d(TAG, "onPrepared called");
		}

		mIsVideoReadyToBePlayed = true;

		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}

		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown)
		{
			startVideoPlayback();
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k)
	{
		if (Debug.LOG)
		{
			Log.d(TAG, "surfaceChanged called");
		}

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder)
	{
		if (Debug.LOG)
		{
			Log.d(TAG, "surfaceDestroyed called");
		}
	}

	public void surfaceCreated(SurfaceHolder holder)
	{
		if (Debug.LOG)
		{
			Log.d(TAG, "surfaceCreated called");
		}

		int videoWidth = 720;
		int videoHeight = 480;

		// Get the width of the screen
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();

		// Get the SurfaceView layout parameters
		android.view.ViewGroup.LayoutParams lp = mPreview.getLayoutParams();

		// Set the width of the SurfaceView to the width of the screen
		lp.width = screenWidth;

		// Set the height of the SurfaceView to match the aspect ratio of the
		// video
		// be sure to cast these as floats otherwise the calculation will likely
		// be 0
		lp.height = (int) (((float) videoHeight / (float) videoWidth) * (float) screenWidth);

		// Commit the layout parameters
		mPreview.setLayoutParams(lp);
		playVideo();
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
	protected void onPause()
	{
		super.onPause();
		releaseMediaPlayer();
		doCleanUp();
		surfaceLayout.removeView(surfaceView);
	}

	/*
	 * The final call you receive before your activity is destroyed. This can
	 * happen either because the activity is finishing (someone called finish()
	 * on it, or because the system is temporarily destroying this instance of
	 * the activity to save space. You can distinguish between these two
	 * scenarios with the isFinishing() method.
	 */

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		releaseMediaPlayer();
		doCleanUp();
	}

	private void releaseMediaPlayer()
	{
		if (mMediaPlayer != null)
		{
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void doCleanUp()
	{
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback()
	{
		if (Debug.LOG)
		{
			Log.v(TAG, "startVideoPlayback");
		}

		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}

	/*
	 * Called when the activity will start interacting with the user. At this
	 * point your activity is at the top of the activity stack , with user input
	 * going to it. Always followed by onPause().
	 */

	@Override
	public void onResume()
	{
		super.onResume();
		applyPreferences();

		surfaceLayout = (LinearLayout) findViewById(R.id.surfaceLayout);
		surfaceView = new SurfaceView(this);
		surfaceView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		surfaceLayout.addView(surfaceView);
		mPreview = surfaceView;
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
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

	private void applyPreferences()
	{
		preferenceManager.getSat();
		preferenceManager.getTraffic();
		preferenceManager.getGpsLoc();
	}

	/**
	 * Show error.
	 * 
	 * @param message
	 *            the message
	 */
	// private void showError(String message)
	// {
	// TextUtils.showMessage(this, message);
	// }

}
