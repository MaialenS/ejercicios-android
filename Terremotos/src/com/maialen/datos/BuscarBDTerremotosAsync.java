package com.maialen.datos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.maialen.preferencias.PreferenciasActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

public class BuscarBDTerremotosAsync extends AsyncTask<URL, Integer, Long>{
	private static final String TAG = "Terremotos";
	
	private Context contex;
	private TerremotosBD bd;
	private IBuscarBD activity;
	
	public interface IBuscarBD {
		public void actulizarVista(Cursor c);
	}
	

	public BuscarBDTerremotosAsync(Context contex, IBuscarBD activity){
		Log.d(TAG, "ObtenerTerremotosAsync constructor");
		
		//comprobar que quien lo llama implementa la interfaz
				try {
					this.activity = (IBuscarBD)activity;
				} catch(ClassCastException e) {
					throw new ClassCastException(activity.toString() + " no implementa la interfaz IBuscarBD");
				}
		
		
		this.contex=contex;
		bd = TerremotosBD.getDB(contex);
		this.activity=activity;
	}

	@Override
	protected Long doInBackground(URL... arg0) {
		// TODO Auto-generated method stub
		
		Log.d(TAG, "doInBackground");
		
		
		
		
		Cursor c= bd.getTerremotoMagnitud(obtenerMagnitud());
		
		this.activity.actulizarVista(c);
		
		
		return null;
		
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		Log.d(TAG, "onPostExecute");
    }

	@Override
    protected void onPostExecute(Long result) {
		Log.d(TAG, "onProgressUpdate");
		
    }
	
	@Override
	protected void onPreExecute (){
		Log.d(TAG, "onPreExecute");
	}
	
	private float obtenerMagnitud(){
		
		//mirar las preferencias
	    
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.contex);	
	  	
	    Log.d(TAG, "buscando la magnitud");
	    float mag = (float)Double.parseDouble(prefs.getString(PreferenciasActivity.KEY_PREF_MAGNITUD, "0"));
	    Log.d(TAG, "magnitud de los settings -->"+mag);
		return mag;
	
	}

}
