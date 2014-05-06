package com.example.ciclovida;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class ActivityA extends Activity {
	
	public void abrirB(View v){
		
		Intent intent = new Intent(this, ActivityB.class);
		startActivity(intent);
		
	}
	public void abrirC(View v){
		Intent intent = new Intent(this, ActivityC.class);
		startActivity(intent);
	}
	public void cerrar(View v){
		
		finish();
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity);
		
		Log.d("Act A", "onCreate");
		
		//poner los botones
		Button botonB = (Button) findViewById(R.id.button1); //Definimos el boton
		Button botonC = (Button) findViewById(R.id.button2); //Definimos el boton
		Button botonCerrar = (Button) findViewById(R.id.button3); //Definimos el boton
       
		
		botonB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Log.d("Act A", "Se ha pulsado el boton B");
            }
        });
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity, menu);
		
		Log.d("Act A", "onCreateOptionsMenu");
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Log.d("Act A", "onOptionsItemSelected");
		
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
	public void onRestart(){
	     super.onRestart();
	     // Load changes knowing that the Activity has already
	     // been visible within this process.
	     
	     Log.d("Act A", "onRestart");
	     
	}
	
	// Called at the start of the visible lifetime. @Override
	public void onStart(){
	     super.onStart();
	     // Apply any required UI change now that
	     // the Activity is visible.
	     
	     Log.d("Act A", "onStart");
	     
	}
	
	// Called at the start of the active lifetime. @Override
	public void onResume(){
	     super.onResume();
	     // Resume any paused UI updates, threads, or processes required
	     // by the Activity but suspended when it was inactive.
	     
	     Log.d("Act A", "onResume");
	     
	}
	
	// Called to save UI state changes at the
	// end of the active lifecycle.
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	     // Save UI state changes to the savedInstanceState.
	     // This bundle will be passed to onCreate and
	     // onRestoreInstanceState if the process is
	     // killed and restarted by the run time.
	     super.onSaveInstanceState(savedInstanceState);
	     
	     Log.d("Act A", "onSaveInstanceState");
	     
	}
	
	// Called at the end of the active lifetime. @Override
	public void onPause(){
	     // Suspend UI updates, threads, or CPU intensive processes
	     // that don’t need to be updated when the Activity isn’t
	     // the active foreground Activity.
	     super.onPause();
	     
	     Log.d("Act A", "onPause");
	     
	}
	
	// Called at the end of the visible lifetime. @Override
	public void onStop(){
	     // Suspend remaining UI updates, threads, or processing
	     // that aren’t required when the Activity isn’t visible.
	     // Persist all edits or state changes
	     // as after this call the process is likely to be killed.
	     super.onStop();
	     
	     Log.d("Act A", "onStop");
	     
	}
	
	// Sometimes called at the end of the full lifetime. @Override
	public void onDestroy(){
	     // Clean up any resources including ending threads,
	     // closing database connections etc.
	     super.onDestroy();
	     
	     Log.d("Act A", "onDestroy");
	     
	}
	
	
	
	
	protected void onRestoreInstanceState (Bundle savedInstanceState){
		
			super.onRestoreInstanceState(savedInstanceState);
		
		 Log.d("Act A", "onRestoreInstanceState");
	}
	

}
