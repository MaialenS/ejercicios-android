package com.maialen.calculadora;



import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	
	Calculos calculos;
	TextView pantalla;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 this.pantalla = (TextView) findViewById(R.id.pantalla); 
		this.calculos= new Calculos();
		
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
	
	
	//funciones de interaccion con la activity 
	
	public void operar(View v){
		Button btn = (Button)v;
		String op = btn.getText().toString();
		Log.d("cal", "operador "+op);
		
	}

	public void addNumero(View v){
		Button btn = (Button)v;
		String num = btn.getText().toString();
		Log.d("cal", "numero "+num);
		String pan= this.calculos.addNumero(num);
		this.pantalla.setText(pan);
		
	}
}
