package com.maialen.terremotos;

import java.util.HashMap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.maialen.datos.TerremotosContentProvider;
import com.maialen.preferencias.PreferenciasActivity;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapaFragmentoDetalle extends com.google.android.gms.maps.MapFragment {

	private static final String TAG="Terremotos";
	
	private GoogleMap mapa;

	
	
	public void onActivityCreated (Bundle savedInstanceState){
		
		super.onActivityCreated(savedInstanceState);

		mapa = this.getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		mapa.clear();
	}


	public void ponerMarca(LatLng posicion){
		mapa.clear();
		
		mapa.addMarker(new MarkerOptions()
			        .position(posicion)
			        .draggable(false));
		 mapa.animateCamera(CameraUpdateFactory.newLatLng(posicion));
		
	}
}
