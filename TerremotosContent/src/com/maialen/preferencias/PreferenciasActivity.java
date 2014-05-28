package com.maialen.preferencias;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.maialen.datos.AlarmaBuscarTerremotos;

public class PreferenciasActivity extends Activity implements OnSharedPreferenceChangeListener{
	private static final String TAG="Terremotos";
	public static final String KEY_PREF_ACTUALIZAR = "AUTOREFRESH";
	public static final String KEY_PREF_INTERVALOS = "INTERVALOS";
	public static final String KEY_PREF_MAGNITUD = "MAGNITUD";
	public static final int PONER_AUTOREFRESH= 22;
	
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
			 
			 ponerQuitarAlarmaRefresh();
			 
			 
			 
		 }else if(key.equals(KEY_PREF_INTERVALOS)){

			 String intervalo = prefs.getString(PreferenciasActivity.KEY_PREF_INTERVALOS, "default");
			 
			 Log.d(TAG, "Se ha cambiado el intervalo-->"+intervalo);
			 
		 }else if(key.equals(KEY_PREF_MAGNITUD)){

			 String magnitud = prefs.getString(PreferenciasActivity.KEY_PREF_MAGNITUD, "default");
			 
			 Log.d(TAG, "Se ha cambiado la magnitud-->"+magnitud);
			 
			 
		 }else{
			 Log.d(TAG, "Algo se ha cambiado");
		 }
		
	}
	
	private void ponerQuitarAlarmaRefresh(){
		
		
		
		boolean autorefresh = prefs.getBoolean(PreferenciasActivity.KEY_PREF_ACTUALIZAR, false);
		String intervaloS = prefs.getString(PreferenciasActivity.KEY_PREF_INTERVALOS, "default");
		long intervalo= Long.valueOf(intervaloS)*60*1000;//tiempo en minutos*segundos*milisegundos
		
		intervalo=1000;//para las pruebas
		
		
		//obtener el manejador de alarmas
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		//poner la alamarma para que no levante al movil.
	    int alarmType = AlarmManager.RTC;
	  
	  //Create a Pending Intent that will broadcast and action
	   
	    Intent intent = new Intent(AlarmaBuscarTerremotos.ACTION);
	    
	    PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		
		
		if(autorefresh){//poner la alarma
			Log.d(TAG,"poniedno alarma");
		    //ponerla de forma que se ejecute ahora y en el intervalo
		    
		    alarmManager.setInexactRepeating(alarmType,
                    0,
                    intervalo,
                    alarmIntent);
		    
			
		}else{//quitar la alarma
			Log.d(TAG,"quitando alarma");
			alarmManager.cancel(alarmIntent);
		}
		
		
	}
	
	
	
}
