package com.maialen.pruebaintent;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;


public class LlamadasBroadcast extends BroadcastReceiver{
	private static final String SMS_EXTRA_NAME = "pdus";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub  android.intent.action.AIRPLANE_MODE
		Log.w("ACT", "QUE PASA ALGO");
		String action = intent.getAction();
		
		Log.w("ACT", "action-> "+action);
		if (action.equals("android.intent.action.PHONE_STATE")){
			tratarLlamada(intent.getExtras());
			
		}else if (action.equals("android.provider.Telephony.SMS_RECEIVED")){
			
			tratarMensaje(intent.getExtras());
		}else if (action.equals("android.intent.action.AIRPLANE_MODE")){
			tratarAirPlane(intent.getExtras());
			
		}else if(action.equals("android.intent.action.SERVICE_STATE")){
			tratarCambioEstado(intent.getExtras());
			
		}
		
		else if(action.equals("android.net.conn.CONNECTIVITY_CHANGE")){
			tratarCambioConexion(intent.getExtras());
			
		}
		
	}
	
	private void tratarLlamada(Bundle extras){
		if (extras != null) {
		      String state = extras.getString(TelephonyManager.EXTRA_STATE);
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
	
	private void tratarCambioConexion(Bundle extras){
		
		Log.w("ACT", "cambio de conexion");
		
		
	}
	
	private void tratarAirPlane(Bundle extras){
		//Log.w("ACT", "airplane "+Settings.Global.AIRPLANE_MODE_ON);
		Log.w("ACT", "airplane ");
		
		
	}

}
