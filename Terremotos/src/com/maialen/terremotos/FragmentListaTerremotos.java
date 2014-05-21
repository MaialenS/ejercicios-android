package com.maialen.terremotos;


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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FragmentListaTerremotos extends Fragment{

	private SimpleCursorAdapter mAdapter;
	private ListView listaTerremotos;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_lista_terremotos, container, false);

		//ponerlo en modo singleton
		//PARA PASARLE EL PADRE
		TerremotosBD bd=new TerremotosBD(getActivity());
    	bd.open();
			
		listaTerremotos= (ListView) v.findViewById(R.id.listaTerremotos);

		
        Cursor c=bd.getAllTerremotos();   
        
        //adaptador para el cursor y un listView
        // For the cursor adapter, specify which columns go into which views
           String[] fromColumns = {TerremotosDBOpenHelper.PLACE_COLUMN};
           int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1
           // Create an empty adapter we will use to display the loaded data.
           // We pass null for the cursor, then update it in onLoadFinished()
           mAdapter = new SimpleCursorAdapter(getActivity(), 
                   android.R.layout.simple_list_item_1, c,
                   fromColumns, toViews, 0);
           
           listaTerremotos.setAdapter(mAdapter);


		return v;
	}

	
	public void onActivityCreated (Bundle savedInstanceState){
		
		//buscar nuevos terremotos
		
	}
	
	
	
	
}
