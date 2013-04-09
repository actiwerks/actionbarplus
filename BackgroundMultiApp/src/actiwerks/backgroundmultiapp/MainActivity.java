package actiwerks.backgroundmultiapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private MainView mainView;
	
	private static MainActivity instance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("AppPeek", "App started");
		mainView = new MainView(this);
		setContentView(mainView);
		instance = this;
	}

	public static MainActivity getInstance() {
		return instance;
	}
	
	
	public void setPeekInfo(int peekValue) {
		mainView.setExtra(peekValue);
	}
	
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		MenuItem startItem = menu.add(Menu.NONE, 1, Menu.NONE, "Start ABP");
		startItem.setIcon(android.R.drawable.ic_menu_slideshow);
		if(android.os.Build.VERSION.SDK_INT >= 11) {
			startItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			startABP();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	public void startABP() {
		Intent startABPIntent = new Intent();
		startABPIntent.setComponent(new ComponentName("actiwerks.actionbarplus", "actiwerks.actionbarplus.SampleActivity"));
		startActivity(startABPIntent);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onNewIntent(android.content.Intent)
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		Log.i("AppPeek", "onNewIntent : " + intent);
		super.onNewIntent(intent);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}
	
	
	

}
