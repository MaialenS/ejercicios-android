package com.maialen.datos;

import com.maialen.servicios.ServicioBusquedaTerremotos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmaBuscarTerremotos extends BroadcastReceiver{
	
	public static final String ACTION = "com.maialen.ACTION_EARTHQUAKE_ALARM";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		
		String action = intent.getAction();
		
		
		if (action.equals(ACTION)){

			Intent intentServicio = new Intent(context, ServicioBusquedaTerremotos.class);
			context.startService(intentServicio);
		}
		
	}

}
