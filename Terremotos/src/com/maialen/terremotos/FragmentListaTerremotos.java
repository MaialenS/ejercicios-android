package com.maialen.terremotos;


import com.maialen.datos.ObtenerTerremotos;
import com.maialen.datos.TerremotosBD;
import com.maialen.datos.TerremotosDBOpenHelper;
import com.maialen.preferencias.PreferenciasActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FragmentListaTerremotos extends Fragment{
	private static final String TAG="Terremotos";
	private SimpleCursorAdapter mAdapter;
	private ListView listaTerremotos;
	private Cursor c;
	
	TerremotosBD bd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_lista_terremotos, container, false);
		
		listaTerremotos= (ListView) v.findViewById(R.id.listaTerremotos);
		//ponerlo en modo singleton
		//PARA PASARLE EL PADRE
		
		bd = TerremotosBD.getDB(getActivity());
    	
        c=bd.getTerremotoMagnitud(obtenerMagnitud());   
        
        /*
        //adaptador para el cursor y un listView
        // For the cursor adapter, specify which columns go into which views
           String[] fromColumns = {TerremotosDBOpenHelper.PLACE_COLUMN};
           int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1
           // Create an empty adapter we will use to display the loaded data.
           // We pass null for the cursor, then update it in onLoadFinished()
           mAdapter = new SimpleCursorAdapter(getActivity(), 
                   android.R.layout.simple_list_item_1, c,
                   fromColumns, toViews, 0);
           
           
       */
        
        String[] fromColumns = {TerremotosDBOpenHelper.MAGNITUDE_COLUMN, TerremotosDBOpenHelper.PLACE_COLUMN, TerremotosDBOpenHelper.TIME_COLUMN};
        int[] toViews = {R.id.textTupaMagnitud, R.id.textTuplaLugar, R.id.textTupaFecha}; // The TextView in simple_list_item_1
        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new SimpleCursorAdapter(getActivity(), 
                R.layout.tupla_terremoto, c,
                fromColumns, toViews, 0);
        
           listaTerremotos.setAdapter(mAdapter);


		return v;
	}

	
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		//buscar nuevos terremotos
		
		descargarNuevosTerremotos();
		
	}
	
	public void descargarNuevosTerremotos(){
		
		ObtenerTerremotos obtenerTerremotos=new ObtenerTerremotos(bd);
    	obtenerTerremotos.buscar();
    	
    	c=bd.getTerremotoMagnitud(obtenerMagnitud());   
    	mAdapter.changeCursor(c);
	}
	
	private void obtenerTerremotos(){
		
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
	
}
