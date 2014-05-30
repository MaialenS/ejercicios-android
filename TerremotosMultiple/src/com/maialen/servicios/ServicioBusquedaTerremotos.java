package com.maialen.servicios;

import java.net.MalformedURLException;
import java.net.URL;

import com.maialen.datos.DescargarTerremotos;
import com.maialen.terremotos.R;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;



public class ServicioBusquedaTerremotos extends Service{
	private static final String TAG="Terremotos";
	
	////////////funciones para el servicio//////////
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	};
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "servicio descargando nuevos terremotos");
		descargarNuevosTerremotos();
	
	    //return Service.START_NOT_STICKY;
	    return super.onStartCommand(intent, flags, startId);
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}


	////////////funciones para obtener los datos///////////////

	private void descargarNuevosTerremotos(){
		Thread t= new Thread(new Runnable(){
			public void run() {
				
				descargarDatos();
				
			}
		});
		t.start();
		
		
		//para que se termine la ejecucion de la clase
		stopSelf();
	}
	
	private void descargarDatos(){
		try {
			String path = getString(R.string.url_terremotos);
			URL url = new URL(path);
			DescargarTerremotos descTerremotos= new DescargarTerremotos(url, this);
			descTerremotos.descargarDatos();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		
		
	}
	
}
