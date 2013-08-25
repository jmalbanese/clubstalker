package com.fedorvlasov.lazylist;

import java.io.File;
import java.util.Date;

import com.evilkittyrpg.constants.Constants;

import android.content.Context;

/**
 * @author jmalbanese
 *
 */
public class FileCache {
    
    private File cacheDir;
    
    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"ClubZoniList");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public void DeleteCacheDir(Context context)
    {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"ClubZoniList");
            if(cacheDir.exists())
            {
            	cacheDir.delete();
            }
        }
    }
    
    /**
     * attempt to open file in the filecache
     * 
     * @param url
     * @return handle or null if not present
     */
    public File getFile(String url)
    {
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
    }
    
    public synchronized void  deleteFile(String url )
    {
    	@SuppressWarnings("unused")
		Boolean r = false;
        //I identify images by hash code. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        if(f.exists())
        {
        	r = f.delete();
        }
    }

    public void deleteAllFiles()
    {
        File[] files=cacheDir.listFiles();
        for(File f: files)
        {
    		f.delete(); 
        }
    }
    
    public void clear()
    {
    	int	storageConsumed = 0;;
    	Date dieDate;
    	Date today = new Date();
//    	String sdiedate;
//   	String stoday;
    	
        File[] files=cacheDir.listFiles();
        if(files==null)
        {
            return;
        }

        storageConsumed = 0;
        
        // get rid of the old ones
        
        for(File f: files)
        {
        	dieDate = new Date( f.lastModified() -  Constants.MAX_PIX_AGE_DAYS );
        	
   //     	sdiedate = dieDate.toLocaleString();
   //     	stoday = today.toLocaleString();
 
        	if( today.compareTo(dieDate) > 1) // die date older than today
        	{
            	f.delete(); 
        	}
        }
        
        // now the nuke option
        files=cacheDir.listFiles();
        if(files==null)
        {
            return;
        }

        for(File f: files)
        {
        	storageConsumed+= f.length();

        	if( storageConsumed > Constants.MAX_PIX_STORAGE)
    		{
    			f.delete(); 
    		}
        }
    }

}