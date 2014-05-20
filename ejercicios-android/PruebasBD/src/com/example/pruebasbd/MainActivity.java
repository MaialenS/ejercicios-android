package com.example.pruebasbd;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    	DatabaseErrorHandler errorHandler = null;

    	Terremoto terremoto= new Terremoto(22, "un lugar", 2151, "detalles",
    			(float)20.1, (float)0.22, (float)541.0, "una url",
    			44684);


    	TerremotosBD bd=new TerremotosBD(this);
    	bd.open();
    	//bd.crearTerremoto(terremoto);
    	
    	Cursor c=bd.getAllTerremotos();
    	//obtener el indice de la id y el lugar
    	int index_id = c.getColumnIndexOrThrow(TerremotosDBOpenHelper.ID_COLUMN);
    	int index_lugar = c.getColumnIndexOrThrow(TerremotosDBOpenHelper.PLACE_COLUMN);
    	
    	
    	if(c!=null){
    		
    		Log.d("ACT", "tamaño del cursor-->"+c.getCount());
    		
    		int id ;
			String lugar;
    		
    		// looping through all rows and adding to list
    	    if (c.moveToFirst()) {
    	    	Log.d("ACT", "cursor to first");
    	        do {
    	        	Log.d("ATC", "id-->"+index_id+" lugar --> "+index_lugar);
    	        	id = c.getInt(index_id);
    				lugar= c.getString(index_lugar);
    				
    				Log.d("ATC", "id-->"+id+" lugar --> "+lugar);
    	        } while (c.moveToNext());
    	    }else{
    	    	Log.d("ACT", "no se movio al primero");
    	    }

    	}else{
    		Log.d("ACT", "cursor null");
    	}
    	
    	
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

  

}
