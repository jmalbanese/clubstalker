package com.clubzoni;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.clubzoni.R;
import com.cookbook.register.Register;
import com.evilkittyrpg.runnables.UpdateUserRunnable;
import com.evilkittyrpg.runnables.UploadPictureRunnable;
import com.evilkittyrpg.utils.Debug;
import com.evilkittyrpg.utils.ExternalIntent;
import com.evilkittyrpg.utils.ImageUtils;
import com.evilkittyrpg.utils.TextUtils;

public class ProfileActivity extends Activity // DetailsCommon
{
	private final String TAG = "ProfileActivity";
	private final int PHOTO_REQUEST = 5;
	private final int GALLERY_REQUEST = 6;

	private ImageView imageView;
	private Button buttonCamera;
	private Button buttonYouPick;
	private Button saveProfile;
	private Button buttonCommentSend;
	private TextView textViewName;
	private TextView textViewComments;
	private TextView textViewPhone;
	private RadioButton radioButtonMale;
	private RadioButton radioButtonFemale;
	private RadioButton radioButtonFreezeOut;
	private RadioButton radioButtonAnonymous;
	private RadioButton radioButtonThousandYardStare;
	private RadioButton radioButtonShowItAll;
	private RadioButton radioButtonNukeMe;
	private CheckBox checkBoxEnablePhone;
	private CheckBox checkBoxEnableTwitter;

	private TextView textViewTwitter;
	@SuppressWarnings("unused")
	private ExternalIntent externalIntent;
	private Bitmap bMap;

	public ProfileActivity() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profileactivity);
		handleIntent(getIntent());
	}

	@Override
	public void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}

	protected void handleIntent(Intent intent)
	{
		@SuppressWarnings("unused")
		Bundle extras = intent.getExtras();
		externalIntent = new ExternalIntent(this);
	}

	private void postToDisplay()
	{
		// String pixuri;
		Bitmap bMap;

		imageView = (ImageView) findViewById(R.id.image);

		bMap = ImageUtils.getBitmapFromFile(Constants.CLIENT_HEADSHOT_FILENAME, this); // get
																						// it
																						// from
																						// general
																						// dir
																						// for
																						// get
																						// or
																						// forever?
		// bMap =
		// ImageUtils.getBitmapFromFile(Constants.CLIENT_HEADSHOT_FILENAME,
		// null); // get it from general dir for get or forever?

		if (bMap != null)
		{
			imageView.setImageBitmap(bMap);
		}

		// we use whatever is in the layout if we have no head shot

		// uncomment if we want to go back to loading the "not found" default
		// else // fall back to a stub
		// {
		// pixuri = new String(Constants.NO_FULL);
		// imageLoader.DisplayImage(pixuri, (ImageView)
		// findViewById(R.id.image), Constants.DETAIL_LANDSCAPE_SIZE);
		// }

		initControls();
	}

	/**
	 * Inits the controls.
	 */
	private void initControls()
	{
		try
		{

			textViewName = (TextView) findViewById(R.id.profileNameEdit);
			textViewName.setText(AppCommon.getInstance().getClientData().getName_stage());

			textViewTwitter = (TextView) findViewById(R.id.profileTwitterEdit);
			textViewTwitter.setText(AppCommon.getInstance().getClientData().getTwitter());

			textViewComments = (TextView) findViewById(R.id.profileCommentEdit);
			textViewComments.setText(AppCommon.getInstance().getClientData().getComments());

			textViewPhone = (TextView) findViewById(R.id.profilePhoneEdit);
			textViewPhone.setText(AppCommon.getInstance().getClientData().getPhoneContact());

			checkBoxEnableTwitter = (CheckBox) findViewById(R.id.enableTwitter);
			checkBoxEnablePhone = (CheckBox) findViewById(R.id.enablePhone);

			checkBoxEnableTwitter.setChecked(AppCommon.getInstance().getClientData().getEnableTwitter()
					.equalsIgnoreCase(Constants.ENABLED));
			checkBoxEnablePhone.setChecked(AppCommon.getInstance().getClientData().getEnablePhone()
					.equalsIgnoreCase(Constants.ENABLED));

		} catch (Exception e)
		{
			if (Debug.LOG)
			{
				Log.d(TAG, " looking for a null pointer here");
			}
		}

		// call external intent on current client data
		buttonCommentSend = (Button) findViewById(R.id.commentSendButton);
		buttonCommentSend.setEnabled(true);

		buttonCommentSend.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				saveProfile();
			}
		});

		buttonCamera = (Button) findViewById(R.id.profileCameraButton);
		buttonCamera.setEnabled(true);

		buttonYouPick = (Button) findViewById(R.id.profileYouPickButton);
		buttonYouPick.setEnabled(true);

		// radioSexGroup = (RadioGroup) findViewById(R.id.radioSexGroup);
		radioButtonMale = (RadioButton) findViewById(R.id.radioMale);
		radioButtonFemale = (RadioButton) findViewById(R.id.radioFemale);

		radioButtonFreezeOut = (RadioButton) findViewById(R.id.radioFreezeOut);
		radioButtonAnonymous = (RadioButton) findViewById(R.id.radioAnonymous);
		radioButtonThousandYardStare = (RadioButton) findViewById(R.id.radioThousandYardStare);
		radioButtonShowItAll = (RadioButton) findViewById(R.id.radioShowItAll);
		radioButtonNukeMe = (RadioButton) findViewById(R.id.radioNukeMe);

		String f = (AppCommon.getInstance().getClientData().getSex());

		radioButtonFemale.setChecked(f.equalsIgnoreCase(Constants.CLIENT_SEX_FEMALE));
		radioButtonMale.setChecked(f.equalsIgnoreCase(Constants.CLIENT_SEX_MALE));

		String p = AppCommon.getInstance().getClientData().getPrivacy();

		radioButtonFreezeOut.setChecked(p.equalsIgnoreCase(Constants.CLIENT_PRIVACY_FREEZE_OUT));
		radioButtonAnonymous.setChecked(p.equalsIgnoreCase(Constants.CLIENT_PRIVACY_ANONYMOUS));
		radioButtonThousandYardStare.setChecked(p.equalsIgnoreCase(Constants.CLIENT_PRIVACY_THOUSAND_YARD_STARE));
		radioButtonShowItAll.setChecked(p.equalsIgnoreCase(Constants.CLIENT_PRIVACY_SHOW_IT_ALL));
		radioButtonNukeMe.setChecked(p.equalsIgnoreCase(Constants.CLIENT_PRIVACY_NUKE_ME));

		buttonCamera.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				dispatchTakePictureIntent(PHOTO_REQUEST);
			}
		});

		imageView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dispatchTakePictureIntent(PHOTO_REQUEST);
			}
		});

		buttonYouPick.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				dispatchGetPictureFromGalleryIntent(GALLERY_REQUEST);
			}
		});

		saveProfile = (Button) findViewById(R.id.saveProfile);
		saveProfile.setEnabled(true);

		saveProfile.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				saveProfile();
			}
		});
	}

	private void dispatchTakePictureIntent(int actionCode)
	{
		if (Debug.LOG)
		{
			Log.d(TAG, " on camera requesed called");
		}

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, actionCode);
	}

	private void dispatchGetPictureFromGalleryIntent(int actionCode)
	{
		if (Debug.LOG)
		{
			Log.d(TAG, " on get a pix from gallery called");
		}

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, actionCode);
	}

	/**
	 * On resume will be called immediately after this...
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode != RESULT_OK)
		{
			if (Debug.LOG)
			{
				Log.d(TAG, "Result code failed ");
			}

			return;
		}

		if (requestCode == PHOTO_REQUEST)
		{
			Bundle extras = data.getExtras();
			bMap = (Bitmap) extras.get("data");
			ImageUtils.saveAsJpgToStorage(bMap, Constants.CLIENT_HEADSHOT_FILENAME, this); // save
																							// to
																							// app
																							// private
			// ImageUtils.saveAsJpgToStorage(bMap,
			// Constants.CLIENT_HEADSHOT_FILENAME, null); // save to general dir
			// ( so we can see them with other tools )
		}

		if (requestCode == GALLERY_REQUEST)
		{
			String selectedImagePath;
			Uri selectedImageUri = data.getData();
			selectedImagePath = getPath(selectedImageUri);
			bMap = ImageUtils.getBitmapFromImagePath(selectedImagePath);
			ImageUtils.saveAsJpgToStorage(bMap, Constants.CLIENT_HEADSHOT_FILENAME, this); // save
																							// to
																							// app
																							// private
			// ImageUtils.saveAsJpgToStorage(bMap,
			// Constants.CLIENT_HEADSHOT_FILENAME, null); // save to general dir
			// ( so we can see them with other tools )
		}
	}

	// UPDATED!
	private String getPath(Uri uri)
	{
		String[] projection =
		{ MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null)
		{
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		else
		{
			return null;
		}
	}

	private void saveProfile()
	{
		if (Debug.LOG)
		{
			Log.d(TAG, "Saving Profile");
		}

		bMap = ImageUtils.getBitmapFromFile(Constants.CLIENT_HEADSHOT_FILENAME, this); // get
																						// it
																						// from
																						// general
																						// dir
																						// for
																						// get
																						// or
																						// forever?
		// bMap =
		// ImageUtils.getBitmapFromFile(Constants.CLIENT_HEADSHOT_FILENAME,
		// null); // get it from general dir for get or forever?

		if (bMap != null)
		{
			UploadPictureRunnable runnable = new UploadPictureRunnable(bMap, Constants.CLIENT_HEADSHOT_FILENAME, AppCommon
					.getInstance().getClientData().getUuid());
			AppCommon.getInstance().execPool.execute(runnable); // send stuff to
																// server,
																// register if
																// success
			AppCommon.getInstance().getImageLoader().clearAllCaches(); // force
																		// upload
																		// of
																		// new
																		// images
		}

		Editor editor = AppCommon.getInstance().getSharedPreferences().edit();

		editor.putString(Constants.CLIENT_NAME_STAGE, textViewName.getText().toString());
		editor.putString(Constants.CLIENT_COMMENT, textViewComments.getText().toString());
		editor.putString(Constants.CLIENT_PHONE_CONTACT, textViewPhone.getText().toString());
		editor.putString(Constants.CLIENT_TWITTER, textViewTwitter.getText().toString());

		String x = Constants.DISABLED;

		if (checkBoxEnablePhone.isChecked())
		{
			x = Constants.ENABLED;
		}

		editor.putString(Constants.CLIENT_ENABLE_PHONE, x);

		x = Constants.DISABLED;

		if (checkBoxEnableTwitter.isChecked())
		{
			x = Constants.ENABLED;
		}
		editor.putString(Constants.CLIENT_ENABLE_TWITTER, x);

		String _sex = Constants.CLIENT_SEX_FEMALE;
		if (this.radioButtonMale.isChecked())
		{
			_sex = Constants.CLIENT_SEX_MALE;
		}

		editor.putString(Constants.CLIENT_SEX, _sex);

		if (radioButtonFreezeOut.isChecked())
		{
			editor.putString(Constants.CLIENT_PRIVACY, Constants.CLIENT_PRIVACY_FREEZE_OUT);
		}

		if (radioButtonAnonymous.isChecked())
		{
			editor.putString(Constants.CLIENT_PRIVACY, Constants.CLIENT_PRIVACY_ANONYMOUS);
		}

		if (radioButtonThousandYardStare.isChecked())
		{
			editor.putString(Constants.CLIENT_PRIVACY, Constants.CLIENT_PRIVACY_THOUSAND_YARD_STARE);
		}

		if (radioButtonShowItAll.isChecked())
		{
			editor.putString(Constants.CLIENT_PRIVACY, Constants.CLIENT_PRIVACY_SHOW_IT_ALL);
		}

		if (radioButtonNukeMe.isChecked())
		{
			onNukeIsChecked();
		}
		else
		{
			showMessage(getString(R.string.uploading_profile));
			editor.commit();
			AppCommon.getInstance().getClientData().updateFromPrefs(); // tell
																		// app
																		// common
																		// to
																		// refresh,
																		// setting
																		// up
																		// location
																		// services
																		// to
																		// run
																		// etc
			UpdateUserRunnable uur = new UpdateUserRunnable(); // we push
																// immediately
																// don't wait
																// for refresh
																// cycle
			AppCommon.getInstance().execPool.execute(uur); // send stuff to
															// server
		}
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

	@Override
	public void onResume()
	{
		super.onResume();
		postToDisplay();
	}

	AlertDialog.Builder builder;
	AlertDialog alert;
	Activity activity;

	@Override
	public void onBackPressed()
	{
		if (Debug.LOG)
		{
			Log.d(TAG, "Back Pressed");
		}

		activity = this;
		buildAYSDialog();
		alert = builder.create();
		alert.show();
	}

	private void buildAYSDialog()
	{
		builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.exit_clubzoni)).setCancelable(false)
				.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						Editor editor = AppCommon.getInstance().getSharedPreferences().edit();
						editor.putString(Constants.CLIENT_PRIVACY, Constants.CLIENT_PRIVACY_NUKE_ME);
						activity.finish();
					}
				}).setNegativeButton(getString(R.string.NO), new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.cancel();
					}
				});
	}

	private void onNukeIsChecked()
	{
		if (Debug.LOG)
		{
			Log.d(TAG, "Nuke Enabled");
		}

		buildNukeDialog();
		activity = this;
		alert = builder.create();
		alert.show();
	}

	private void buildNukeDialog()
	{
		builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.NUKE_ME_DIALOG_TEXT)).setCancelable(false)

		.setPositiveButton(getString(R.string.NUKE_ME_NOW), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				Register.UnRegister();
				activity.finish();
			}
		})

		.setNegativeButton(getString(R.string.NUKE_ME_NO), new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				dialog.cancel();
			}
		});
	}

	/**
	 * Show messenger.
	 * 
	 * @param message
	 *            the message
	 */
	private void showMessage(String message)
	{
		TextUtils.showMessage(this, message);
	}
}
