package com.maialen.terremotos;

import com.maialen.adaptadores.ListaTerremotosAdapter;

import java.net.MalformedURLException;
import java.net.URL;

import com.maialen.preferencias.PreferenciasActivity;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class FragmentListaTerremotos2 extends ListFragment {
	static final String STATE_ACTIVIDAD = "estadoActividad";
	private static final String TAG="Terremotos";
//	private SimpleCursorAdapter mAdapter;
	
	static final String STATE_LISTADO = "estadoListado";

	private ListaTerremotosAdapter adapter;

	
	
	

	 @Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);

	        this.adapter= new ListaTerremotosAdapter(getActivity(), null);

	        setListAdapter(this.adapter);

	        
	 }
	 
	 
	 @Override
		public void onResume() {
		    super.onResume();
		    
		   
		    /*
		    buscarTerremotosBD= new BuscarBDTerremotosAsync(getActivity(), this);
	        buscarTerremotosInternet= new ObtenerTerremotosAsync(getActivity(), this);
	        
	        buscarTerremotosBD.execute();
	        
	        descargarNuevosTerremotos();
		    */
		    
		    
	 }

	
	 
		
		public void descargarNuevosTerremotos(){

				
			String path = getString(R.string.url_terremotos);
			try {
				URL url = new URL(path);			
				//buscarTerremotosInternet.execute(url);

			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			}
			
	    	
	    	
		}
			
		
		private float obtenerMagnitud(){
			
			//mirar las preferencias
		    Context context = getActivity();
		    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);	
		  	
		    Log.d(TAG, "buscando la magnitud");
		    float mag = (float)Double.parseDouble(prefs.getString(PreferenciasActivity.KEY_PREF_MAGNITUD, "0"));
		    Log.d(TAG, "magnitud de los settings -->"+mag);
			return mag;
		
		}

		
		
		
		// Called at the start of the visible lifetime. @Override
		public void onStart(){
		  super.onStart();
		  // Apply any required UI change now that the Fragment is visible.
		  
		  
		}


		
		public void buscarBD(){
			
	    	//buscar en la BD
			//buscarTerremotosBD.execute();
			
			//BuscarBDTerremotosAsync buscar2= new BuscarBDTerremotosAsync(getActivity(), this);
		}
		
		
		
		public void actulizarVista(Cursor c) {
			// se han obtenido nuevos datos de internet

	    	this.adapter.changeCursor(c);
	    	this.adapter.notifyDataSetChanged();
			
		}

		
		

		
}
