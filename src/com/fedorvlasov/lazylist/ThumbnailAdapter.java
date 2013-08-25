package com.fedorvlasov.lazylist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.MediaElements;


public class ThumbnailAdapter extends BaseAdapter {
    
    private Activity activity;
    private MediaElements mels;
    private static LayoutInflater inflater=null;
    
    public ThumbnailAdapter(Activity a, MediaElements m) {
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
    
    // view is an interface
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
        {
            vi = inflater.inflate(R.layout.thumbnailitem, null); // item defines a "text" and "image" view
        }
        
        ImageView image=(ImageView)vi.findViewById(R.id.thumbnailimage);					// same for the inflated image
        AppCommon.getInstance().getImageLoader().DisplayImage(mels.get(position).getUri(), image, Constants.THUMBNAIL_SIZE);			// stuff in the image 
        return vi;
    }
}