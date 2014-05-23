package com.maialen.terremotos;

import com.maialen.adaptadores.ListaTerremotosAdapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.maialen.datos.ObtenerTerremotos;
import com.maialen.datos.ObtenerTerremotosAsync;
import com.maialen.datos.TerremotosBD;
import com.maialen.datos.TerremotosDBOpenHelper;
import com.maialen.preferencias.PreferenciasActivity;

import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentListaTerremotos2 extends ListFragment implements ObtenerTerremotosAsync.IDatosNuevo{
	private static final String TAG="Terremotos";
//	private SimpleCursorAdapter mAdapter;
	
	private Cursor c;
	
	TerremotosBD bd;
	static final String STATE_LISTADO = "estadoListado";

	private ListaTerremotosAdapter adapter;

	 @Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	      
	        bd = TerremotosBD.getDB(getActivity());
	    	
	        c=bd.getTerremotoMagnitud(obtenerMagnitud());   
	        Log.d(TAG, String.valueOf(c.getCount()));
	        
	        this.adapter= new ListaTerremotosAdapter(getActivity(), c);
	        
	        
	        
	        setListAdapter(this.adapter);
	        descargarNuevosTerremotos();
	 }

		
		public void descargarNuevosTerremotos(){
			/*
			ObtenerTerremotos obtenerTerremotos=new ObtenerTerremotos(bd);
	    	obtenerTerremotos.buscar();
	    	
	    	c=bd.getTerremotoMagnitud(obtenerMagnitud());   
	    	this.adapter.changeCursor(c);
	    	this.adapter.notifyDataSetChanged();
	    	*/
			
			ObtenerTerremotosAsync asiync= new ObtenerTerremotosAsync(getActivity(), this);
	    	
			
			String path = getString(R.string.url_terremotos);
			try {
				URL url = new URL(path);
				
				asiync.execute(url);
				
				
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
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


		@Override
		public void actulizarVista() {
			// se han obtenido nuevos datos de internet
			
			c=bd.getTerremotoMagnitud(obtenerMagnitud());   
	    	this.adapter.changeCursor(c);
	    	this.adapter.notifyDataSetChanged();
			
		}

}
