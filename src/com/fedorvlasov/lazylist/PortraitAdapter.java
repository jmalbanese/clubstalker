package com.fedorvlasov.lazylist;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
//import android.widget.Gallery;
import android.widget.ImageView;
import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.MediaElement;
import com.evilkittyrpg.dataobjects.MediaElements;
import com.evilkittyrpg.utils.Debug;


public class PortraitAdapter extends BaseAdapter {
    
    private Activity activity;
    private MediaElements mels;
    private static LayoutInflater inflater=null;
//    private float mDensity;
    
    private static final String TAG = "PortraitAdapter";
    
    public PortraitAdapter(Activity a, MediaElements m) {
        activity = a;
        mels = m;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mels.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        String pixuri;
        MediaElement mel;
        
        if(convertView==null)
        {
            vi = inflater.inflate(R.layout.portraititem, null); // item defines a "text" and "image" view
        }
        
//        vi.setLayoutParams(new Gallery.LayoutParams((int)(355 * mDensity), (int) (503* mDensity)));
        
        ImageView image=(ImageView)vi.findViewById(R.id.portraitimage);					// same for the inflated image
       
        mel = mels.get(position);
		if(mel==null)
		{
			if(Debug.LOG)
			{
				Log.e(TAG, "No media present");
			}
			
			pixuri = new String( AppCommon.getInstance().getConfiguration().getServer_http() + Constants.GET_NO_FULL);
		}
		else
		{
			pixuri = mel.getUri();
		}

		AppCommon.getInstance().getImageLoader().DisplayImage(pixuri, image, Constants.PORTRIAT_SIZE);			// stuff in the image 
		
        return vi;
    }
}