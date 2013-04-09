package actiwerks.actionbarplus;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ActionBarPlus extends ViewGroup {

	private Activity parentActivity;
	private int height;
	private TextView titleLabel;
	
	private ActionBarWrapper abWrapper;
	private ViewGroup contentView;
	
	private float yMoveStart;
	
	public ActionBarPlus(Activity activityContext) {
		super(activityContext);
		parentActivity = activityContext;
		parentActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ActionBar activityActionBar = parentActivity.getActionBar();
		titleLabel = new TextView(activityContext);
		titleLabel.setTextColor(Color.WHITE);
		titleLabel.setTextSize(20f);
		addView(titleLabel);
		if(activityActionBar != null) {
			height = activityActionBar.getHeight();
			TypedValue tv = new TypedValue();
			activityContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
			int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);
			height = actionBarHeight;
			Log.i("ABP", "orig heigh:"+actionBarHeight);
			if(activityActionBar.getTitle() != null) {
				titleLabel.setText("AB title: " + activityActionBar.getTitle());
			} else {
				titleLabel.setText(getApplicationName(activityContext));
			}
			activityActionBar.hide();
			
		} else {
			TypedValue tv = new TypedValue();
			activityContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
			int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);
			height = actionBarHeight;
			titleLabel.setText("No title from AB");
		}
		if(height < 56) {
			height = 56;
		}
		//Log.i("ABP", "Height : " + height);
		
		abWrapper = new ActionBarWrapper(activityContext, this);
		abWrapper.addView(this);
		parentActivity.setContentView(abWrapper);
		setBackgroundColor(Color.DKGRAY);
	}
	
	public void attachContentView(ViewGroup contentView) {
		this.contentView = contentView;
		//parentActivity.setTheme( R.style.Theme_Transparent);
		abWrapper.setContentView(contentView);
	}
	
	public static String getApplicationName(Context context) {
	    int stringId = context.getApplicationInfo().labelRes;
	    return context.getString(stringId);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
		//int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
		titleLabel.measure(View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY), 
				View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY));
		super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
	}
	
	
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//Log.i("ABP", "ActionBarPlus l:"+l+",t:"+t+",r:"+r+",b:"+b);
		titleLabel.layout(l+10, (b-t-titleLabel.getMeasuredHeight())/2, 
				l+10+titleLabel.getMeasuredWidth(), (b-t-titleLabel.getMeasuredHeight())/2+titleLabel.getMeasuredHeight());
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent ev) {
		final int action = ev.getAction() & MotionEvent.ACTION_MASK;
		switch (action) {
			case MotionEvent.ACTION_DOWN: {
				Log.i("ABP", "Action down :" + ev.getPointerCount());
				break;
			}
			case MotionEvent.ACTION_POINTER_DOWN: {
				Log.i("ABP", "Pointer down :" + ev.getPointerCount());
				if(ev.getPointerCount() == 2) {
					yMoveStart = ev.getRawY();
				}
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				Log.i("SV", "Action move :" + ev.getX() + "," + ev.getY() + " "  + ev.getRawY());
				if(ev.getPointerCount() == 2) {
					abWrapper.setVerticalOffset((int)(ev.getRawY()- yMoveStart));
				}
				break;
			}
		}
		return true;
	}

}
