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

public class MapaFragmento extends com.google.android.gms.maps.MapFragment implements LoaderCallbacks<Cursor>{
	static final String TERREMOTO_SELECCIONADO="id_terremoto";
	private static final int LOADER_TERREMOTOS = 3;
	private static final int SHOW_DETAIL=1;
	private static final String TAG="Terremotos";
	
	private GoogleMap mapa;

	private HashMap<Marker, Long > relacionMarcasTerremotos;
	
	
	private OnMarkerClickListener marcaClick = new OnMarkerClickListener() {
		@Override
		public boolean onMarkerClick(Marker marca) {
			// TODO Auto-generated method stub
			
			Log.d(TAG, "Click en la marca, id -->"+marca.getId());
			
			//obtener la  id del terremoto 
			
			Long elTerremoto = relacionMarcasTerremotos.get(marca);
			
			//para poder acceder al this de una clase en una instancia interna  NombreClase.this
	    	Intent intent= new Intent(getActivity(), DetalleTerremoto.class);	    	
	    	//pasar los datos al intent
	    	intent.putExtra(TERREMOTO_SELECCIONADO, elTerremoto);
	    	
			startActivityForResult(intent, SHOW_DETAIL);
			
			return true;
		}
	};
	
	public void onActivityCreated (Bundle savedInstanceState){
		
		super.onActivityCreated(savedInstanceState);

		mapa = this.getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		
		mapa.setOnMarkerClickListener(marcaClick);

		
		//para poner el loader de los datos
		
		getLoaderManager().initLoader(LOADER_TERREMOTOS, null, this);
	}

	///////////////////loader/////////////////////////
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] result_columns = TerremotosContentProvider.ALL_COLUMS;
		String where = TerremotosContentProvider.MAGNITUDE_COLUMN + " >= ?";
		String whereArgs[] = { String.valueOf(obtenerMagnitud()) };
		String order = TerremotosContentProvider.TIME_COLUMN + " DESC";
		
		
		CursorLoader loader = new CursorLoader(getActivity(),
				TerremotosContentProvider.CONTENT_URI, result_columns, where, whereArgs, order);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		
		relacionMarcasTerremotos= new HashMap<Marker,Long >();
		//limpiar las marcas anteriores del mapa
		mapa.clear();
		LatLng PERTH;
		Marker marca;
		if (cursor.moveToFirst()) {
		    do {
		    	Long lat = cursor.getLong(cursor
						.getColumnIndex(TerremotosContentProvider.LAT_COLUMN));
				Long lon = cursor.getLong(cursor
						.getColumnIndex(TerremotosContentProvider.LON_COLUMN));
				Long idTerremoto = cursor.getLong(cursor
						.getColumnIndex(TerremotosContentProvider.ID_COLUMN));
				Long mag = cursor.getLong(cursor
						.getColumnIndex(TerremotosContentProvider.MAGNITUDE_COLUMN));

				 PERTH = new LatLng(lat, lon);
				 marca = mapa.addMarker(new MarkerOptions()
			        .position(PERTH)
			        .title(String.valueOf(mag))
			        .draggable(false));
		    	
				 relacionMarcasTerremotos.put(marca, idTerremoto);
				 mapa.animateCamera(CameraUpdateFactory.newLatLng(PERTH));
		    	
		    } while (cursor.moveToNext());
		}
		
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	private float obtenerMagnitud() {

		// mirar las preferencias
		Context context = getActivity();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		//Log.d(TAG, "buscando la magnitud");
		float mag = (float) Double.parseDouble(prefs.getString(
				PreferenciasActivity.KEY_PREF_MAGNITUD, "0"));
		//Log.d(TAG, "magnitud de los settings -->" + mag);
		return mag;

	}

}
