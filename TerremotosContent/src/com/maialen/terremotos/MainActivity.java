package com.maialen.terremotos;


import com.maialen.datos.Terremoto;
import com.maialen.datos.TerremotosContentProvider;
import com.maialen.preferencias.PreferenciasActivity;
import com.maialen.servicios.ServicioBusquedaTerremotos;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	private static final String TAG="Terremotos";
	private static final String MIS_PREFERENCIAS = "preferencias_terremotos";
	private static final String PRIMERA_EJECUCION = "primera_ejecucion";
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		//cargar los fragmentos
		if (savedInstanceState == null) {
			
			getFragmentManager().beginTransaction()
					.add(R.id.container, new FragmentListaTerremotos(), "list").commit();
		}
		
		//comprobar si es la primera vez que se ejecuta la aplicacion
		
		SharedPreferences mySharedPreferences = getSharedPreferences(MIS_PREFERENCIAS,
                Activity.MODE_PRIVATE);
		boolean primeraVez = mySharedPreferences.getBoolean(PRIMERA_EJECUCION, true);
		if(primeraVez){//primera vez que se ejecuta la aplicacion
			Log.d(TAG, "primera ejecucion del app");
			//apuntar que ya se ha ejecutado por primera vez
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putBoolean(PRIMERA_EJECUCION, false);
			//guardar los cambios
			editor.apply();
			
			//pedir terremotos
			Intent intent = new Intent(this, ServicioBusquedaTerremotos.class);
			startService(intent);
			
		}else{
			Log.d(TAG, "no es primera ejecucion del app");
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
			startActivity(i);
			
			return true;
		} else if(id == R.id.action_refresh){
			//refrescar los terremotos
			//((FragmentListaTerremotos)(getFragmentManager().findFragmentById(R.id.container))).descargarNuevosTerremotos();
			// Explicitly start My Service
			Intent intent = new Intent(this, ServicioBusquedaTerremotos.class);
			startService(intent);
			
		}
		return super.onOptionsItemSelected(item);
	}

	

}
