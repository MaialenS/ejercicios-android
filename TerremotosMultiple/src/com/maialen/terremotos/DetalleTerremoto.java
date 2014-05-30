package com.maialen.terremotos;

import com.maialen.preferencias.PreferenciasActivity;

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
import android.os.Build;

public class DetalleTerremoto extends Activity {
	private static final String TAG = "Terremotos";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_terremoto);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new FragmentDetalleTerremoto()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_terremoto, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, PreferenciasActivity.class);
			startActivity(i);
			
			return true;
		}else if(id== android.R.id.home){
	    	
	    	 Log.d(TAG, "volver");
	    	 super.onBackPressed();
	         return true;
	    }else if(id== R.id.action_share){
	    	
	    	 Log.d(TAG, "SHARE");
	    	 
	    	 ((FragmentDetalleTerremoto)(getFragmentManager().findFragmentById(R.id.container))).compartirUrl();
	    	 
	         return true;
	    }
		
		
		
		return super.onOptionsItemSelected(item);
	}

	

}
