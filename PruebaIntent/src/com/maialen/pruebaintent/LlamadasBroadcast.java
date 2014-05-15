package com.maialen.pruebaintent;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class LlamadasBroadcast extends BroadcastReceiver{
	private static final String SMS_EXTRA_NAME = "pdus";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub  android.intent.action.AIRPLANE_MODE
		Log.w("ACT", "QUE PASA ALGO");
		String action = intent.getAction();
		
		Log.w("ACT", "action-> "+action);
		if (action.equals("android.intent.action.PHONE_STATE")){
			tratarLlamada(intent.getExtras(), intent);
			
		}else if (action.equals("android.provider.Telephony.SMS_RECEIVED")){
			
			tratarMensaje(intent.getExtras());
		}else if (action.equals("android.intent.action.AIRPLANE_MODE")){
			tratarAirPlane(intent.getExtras());
			
		}else if(action.equals("android.net.conn.CONNECTIVITY_CHANGE")){
			tratarCambioConexion(context, intent.getExtras());
			
		}else if(action.equals("android.intent.action.SERVICE_STATE")){
			tratarCambioEstado(intent.getExtras());
			
		}
		
	}
	
	private void tratarLlamada(Bundle extras,  Intent intent){
		if (extras != null) {
		      String state = extras.getString(TelephonyManager.EXTRA_STATE);
			  String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		      
		      Log.w("ACT","estado --> "+ state);
		      if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
		        String phoneNumber = extras
		            .getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
		        Log.w("ACT", "Numero de telefono-> "+phoneNumber);
		      }
		    }
	}
	
	private void tratarMensaje(Bundle extras){
		String messages = "";
        
        if ( extras != null )
        {
            // Get received SMS array
            Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );

            for ( int i = 0; i < smsExtra.length; ++i )
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
                 
                String body = sms.getMessageBody().toString();
                String address = sms.getOriginatingAddress();
                 
                messages += "SMS from " + address + " :\n";                    
                messages += body + "\n";
            }
        }
        Log.w("ACT", "mensaje -> "+messages);
	}
	
	private void tratarCambioEstado(Bundle extras){
		Log.w("ACT", "cambio de estado");
		
		
	}
	
	private void tratarCambioConexion(Context context, Bundle extras){
		
		Log.w("ACT", "cambio de conexion");
		Log.w("ACT", "airplane "+isAirplaneModeOn(context));
		
		ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            Log.d("RECEIVER", "Network available");
        } else {
        	Log.d("RECEIVER", "Network NOT available");
        }
		
	}
	
	private void tratarAirPlane(Bundle extras){
		Log.w("ACT", "airplane "+Settings.Global.AIRPLANE_MODE_ON);
		Log.w("ACT", "airplane ");
		
		
		
		
	}

	//mirar si esta en modo avion o no.
	private boolean isAirplaneModeOn(Context context) {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
	        return Settings.System.getInt(context.getContentResolver(), 
	                Settings.System.AIRPLANE_MODE_ON, 0) != 0;          
	    } else {
	        return Settings.Global.getInt(context.getContentResolver(), 
	                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
	    }
	}
	
}
