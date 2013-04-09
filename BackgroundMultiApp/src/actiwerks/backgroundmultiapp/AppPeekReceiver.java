package actiwerks.backgroundmultiapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppPeekReceiver extends BroadcastReceiver {


	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction() != null && intent.getAction().equals("actiwerks.intent.peekapp")) {
			int peekSize = intent.getIntExtra("APP_PEEK_SIZE", -1);
			if(MainActivity.getInstance() != null) {
				MainActivity.getInstance().setPeekInfo(peekSize);
			}
		} else {
			Log.i("AppPeek", "Intent not recognized : " + intent);
		}
	}

}
