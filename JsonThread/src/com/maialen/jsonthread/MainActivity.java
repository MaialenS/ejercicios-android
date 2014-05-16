package com.maialen.jsonthread;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.maiaalen.jsonthread.R;

import android.app.Activity;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

	private static final String TAG="ACT";
	
	private Button btnDescargar, btnDescargarManager;
	private TextView textoSalida;
	private String myFeed;

	private ArrayList<String> listaFotos;
	
	private long referenciaDescarga;

	BroadcastReceiver receiver;
	
	
	//cuando se haga click en boton Descargar
	View.OnClickListener clickDescargar = new View.OnClickListener() {
		public void onClick(View v) {
		
			//hacerlo en otro thread
			
			Thread t= new Thread(new Runnable(){
				public void run() {
					
					descargarDatos();
					
				}
			});
			t.start();
		}
	};
	
	View.OnClickListener clickDescargarManager= new View.OnClickListener() {

		public void onClick(View v) {
			
			if(receiver==null){
				ponerEscuchador();
			}
			
			String serviceString = Context.DOWNLOAD_SERVICE;
			//manager de descargas
			DownloadManager downloadManager;
			downloadManager = (DownloadManager)getSystemService(serviceString); 
			
			Uri uri = Uri.parse(getString(R.string.dir_con_manager));
			DownloadManager.Request request = new Request(uri);
			//para decirle que solo descargue por wifi
			//request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
			
			
			//decirle donde guardar el fichero
			//Environment.DIRECTORY_DOWNLOADS  directorio donde se guardan las cosas de esta aplicacion
			request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS, "algo.zip");

			//para cambiar como se ven en las notificaciones
			request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			
			
			
			
			referenciaDescarga = downloadManager.enqueue(request);
			
			//hay que ponerlo antes y solamente una vez
			//ponerEscuchador();
		}
	};
	
	
	private void ponerEscuchador(){
		//Para recibir la notificacion de descarga cuando se hace con manager
		//se crea un broadcast
		Log.d(TAG, "pongo el escuchador");
		IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				String serviceString = Context.DOWNLOAD_SERVICE;
				DownloadManager downloadManager;
				downloadManager = (DownloadManager)getSystemService(serviceString);
				int reason=0;
				String name="";
				long tam=0;
				if (referenciaDescarga == reference) {
					
					Query estado = new Query(); 
					estado.setFilterById(referenciaDescarga);
					
					Cursor descargas = downloadManager.query(estado); 
					int reasonIdx = descargas.getColumnIndex(DownloadManager.COLUMN_REASON);
					int nameIdx = descargas.getColumnIndex(DownloadManager.COLUMN_TITLE);
					int tamanoIdx = descargas.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
					
					while (descargas.moveToNext()) {
					    reason = descargas.getInt(reasonIdx);
					    name = descargas.getString(nameIdx);
					    tam = descargas.getLong(tamanoIdx);
					    Log.d(TAG, "Estado del fichero-->"+ reason);
					    
					}
					
					textoSalida.setText(name+ "\nTamano: "+tam+"\nEstado: "+reason);
					
				}
			}
		};
		
		registerReceiver(receiver, filter);
		
	}
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.btnDescargar= (Button) findViewById(R.id.btnDescargar);
		this.btnDescargar.setOnClickListener(clickDescargar);

		this.btnDescargarManager= (Button) findViewById(R.id.btnDescargarManager);
		this.btnDescargarManager.setOnClickListener(clickDescargarManager);
		
		this.listaFotos=new ArrayList<String>();

		this.textoSalida= (TextView) findViewById(R.id.textArchivo);
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	////////////////////////////////////////////
	
	private void descargarDatos(){
		try {
			myFeed = getString(R.string.my_feed);
			URL url = new URL(myFeed);
			
			// Create a new HTTP URL connection
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			int responseCode = httpConnection.getResponseCode();
			//comprobar si el estado de la conexion es correcto y si eso pedir los datos.
			if (responseCode == HttpURLConnection.HTTP_OK) {
			  InputStream in = httpConnection.getInputStream();
			  processStream(in);
			}
			
		} catch (MalformedURLException e) {
			  Log.d(TAG, "Malformed URL Exception.", e);
		}
		catch (IOException e) {
		  Log.d(TAG, "IO Exception.", e);
		}
	}
	
	private void processStream(InputStream in){
		Log.d(TAG, "leido "+in.toString());
		
	
			Log.d(TAG, "parsearlo ");

		      BufferedReader rd;
			try {
				rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String jsonText = readAll(rd);
				//Log.d(TAG, "todo "+jsonText);
			    JSONObject json = new JSONObject(jsonText);
			   
			    JSONArray fotos = json.getJSONArray("photos");
			    
			    Log.d(TAG, "fotos "+fotos.length());
			    
			    for(int i=0; i<fotos.length();i++){
			    	
			    	JSONObject foto= fotos.getJSONObject(i);
			    	
			    	this.listaFotos.add(foto.getString("image_url"));
			    }
			    
			    Log.d(TAG, "Url fotos "+this.listaFotos);
			   
			   
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
