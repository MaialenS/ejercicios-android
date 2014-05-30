package com.maialen.terremotos;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MapaActivity extends Activity {

	MapFragment mapaF;
	GoogleMap mapa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

		// obtener el fragmento que tiene el mapa MapFragment

		FragmentManager fManager = getFragmentManager();
		mapaF = (MapFragment) fManager.findFragmentById(R.id.map);

		mapa = mapaF.getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mapa, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

}
