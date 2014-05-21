package com.maialen.terremotos;

import com.maialen.datos.ObtenerTerremotos;
import com.maialen.datos.TerremotosBD;
import com.maialen.preferencias.PreferenciasActivity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {
	
	private static final String TAG="Terremotos";
	private static final int SHOW_PREFERENCES = 1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TerremotosBD bd=new TerremotosBD(this);
    	bd.open();
		
		//pruebas con bd
/*
    	Terremoto terremoto= new Terremoto("42", "un lugar42", 2151, "detalles42",
    			(float)20.1, (float)0.22, (float)541.0, "una url42");
    	
    	Terremoto terremoto2= new Terremoto("22", "un lugar22", 2151, "detalles22",
    			(float)20.1, (float)0.22, (float)541.0, "una url42");


    	bd.crearTerremoto(terremoto);
    	bd.crearTerremoto(terremoto2);
   	
    	Cursor c=bd.getAllTerremotos();    	
    	
    	if(c!=null){
    		//obtener el indice de la id y el lugar
        	int index_id = c.getColumnIndexOrThrow(TerremotosDBOpenHelper.ID_COLUMN);
        	int index_terremoto = c.getColumnIndexOrThrow(TerremotosDBOpenHelper.ID_TERREMOTO);
        	int index_lugar = c.getColumnIndexOrThrow(TerremotosDBOpenHelper.PLACE_COLUMN);
    		    		
    		Log.d(TAG, "tama–o del cursor-->"+c.getCount());
    		
    		int id ;
			String lugar, terr;
    		
    		// looping through all rows and adding to list
    	    if (c.moveToFirst()) {
    	    	Log.d(TAG, "cursor to first");
    	        do {
    	        	
    	        	id = c.getInt(index_id);
    	        	terr=c.getString(index_terremoto);
    				lugar= c.getString(index_lugar);
    				
    				Log.d(TAG, "id-->"+id+" terremoto-->"+terr+" lugar --> "+lugar);
    	        } while (c.moveToNext());
    	    }else{
    	    	Log.d(TAG, "no se movio al primero");
    	    }

    	}else{
    		Log.d(TAG, "cursor null");
    	}
		
		*/ 
		//pruebas con json
    	
    	
    	ObtenerTerremotos obtenerTerremotos=new ObtenerTerremotos(bd);
    	obtenerTerremotos.buscar();
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new FragmentListaTerremotos()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			startActivityForResult(i, SHOW_PREFERENCES);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
