package com.example.pruebasbd;

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

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class ObtenerTerremotos extends Activity {

	public void buscar() {

		try {
			String myFeed = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
			URL url = new URL(myFeed);

			// Create a new HTTP URL connection
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			// comprobar si el estado de la conexion es correcto y si eso pedir
			// los datos.
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				procesarJson(in);
			}

		} catch (MalformedURLException e) {
			Log.d("ACT", "Malformed URL Exception.", e);
		} catch (IOException e) {
			Log.d("ACT", "IO Exception.", e);
		}
	}

	private void procesarJson(InputStream in) {

		BufferedReader rd;
		try {
			rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String jsonText = readAll(rd);
			// Log.d(TAG, "todo "+jsonText);
			JSONObject json = new JSONObject(jsonText);

			JSONArray arrayTerremotos = json.getJSONArray("features");
			
			for(int i=0; i< arrayTerremotos.length(); i++) {
				JSONObject eq = arrayTerremotos.getJSONObject(i);
				JSONObject propiedades = eq.getJSONObject("properties");
				JSONArray coordenadas = eq.getJSONObject("geometry").getJSONArray("coordinates");
				
				Terremoto t =new Terremoto(	
						eq.getInt("id"), 
						propiedades.getString("place"), 
						propiedades.getInt("time"), 
						propiedades.getString("detail"),
						(float)propiedades.getDouble("mag"), 
						(float)coordenadas.getDouble(1), 
						(float)coordenadas.getDouble(0), 
						propiedades.getString("url"),
						propiedades.getInt("updated")
						);
				
				//guardarlo donde sea
				
			}	


		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

}
