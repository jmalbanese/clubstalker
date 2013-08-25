package com.clubzoni;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.*;

import android.content.Intent;
import com.clubzoni.R;
import com.evilkittyrpg.dataobjects.MediaElement;
import com.evilkittyrpg.utils.Debug;

public class PatronDetailsActivity extends DetailsCommon
{
	private String TAG = "PatronDetailsActivity";

	private ImageView imageView;
	private Button buttonVideo;
	private Button buttonGallery;
	private Button buttonPhone;
	private Button buttonEmail;
	private Button buttonShare;
	private Button buttonMap;

	private TextView textViewComment;
	private TextView textViewName;
	private TextView textViewRegion;
	public PatronDetailsActivity() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patrondetailsactivity);
		handleIntent(getIntent());

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
	}

	@Override
	public void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}

	protected void handleIntent(Intent intent)
	{
		String uuid;
		Bundle extras = intent.getExtras();
		{
			if (extras != null)
			{
				uuid = extras.getString(Constants.ENTITY_TYPE_UUID);
				entity = appcommonInstance.getPatrons().findByUuid(uuid);
				postToDisplay();
				super.handleIntent(intent);
			}
		}
	}

	private void postToDisplay()
	{
		MediaElement mel;
		String pixuri;

		if (entity != null)
		{
			// first we get the image...
			mel = entity.getMediaElements().findByType(
					Constants.MEDIAELEMENT_TYPE_MAIN);
			if (mel == null)
			{
				mel = entity.getMediaElements().findByType(
						Constants.MEDIAELEMENT_TYPE_FULL);
			}

			if (mel == null)
			{
				if(Debug.LOG)
				{
					Log.e(TAG, "No media present for " + entity.getName_stage()	+ " uuid " + entity.getUuid());
				}
				pixuri = new String( AppCommon.getInstance().getConfiguration().getServer_http() + Constants.GET_NO_FULL);
			}
			else
			{
				pixuri = mel.getUri();
			}

			AppCommon.getInstance().getImageLoader().DisplayImage(pixuri, (ImageView) findViewById(R.id.image),Constants.DETAIL_SIZE);

			initControls();
		}
	}

	/**
	 * Inits the controls.
	 */
	private void initControls()
	{

		// the video button

		buttonVideo = (Button) findViewById(R.id.video);

		buttonVideo.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				onVideoRequested();
			}
		});

		if (entity.getMediaElements().findByType(
				Constants.MEDIAELEMENT_TYPE_VIDEO) == null)
		{
			buttonVideo.setEnabled(false);
		}

		// the gallery button - do we have full images for the girl? we have
		// turned off thumbnail view, it's boring

		buttonGallery = (Button) findViewById(R.id.gallery);

		buttonGallery.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				onGalleryRequested();
			}
		});

		if (entity.getMediaElements().findByType(
				Constants.MEDIAELEMENT_TYPE_FULL) == null)
		{
			buttonGallery.setEnabled(false);
		}
		else
		// we have gallery - let the image click too
		{
			// image

			imageView = (ImageView) findViewById(R.id.image);

			imageView.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					onGalleryRequested();
				}
			});
		}

		textViewName = (TextView) findViewById(R.id.name_stage);
		textViewName.setText(entity.getName_stage());

		textViewRegion = (TextView) findViewById(R.id.region);
		textViewRegion.setText(entity.getRegion());

		textViewComment = (TextView) findViewById(R.id.comments);
		textViewComment.setText(entity.getComments());

		// now the phone

		buttonPhone = (Button) findViewById(R.id.phone);
		buttonPhone.setEnabled(true);

		if (!entity.getEnable_phone_contact().equals(Constants.ENABLED))
		{
			buttonPhone.setEnabled(false);
		}

		buttonPhone.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				externalIntent.onPhoneRequested(entity);
			}
		});

		/*
		 * // chat? need a server side - defer for now
		 * 
		 * buttonChat = (Button) findViewById(R.id.chat);
		 * 
		 * if(!entity.getEnable_chat().equals(Constants.ENABLED)) {
		 * buttonChat.setEnabled(false); }
		 * 
		 * buttonChat.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { externalIntent.onChatRequested(); } });
		 */
		// email

		buttonEmail = (Button) findViewById(R.id.email);

		if (!entity.getEnable_email().equals(Constants.ENABLED))
		{
			buttonEmail.setEnabled(false);
		}
		else
		{
			buttonEmail.setEnabled(true);
		}

		buttonEmail.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				externalIntent.onEmailRequested(entity);
			}
		});

		// sms
		buttonShare = (Button) findViewById(R.id.share);
		buttonShare.setEnabled(false);

		if (entity.getEnable_twitter().equals(Constants.ENABLED) )
		{
			buttonShare.setEnabled(true);
		}
		
		buttonShare.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				onShareRequested();
			}
		});

		buttonMap = (Button) findViewById(R.id.map);

		if (!entity.getEnable_location().equals(Constants.ENABLED))
		{
			buttonMap.setEnabled(false);
		}
		buttonMap.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				onMapRequested();
			}
		});
		
	} // end init controls

//	public OnClickListener listener = new OnClickListener()
//	{
//		@Override
//		public void onClick(View arg0)
//		{
//			adapter.imageLoader.clearCache();
//			adapter.notifyDataSetChanged();
//		}
//	};

}