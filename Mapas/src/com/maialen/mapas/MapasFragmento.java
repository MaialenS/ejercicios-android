package com.maialen.mapas;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MapasFragmento extends Fragment{
	
	TextView textLat, textLon, textAltura;
	ListView listaPosiciones;
	Button refrescar;
	
	
	
	String serviceString;
	LocationManager locationManager;
	String bestProvider;
	LocationProvider provider;
	

	ArrayList<String> listaLugares;
	
	ArrayAdapter adapter;
	
	View.OnClickListener refrescarClick = new View.OnClickListener() {
		public void onClick(View v) {
			
			ponerUltimaPosicionConocida();
	        	
	    }
	};
	
	
	LocationListener locListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

			
			String lat=String.valueOf(location.getLatitude());
			String lon=String.valueOf(location.getLongitude());
			String nuevo= "Lat: "+lat+"\nLon: "+lon;
			listaLugares.add(0, nuevo);
			
			adapter.notifyDataSetChanged();
			
		}
	};
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		
		textLat= (TextView) v.findViewById(R.id.textLat);
		textLon= (TextView) v.findViewById(R.id.textLon);
		textAltura= (TextView) v.findViewById(R.id.textAlt);
		listaPosiciones= (ListView) v.findViewById(R.id.listaPosiciones);
		refrescar = (Button) v.findViewById(R.id.btnRefrescar);
		
		refrescar.setOnClickListener(refrescarClick);

		listaLugares= new ArrayList<String>();
		

		adapter= new ArrayAdapter(getActivity(),  android.R.layout.simple_list_item_1, listaLugares);
		
		listaPosiciones.setAdapter(adapter);

		
		////para la localizacion
		serviceString = getActivity().LOCATION_SERVICE;
		locationManager = (LocationManager) getActivity().getSystemService (serviceString);
		//decidir el tipo de proveedor, que lo haga android
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		  //criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
		criteria.setPowerRequirement(Criteria.POWER_LOW); criteria.setAltitudeRequired(false);
		criteria.setSpeedRequired(false);
		
		bestProvider = locationManager.getBestProvider(criteria, true);
		provider = locationManager.getProvider(bestProvider);
		
		int t = 5000; // milliseconds
		int distance = 5; // meters
		
		locationManager.requestLocationUpdates(bestProvider, t, distance, locListener);

		
		ponerBroadcastLocalizacion();
		return v;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ponerUltimaPosicionConocida();
	}
	
	private void ponerUltimaPosicionConocida(){
		
		 Location location = locationManager.getLastKnownLocation(bestProvider);
		 
		 if(location!=null){

			 textLat.setText(String.valueOf(location.getLatitude()));
			 textLon.setText(String.valueOf(location.getLongitude()));
			 textAltura.setText(String.valueOf(location.getAltitude()));
		 }
	}
	
	private void ponerBroadcastLocalizacion(){
		
		
		int t = 5000; // milliseconds
		int distance = 5; // meters
		int flags = PendingIntent.FLAG_UPDATE_CURRENT;
		
		Intent intent = new Intent(getActivity(), MyLocationUpdateReceiver.class); 
		PendingIntent pendingIntent =
		PendingIntent.getBroadcast(getActivity(), 0, intent, flags);
		
		locationManager.requestLocationUpdates(bestProvider, t, distance, pendingIntent);
	}
	
	
	
	

}
