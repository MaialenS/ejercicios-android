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
		this.calculos = new Calculos();

	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// salvar el estado
		Log.d("cal", "salvar el estado " + savedInstanceState);

		savedInstanceState.putString(this.calculos.STATE_NUM1,
				this.calculos.numero1);
		savedInstanceState.putString(this.calculos.STATE_NUM2,
				this.calculos.numero2);
		savedInstanceState.putString(this.calculos.STATE_NUM_MOSTRAR,
				this.calculos.mostrando);
		savedInstanceState.putString(this.calculos.STATE_OPERANDO,
				this.calculos.operando);
		savedInstanceState.putBoolean(this.calculos.STATE_PUNTO,
				this.calculos.punto);

		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d("cal", "restaurar el estado " + savedInstanceState);
		// Always call the superclass so it can restore the view hierarchy
		super.onRestoreInstanceState(savedInstanceState);

		this.calculos.numero1 = savedInstanceState
				.getString(this.calculos.STATE_NUM1);
		this.calculos.numero2 = savedInstanceState
				.getString(this.calculos.STATE_NUM2);
		this.calculos.mostrando = savedInstanceState
				.getString(this.calculos.STATE_NUM_MOSTRAR);
		this.calculos.operando = savedInstanceState
				.getString(this.calculos.STATE_OPERANDO);
		this.calculos.punto = savedInstanceState
				.getBoolean(this.calculos.STATE_PUNTO);

	}

	@Override
	protected void onResume() {
		super.onResume();
		String pan = this.calculos.mostrando;
		this.pantalla.setText(pan);
	}

	// funciones de interaccion con la activity

	public void operar(View v) {
		Log.d("cal", "operar");
		Button btn = (Button) v;
		String op = btn.getText().toString();
		Log.d("cal", "operador " + op);

		String pan = this.calculos.operador(op);
		this.pantalla.setText(pan);
	}

	public void addNumero(View v) {
		Button btn = (Button) v;
		String num = btn.getText().toString();
		Log.d("cal", "numero " + num);
		String pan = this.calculos.addNumero(num);
		this.pantalla.setText(pan);

	}

	public void borrar(View v) {
		Log.d("cal", "borrar");
		Button btn = (Button) v;
		String op = btn.getText().toString();

		String pan = this.calculos.borrar(op);
		this.pantalla.setText(pan);

	}
}
