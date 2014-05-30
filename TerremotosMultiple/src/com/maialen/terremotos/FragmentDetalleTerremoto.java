package com.maialen.terremotos;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.android.gms.maps.model.LatLng;
import com.maialen.datos.TerremotosContentProvider;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;




public class FragmentDetalleTerremoto extends Fragment implements LoaderCallbacks<Cursor>{

	private static final String TAG = "Terremotos";
	private static final int LOADER_DETALLE = 2;
	private static final String LA_URL ="la_url";
	
	private long id_terremoto;
	
	private String stringURL;
	private MapaFragmentoDetalle mapa;
	
	private TextView lugar;
	private TextView magnitud;
	private TextView fecha;
	private TextView latitud;
	private TextView longitud;
	
	private Button btnMostrarUrl;

	
	View.OnClickListener clickMostarUrl = new View.OnClickListener() {
		public void onClick(View v) {
			//poner datos en la respuesta cuando es ok
			
	    	Intent intent= new Intent(getActivity(), MostrarURL.class);	    	
	    	//pasar los datos al intent
	    	intent.putExtra(LA_URL, stringURL);
	    	
			startActivity(intent);
	        	
	    }
	};
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
			
		View v = inflater.inflate(R.layout.fragment_detalle_terremoto, container, false);
			
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//obtener el identificador el terremoto
		Intent intent = getActivity().getIntent();
	    //comprobar si esta por si se ha girado o fallado algo o por si viene de otro lugar
		id_terremoto=0;
	    if (intent!=null){
		  
		    id_terremoto=intent.getLongExtra(FragmentListaTerremotos.TERREMOTO_SELECCIONADO, 0);
			
		    Log.d(TAG, "id terremoto-> "+id_terremoto);
	    }
		
	    lugar= (TextView) v.findViewById(R.id.textDetalleLugar);
	    fecha= (TextView) v.findViewById(R.id.textDetalleFecha);
	    magnitud= (TextView) v.findViewById(R.id.textDetalleMagnitud);
	    latitud= (TextView) v.findViewById(R.id.textDetalleLatitud);
	    longitud= (TextView) v.findViewById(R.id.textDetalleLongitud);

	    btnMostrarUrl = (Button) v.findViewById(R.id.btnDetalleUrl);
	    btnMostrarUrl.setOnClickListener(clickMostarUrl);
	    

		return v;
	};
	

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//para poner el loader de los datos
		getLoaderManager().initLoader(LOADER_DETALLE, null, this);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();



	}

	// Called at the start of the visible lifetime. @Override
	public void onStart() {
		super.onStart();
		// Apply any required UI change now that the Fragment is visible.
		
	}

//////////////METODOS PARA EL LOADER//////////////////

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		
		Uri uri=ContentUris.withAppendedId(TerremotosContentProvider.CONTENT_URI,id_terremoto );
		
		Log.d(TAG, "uri: "+uri);
		
		CursorLoader loader = new CursorLoader(getActivity(),
				uri, TerremotosContentProvider.ALL_COLUMS, null, null, null);
		return loader;
	}



	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		Log.d(TAG, "vuelve uri: ");
		if(cursor.moveToFirst()){
			//coger los datos
			String magnitudS = cursor.getString(cursor
					.getColumnIndex(TerremotosContentProvider.MAGNITUDE_COLUMN));
			String lugarS = cursor.getString(cursor
					.getColumnIndex(TerremotosContentProvider.PLACE_COLUMN));
	
			long fechaLong = cursor.getLong(cursor
					.getColumnIndex(TerremotosContentProvider.TIME_COLUMN));
			String fechaFormateada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date(fechaLong));
			
			stringURL= cursor.getString(cursor
					.getColumnIndex(TerremotosContentProvider.URL_COLUMN));
			
			Long lat = cursor.getLong(cursor
					.getColumnIndex(TerremotosContentProvider.LAT_COLUMN));
			Long lon = cursor.getLong(cursor
					.getColumnIndex(TerremotosContentProvider.LON_COLUMN));
			
			//pintar los datos
			
			lugar.setText(lugarS);
			magnitud.setText(magnitudS);
			fecha.setText(fechaFormateada);
			latitud.setText(String.valueOf(lat));
			longitud.setText(String.valueOf(lon));
			
			LatLng posicion=new LatLng(lat, lon);
			//poner la localizacion en el mapa
			((MapaFragmentoDetalle)(getFragmentManager().findFragmentById(R.id.mapaDetalle))).ponerMarca(posicion);
			
			
	
			Log.d(TAG, "url->"+stringURL);
		}
		
	}



	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

	////////////REDES SOCIALES/////////////
	
	public void compartirUrl(){
		
		Log.d(TAG, "CLICK en compartir");
		
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, stringURL);
		startActivity(Intent.createChooser(share, "prueba de share"));
		
	}
	
	
	

}
