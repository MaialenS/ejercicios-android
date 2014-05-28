package com.maialen.datos;

import com.maialen.servicios.ServicioBusquedaTerremotos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmaBuscarTerremotos extends BroadcastReceiver{
	
	public static final String ACTION = "com.maialen.ACTION_EARTHQUAKE_ALARM";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		
		String action = intent.getAction();
		
		Log.d("Terremotos", "AAAAAAAAAHHHHHHHHHHH");
		
			Intent intent1 = new Intent(context, ServicioBusquedaTerremotos.class);
			context.startService(intent1);
		
		
	}

}
