package com.maialen.terremotos;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.os.Build;

public class MostrarURL extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_url);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostrar_url, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else if(id== android.R.id.home){
	    	 super.onBackPressed();
	         return true;
	    }
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private WebView webView;
		private static final String LA_URL ="la_url";
		
		private String URLstring;
		
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.fragment_mostrar_url,
					container, false);
			
			//pobner el boron de back
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			
			
			
			webView= (WebView) v.findViewById(R.id.webViewDetalle);
			
			
			
			 Intent intent = getActivity().getIntent();
			 if (intent!=null){
				  
				 URLstring=intent.getStringExtra(LA_URL);
				 webView.loadUrl(URLstring);
				 
				 
			 }
			
			
			return v;
		}	
	
	}

}
