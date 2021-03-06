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

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

public class ObtenerTerremotosAsync extends AsyncTask<URL, Integer, Long> {
	private static final String TAG = "Terremotos";

	private Context contex;
	private TerremotosBD bd;
	private IDatosNuevo activity;
	URL url;

	public interface IDatosNuevo {
		public void buscarBD();
	}

	public ObtenerTerremotosAsync(Context contex, IDatosNuevo activity) {
		Log.d(TAG, "ObtenerTerremotosAsync constructor");
		// comprobar que quien lo llama implementa la interfaz
				try {
					this.activity = (IDatosNuevo) activity;	

				} catch (ClassCastException e) {
					throw new ClassCastException(activity.toString()
							+ " no implementa la interfaz IAddItem");
				}
		
		this.contex = contex;
		bd = TerremotosBD.getDB(contex);
		this.activity = activity;
	}

	@Override
	protected Long doInBackground(URL... arg0) {
		// TODO Auto-generated method stub

		Log.d(TAG, "doInBackground");

		url =arg0[0];

		descargarDatos();
		
		return null;

	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		Log.d(TAG, "onPostExecute");
	}

	@Override
	protected void onPostExecute(Long result) {
		Log.d(TAG, "onProgressUpdate");
		
		this.activity.buscarBD();

	}

	@Override
	protected void onPreExecute() {
		Log.d(TAG, "onPreExecute");
	}

	// ////////funciones utiles///////////////
	private void descargarDatos() {
		try {
			URLConnection connection = this.url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				procesarJson(in);

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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

			// Log.d(TAG, "cantidad terremotos->"+arrayTerremotos.length());

			for (int i = 0; i < arrayTerremotos.length(); i++) {
				JSONObject eq = arrayTerremotos.getJSONObject(i);
				JSONObject propiedades = eq.getJSONObject("properties");
				JSONArray coordenadas = eq.getJSONObject("geometry")
						.getJSONArray("coordinates");

				Terremoto t = new Terremoto(eq.getString("id"),
						propiedades.getString("place"),
						propiedades.getLong("time"),
						propiedades.getString("detail"),
						(float) propiedades.getDouble("mag"),
						(float) coordenadas.getDouble(1),
						(float) coordenadas.getDouble(0),
						propiedades.getString("url"));

				// guardarlo donde sea
				// Log.d(TAG, t.toString());
				bd.insertarTerremoto(t);

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
