package com.fedorvlasov.lazylist;

import java.io.InputStream;
import java.io.OutputStream;

import com.evilkittyrpg.utils.Debug;

import android.util.Log;

public class Utils 
{
	static final String TAG = "utils";
	
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex)
        {
        	if(Debug.LOG)
        	{
        		Log.d( TAG, ex.toString());
        	}
        }
    }
}