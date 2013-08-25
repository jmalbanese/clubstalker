package com.clubzoni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.Entity;

public class SmsActivity extends Activity
{
    private Entity entity;
    private AppCommon appcommonInstance;
    private String addrTxt;
    private EditText msgTxt;
    private Button sendBtn;
    private TextView textViewName;
    private TextView textViewRegion;

    public SmsActivity()
    {
    	appcommonInstance = AppCommon.getInstance();  // so we can look at the data.
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sms);
		handleIntent(getIntent());

        sendBtn = (Button)findViewById(R.id.sendSmsBtn);
        msgTxt = (EditText)SmsActivity.this.findViewById(R.id.msgEditText);
        
        addrTxt = entity.getPhone_contact();
        
        sendBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) 
            {
                try {
                    sendSmsMessage(
                        addrTxt,msgTxt.getText().toString());
                    Toast.makeText(SmsActivity.this, "SMS Sent", 
                	        Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(SmsActivity.this, "Failed to send SMS", 
                	        Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }});
    }

	@Override
	public void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent)
	{
		String uuid;
		Bundle extras = intent.getExtras();
		{
			if( extras != null)
			{
				uuid = extras.getString(Constants.ENTITY_TYPE_UUID);
				entity = appcommonInstance.getVenues().findByUuid(uuid);
				postToDisplay();
			}
		}
	}

	private void postToDisplay()
	{
		if( entity != null)
		{
			// first we get the image...
			AppCommon.getInstance().getImageLoader().DisplayImage(entity.getPixPath(), (ImageView) findViewById(R.id.image), Constants.THUMBNAIL_SIZE);	

			textViewName = (TextView) findViewById(R.id.name_stage);
			textViewName.setText(entity.getName_stage());

			textViewRegion = (TextView) findViewById(R.id.region);
			textViewRegion.setText(entity.getRegion());
		}
		
	}
   
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void sendSmsMessage(String address,String message)throws Exception
    {
        SmsManager smsMgr = SmsManager.getDefault();
        smsMgr.sendTextMessage(address, null, message, null, null);
    }
}

