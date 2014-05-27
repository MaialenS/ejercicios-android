package com.maialen.terremotos;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.maialen.datos.TerremotosContentProvider;

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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;




public class FragmentDetalleTerremoto extends Fragment implements LoaderCallbacks<Cursor>{

	private static final String TAG = "Terremotos";
	private static final int LOADER_DETALLE = 1;
	
	private long id_terremoto;
	
	private String stringURL;
	
	
	private TextView lugar;
	private TextView magnitud;
	private TextView fecha;
	private TextView detalle;
	
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
			
		View v = inflater.inflate(R.layout.fragment_detalle_terremoto, container, false);
			
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//obtener el identificador el terremoto
		Intent intent = getActivity().getIntent();
	    //comprobar si esta por si se ha girado o fallado algo o por si viene de otro lugar
	    if (intent!=null){
		  
		    id_terremoto=intent.getLongExtra(FragmentListaTerremotos.TERREMOTO_SELECCIONADO, 0);
			
		    Log.d(TAG, "id terremoto-> "+id_terremoto);
	    }
		
	    lugar= (TextView) v.findViewById(R.id.textDetalleLugar);
	    fecha= (TextView) v.findViewById(R.id.textDetalleFecha);
	    detalle= (TextView) v.findViewById(R.id.textDetalleDetalle);
	    magnitud= (TextView) v.findViewById(R.id.textDetalleMagnitud);


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
		
		String[] result_columns = TerremotosContentProvider.ALL_COLUMS;
		String where = TerremotosContentProvider.ID_COLUMN + " = ?";
		String whereArgs[] = { String.valueOf(id_terremoto) };
		String order = null;
		
		Uri uri=ContentUris.withAppendedId(TerremotosContentProvider.CONTENT_URI,id_terremoto );
		
		CursorLoader loader = new CursorLoader(getActivity(),
				uri, result_columns, where, whereArgs, order);
		return loader;
	}



	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		cursor.moveToFirst();
		//coger los datos
		String magnitudS = cursor.getString(cursor
				.getColumnIndex(TerremotosContentProvider.MAGNITUDE_COLUMN));
		String lugarS = cursor.getString(cursor
				.getColumnIndex(TerremotosContentProvider.PLACE_COLUMN));

		long fechaLong = cursor.getLong(cursor
				.getColumnIndex(TerremotosContentProvider.TIME_COLUMN));
		String fechaFormateada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(fechaLong));
				
		String detalleS =cursor.getString(cursor
				.getColumnIndex(TerremotosContentProvider.DETAIL_COLUMN));
		
		stringURL= cursor.getString(cursor
				.getColumnIndex(TerremotosContentProvider.URL_COLUMN));
		
		//pintar los datos
		
		lugar.setText(lugarS);
		magnitud.setText(magnitudS);
		fecha.setText(fechaFormateada);
		detalle.setText(detalleS);
		
	}



	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
