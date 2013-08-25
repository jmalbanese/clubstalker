package com.evilkittyrpg.utils;

import com.clubzoni.R;
import com.evilkittyrpg.application.AppCommon;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.graphics.Bitmap;
import android.graphics.Color;

public class WebViewDialog
{
	private Context context;
	private Dialog dialog;
	private String url;
	private String title;
	private Button closeButton;
	
	private final static String TAG = "WebViewDialog";
	
	public WebViewDialog(Context _context, int _xmlPullParser, String _url, int _webView, String _title )
	{
		context = _context;
		url = _url;
		title = _title;
	}
	
	public WebViewDialog(Context _context, String _url)
	{
		context = _context;
		url = _url;
	}

	public boolean open()
	{
 		
		if( url == null)
		{
			return false;
		}
		
		if( url.length()== 0)
		{
			return false;
		}


		dialog = new Dialog(context);
	    dialog.setContentView(R.layout.webviewdialog);
	    if( title != null && title.length() > 0)
	    {
	    	dialog.setTitle(title);
	    }
	    
	    dialog.setCancelable(true);
	    
	    WebView webview = (WebView) dialog.findViewById(R.id.webView);
	    webview.setBackgroundColor(Color.BLACK);
	    
	    closeButton = (Button) dialog.findViewById(R.id.close);
	    
	    closeButton.setOnClickListener(	
	    									new OnClickListener()
	    									{
	    										@Override 
	    										public void onClick(View v)
	    										{
	    											dialog.dismiss();
	    										}
	    									}
	    								);
//	    WebView webview = (WebView) dialog.findViewById(webView); // R.id.webview);
	    
	    
//	    webview.getSettings().setJavaScriptEnabled(true);
	    
	    webview.setWebViewClient(new WebViewClient() {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String xurl) 
	        {
	            view.loadUrl(xurl);
	            return false;
	        }
	        
	        @Override
	        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
	        {
	            super.onReceivedError(view, errorCode, description, failingUrl);
	        }

	        @Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon)
	        {
	            super.onPageStarted(view, url, favicon);
	        }

	        @Override
	        public void onPageFinished(WebView view, String url)
	        {
	            super.onPageFinished(view, url);
	            dialog.setTitle(view.getTitle());
	        }

	    });
	    webview.loadUrl(url);
	    if(Debug.LOG)
	    {
	    	Log.d(TAG, "..loading url.." + url);
	    }

	    return true;
	}
	
	public void show()
	{
		dialog.show();
	}

	public void destroy()
	{
		dialog.dismiss();
		dialog = null;
	}
	
	public Dialog getDialog()
	{
		return dialog;
	}
}
