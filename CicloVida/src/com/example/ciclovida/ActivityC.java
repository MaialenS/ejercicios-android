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
import android.os.Build;

public class ActivityC extends Activity {
	
	public void abrirB(View v){
		
		Intent intent = new Intent(this, ActivityB.class);
		startActivity(intent);
		
	}
	public void abrirA(View v){
		Intent intent = new Intent(this, ActivityA.class);
		startActivity(intent);
	}
	public void cerrar(View v){
		
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_c);

		Log.d("Act C", "onCreate");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_c, menu);
		
		Log.d("Act C", "onCreateOptionsMenu");
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Log.d("Act C", "onOptionsItemSelected");
		
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
	     
	     Log.d("Act C", "onRestart");
	     
	}
	
	// Called at the start of the visible lifetime. @Override
	public void onStart(){
	     super.onStart();
	     // Apply any required UI change now that
	     // the Activity is visible.
	     
	     Log.d("Act C", "onStart");
	     
	}
	
	// Called at the start of the active lifetime. @Override
	public void onResume(){
	     super.onResume();
	     // Resume any paused UI updates, threads, or processes required
	     // by the Activity but suspended when it was inactive.
	     
	     Log.d("Act C", "onResume");
	     
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
	     
	     Log.d("Act C", "onSaveInstanceState");
	     
	}
	
	// Called at the end of the active lifetime. @Override
	public void onPause(){
	     // Suspend UI updates, threads, or CPU intensive processes
	     // that don’t need to be updated when the Activity isn’t
	     // the active foreground Activity.
	     super.onPause();
	     
	     Log.d("Act C", "onPause");
	     
	}
	
	// Called at the end of the visible lifetime. @Override
	public void onStop(){
	     // Suspend remaining UI updates, threads, or processing
	     // that aren’t required when the Activity isn’t visible.
	     // Persist all edits or state changes
	     // as after this call the process is likely to be killed.
	     super.onStop();
	     
	     Log.d("Act C", "onStop");
	     
	}
	
	// Sometimes called at the end of the full lifetime. @Override
	public void onDestroy(){
	     // Clean up any resources including ending threads,
	     // closing database connections etc.
	     super.onDestroy();
	     
	     Log.d("Act C", "onDestroy");
	     
	}
	

}
