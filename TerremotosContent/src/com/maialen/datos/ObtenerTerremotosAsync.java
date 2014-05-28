package com.maialen.datos;


import java.net.URL;


import android.content.Context;

import android.os.AsyncTask;
import android.util.Log;

public class ObtenerTerremotosAsync extends AsyncTask<URL, Integer, Long> {
	private static final String TAG = "Terremotos";
	

	private Context contex;
	private URL url;



	public ObtenerTerremotosAsync(Context contex) {
		//Log.d(TAG, "ObtenerTerremotosAsync constructor");

		
		this.contex = contex;

	}

	@Override
	protected Long doInBackground(URL... arg0) {
		// TODO Auto-generated method stub

		//Log.d(TAG, "ObtenerTerremotosAsync doInBackground");

		url =arg0[0];

		DescargarTerremotos descTerremotos= new DescargarTerremotos(url, contex);
		descTerremotos.descargarDatos();
		
		
		return null;

	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		//Log.d(TAG, "ObtenerTerremotosAsync onPostExecute");
	}

	@Override
	protected void onPostExecute(Long result) {
		//Log.d(TAG, "ObtenerTerremotosAsync onProgressUpdate");

	}

	@Override
	protected void onPreExecute() {
		//Log.d(TAG, "ObtenerTerremotosAsync onPreExecute");
	}

	
}
