package actiwerks.actionbarplus;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;

public class SampleActivity extends Activity {

	private SampleView sampleView;
	private ActionBarPlus actionBarPlus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		
		sampleView = new SampleView(this);
		//setContentView(sampleView);
		actionBarPlus = new ActionBarPlus(this);
		actionBarPlus.attachContentView(sampleView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sample, menu);
		return true;
	}


}
