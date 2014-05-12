package com.maialen.listafragmentos;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Choreographer.FrameCallback;
import android.os.Build;

public class MainActivity extends Activity implements FragmentFrom.IAddItem {

	private TareasFragment fragTareas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			
			FragmentFrom fragFormulario = new FragmentFrom();
			fragTareas = new TareasFragment();
			
			fragmentTransaction.add(R.id.fragForm, fragFormulario);
			fragmentTransaction.add(R.id.fragList, fragTareas, "lista");
			fragmentTransaction.commit();
			
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

	@Override
	public void addItem(String txt) {
		// TODO Auto-generated method stub
		TareasFragment lista= (TareasFragment) getFragmentManager().findFragmentByTag("lista");
		lista.addTarea(txt);
	}

	

}
