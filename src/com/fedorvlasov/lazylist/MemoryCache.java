package com.fedorvlasov.lazylist;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.evilkittyrpg.utils.Debug;

import android.graphics.Bitmap;
import android.util.Log;

public class MemoryCache {
    private Map<String, Bitmap> cache=Collections.synchronizedMap(new HashMap<String, Bitmap>());
    
    public Bitmap get(String id)
    {
        if(!cache.containsKey(id))
            return null;
        Bitmap ref=cache.get(id);
        return ref;
    }
    
    public void put(String id, Bitmap bitmap)
    {
        cache.put(id, bitmap);
        
        if(Debug.LOG)
        {
        	Log.d("MemeCache putting bitmap id", id + "  " +  bitmap.getWidth() + "  " +   bitmap.getHeight());
        }
    }

    public void remove(String id)
    {
    	if(Debug.LOG)
    	{
    		Log.d("MemeCache removing bitmap id", id);
    	}
    	
    	cache.remove(id);
    }

    public void clear() {
        cache.clear();
    }
}