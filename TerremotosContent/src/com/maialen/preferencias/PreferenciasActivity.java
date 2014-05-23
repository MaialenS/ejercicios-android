package com.maialen.preferencias;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.util.Log;
import android.preference.PreferenceManager;

public class PreferenciasActivity extends Activity implements OnSharedPreferenceChangeListener{
	private static final String TAG="Terremotos";
	public static final String KEY_PREF_ACTUALIZAR = "AUTOREFRESH";
	public static final String KEY_PREF_INTERVALOS = "INTERVALOS";
	public static final String KEY_PREF_MAGNITUD = "MAGNITUD";
	
	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//poner un fragmento como vista main
		getFragmentManager().beginTransaction()
							.replace(android.R.id.content, new PreferenciasFragment())
							.commit();
		
		
		//mirar las preferencias
	    Context context = getApplicationContext();
	    prefs = PreferenceManager.getDefaultSharedPreferences(context);	
		
		
		prefs.registerOnSharedPreferenceChangeListener(this);
	}  
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
		
		 if(key.equals(KEY_PREF_ACTUALIZAR)){

			 boolean auto = prefs.getBoolean(PreferenciasActivity.KEY_PREF_ACTUALIZAR, false);
			 Log.d(TAG, "Se ha cambiado el autorefresh-->"+String.valueOf(auto));
			 
		 }else if(key.equals(KEY_PREF_INTERVALOS)){

			 String intervalo = prefs.getString(PreferenciasActivity.KEY_PREF_INTERVALOS, "default");
			 
			 Log.d(TAG, "Se ha cambiado el intervalo-->"+intervalo);
			 
		 }else if(key.equals(KEY_PREF_MAGNITUD)){

			 String magnitud = prefs.getString(PreferenciasActivity.KEY_PREF_MAGNITUD, "default");
			 
			 Log.d(TAG, "Se ha cambiado la magnitud-->"+magnitud);
			 
			 //descargarNuevosTerremotos();
			 
		 }else{
			 Log.d(TAG, "Algo se ha cambiado");
		 }
		
	}
}
