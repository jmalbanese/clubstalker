package com.fedorvlasov.lazylist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.clubzoni.R;

import com.evilkittyrpg.application.AppCommon;
import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.dataobjects.Entities;
import com.evilkittyrpg.utils.TextUtils;

public class PatronIndexAdapter extends BaseAdapter {
    
	@SuppressWarnings("unused")
	private String TAG = "PatronIndexAdapter";
    private Activity activity;
    private Entities entities;
    private static LayoutInflater inflater=null;
    
    public PatronIndexAdapter(Activity a, Entities e) {
        activity = a;
        entities=e;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setEntities(Entities e )
    {
    	entities = e;
    }
    
    @Override
    public int getCount() {
        return entities.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    // view is an interface
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        View vi=convertView;
        
        if(convertView==null)
            vi = inflater.inflate(R.layout.patronitem, null); // item defines a "text" and "image" view

        TextView name_stage=(TextView)vi.findViewById(R.id.name_stage); // find the view for text in vi by id "text"
        name_stage.setText(entities.get(position).getName_stage());		// format stage name

        TextView region = (TextView)vi.findViewById(R.id.region);
        region.setText(entities.get(position).getRegion());
        
        TextView comment=(TextView)vi.findViewById(R.id.comment);  		// find the view for text in vi by id "text"
        String theComments = TextUtils.truncateWithDots(entities.get(position).getComments(), Constants.INDEX_TEXT_NUM_CHARS);
        comment.setText(theComments);			// format stage name
        
        ImageView image=(ImageView)vi.findViewById(R.id.image);					// same for the inflated image
        AppCommon.getInstance().getImageLoader().DisplayImage(entities.get(position).getPixPath(), image, Constants.THUMBNAIL_SIZE);
        return vi;
    } 
	

    
 
}