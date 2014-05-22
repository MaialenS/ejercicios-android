package com.maialen.terremotos;


import java.text.SimpleDateFormat;
import java.util.Date;


import com.maialen.datos.ObtenerTerremotos;
import com.maialen.datos.TerremotosBD;
import com.maialen.datos.TerremotosDBOpenHelper;
import com.maialen.preferencias.PreferenciasActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FragmentListaTerremotos extends Fragment{
	private static final String TAG="Terremotos";
//	private SimpleCursorAdapter mAdapter;
	private ListView listaTerremotos;
	private Cursor c;
	
	TerremotosBD bd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_lista_terremotos, container, false);
		
		listaTerremotos= (ListView) v.findViewById(R.id.listaTerremotos);
		//ponerlo en modo singleton
		//PARA PASARLE EL PADRE
		
		bd = TerremotosBD.getDB(getActivity());
    	
        c=bd.getTerremotoMagnitud(obtenerMagnitud());   
        Log.d(TAG, String.valueOf(c.getCount()));
        
        
//        String[] fromColumns = {TerremotosDBOpenHelper.MAGNITUDE_COLUMN, TerremotosDBOpenHelper.PLACE_COLUMN, TerremotosDBOpenHelper.TIME_COLUMN};
//        int[] toViews = {R.id.textTupaMagnitud, R.id.textTuplaLugar, R.id.textTupaFecha}; // The TextView in simple_list_item_1
//        // Create an empty adapter we will use to display the loaded data.
//        // We pass null for the cursor, then update it in onLoadFinished()
//        mAdapter = new SimpleCursorAdapter(getActivity(), 
//                R.layout.tupla_terremoto, c,
//                fromColumns, toViews, 0);
//        
        
        
        TerremotoAdapter mAdapter=new TerremotoAdapter(getActivity(), c);
        
        
           listaTerremotos.setAdapter(mAdapter);


		return v;
	}

	
	public void onActivityCreated (Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		//buscar nuevos terremotos
		
		descargarNuevosTerremotos();
		
	}
	
	public void descargarNuevosTerremotos(){
		
		ObtenerTerremotos obtenerTerremotos=new ObtenerTerremotos(bd);
    	obtenerTerremotos.buscar();
    	
    	c=bd.getTerremotoMagnitud(obtenerMagnitud());   
    	//mAdapter.changeCursor(c);
    	//mAdapter.notifyDataSetChanged();
	}
		
	
	private float obtenerMagnitud(){
		
		//mirar las preferencias
	    Context context = getActivity();
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);	
	  	
	    Log.d(TAG, "buscando la magnitud");
	    float mag = (float)Double.parseDouble(prefs.getString(PreferenciasActivity.KEY_PREF_MAGNITUD, "0"));
	    Log.d(TAG, "magnitud de los settings -->"+mag);
		return mag;
	
	}
	
	private class TerremotoAdapter extends CursorAdapter {
        private LayoutInflater mInflater;
        public Cursor c;
        
        public TerremotoAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);

            /*
             * Gets an inflater that can instantiate
             * the ListView layout from the file.
             */
            mInflater = LayoutInflater.from(context);
            c=cursor;
           
        }
        /**
         * Defines a class that hold resource IDs of each item layout
         * row to prevent having to look them up each time data is
         * bound to a row.
         */
        private class ViewHolder {
            TextView magnitud;
            TextView lugar;
            TextView fecha;
        }
        
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            /* Inflates the item layout. Stores resource IDs in a
             * in a ViewHolder class to prevent having to look
             * them up each time bindView() is called.
             */
            final View itemView =mInflater.inflate(R.layout.tupla_terremoto,
                            						viewGroup,
                            						false);
            final ViewHolder holder = new ViewHolder();
            holder.magnitud =(TextView) itemView.findViewById(R.id.textTupaMagnitud);
            holder.lugar =(TextView) itemView.findViewById(R.id.textTuplaLugar);
            holder.fecha =(TextView) itemView.findViewById(R.id.textTupaFecha);
            
            itemView.setTag(holder);
            return itemView;
        }
        
        @Override
        public void bindView( View view, Context context, Cursor cursor) {
            final ViewHolder holder = (ViewHolder) view.getTag();

            // poner los valores en la tupla
            
            String magnitud = cursor.getString(cursor.getColumnIndex(TerremotosDBOpenHelper.MAGNITUDE_COLUMN));
            String lugar= cursor.getString(cursor.getColumnIndex(TerremotosDBOpenHelper.PLACE_COLUMN));
 
            String fechaInt=cursor.getString(cursor.getColumnIndex(TerremotosDBOpenHelper.TIME_COLUMN));        
            String fechaFormateada= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Integer.parseInt(fechaInt) * 1000L));
            
            holder.magnitud.setText(magnitud);
            holder.lugar.setText(lugar);
            holder.fecha.setText(fechaFormateada);
    
        }
    }

	
}
