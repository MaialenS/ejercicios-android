package com.maialen.mapas;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MapaActivity extends Activity {
	static final LatLng PERTH = new LatLng(43.308110700000000000, -1.891282300000057100);
	MapFragment mapaF;
	GoogleMap mapa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

		
		//obtener el fragmento que tiene el mapa  MapFragment
		
		FragmentManager fManager=getFragmentManager();
		mapaF=(MapFragment)fManager.findFragmentById(R.id.map);

		mapa = mapaF.getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		
	
		Marker marca = mapa.addMarker(new MarkerOptions()
        .position(PERTH)
        .draggable(true)
        );
		
		mapa.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marca) {
				// TODO Auto-generated method stub
				
				Log.d("ACT", "Click en la marca, id -->"+marca.getId());
				
				
				return true;
			}
		});
		
		
		
		mapa.animateCamera(CameraUpdateFactory.newLatLng(PERTH));
		
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_mapa, container,
					false);
			return rootView;
		}
	}

}
