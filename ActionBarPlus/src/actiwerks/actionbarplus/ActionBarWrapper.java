package actiwerks.actionbarplus;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ActionBarWrapper extends ViewGroup {

	private ActionBarPlus actionBarPlus;
	private ViewGroup contentView;
	
	private Intent peekIntent;
	
	private int yOffset;
	
	public ActionBarWrapper(Context context, ActionBarPlus actionBarPlus) {
		super(context);
		this.actionBarPlus = actionBarPlus;
		peekIntent = new Intent();
		peekIntent.setAction("actiwerks.intent.peekapp");
	}
	
	public void setContentView(ViewGroup contentView) {
		this.contentView = contentView;
		addView(contentView);
		requestLayout();
	}
	
	
	public void setVerticalOffset(int offset) {
		Log.i("ABP", "YOffs: " + offset);
		
		if(offset < 0) {
			yOffset = 0;
			requestLayout();
			peekIntent.removeExtra("APP_PEEK_SIZE");
			peekIntent.putExtra("APP_PEEK_SIZE", yOffset);
			getContext().sendBroadcast(peekIntent);
			return;
		}
		//invalidate();
		if(yOffset != offset) {
			yOffset = offset;
			requestLayout();
//			if(peekIntent.getExtras() != null) {
//				peekIntent.getExtras().putInt("APP_PEEK_SIZE", yOffset);
//			} else {
//				peekIntent.putExtra("APP_PEEK_SIZE", yOffset);
//			}
			peekIntent.removeExtra("APP_PEEK_SIZE");
			peekIntent.putExtra("APP_PEEK_SIZE", yOffset);
			getContext().sendBroadcast(peekIntent);
		}
	}
	
	
	public int getVerticalOffset() {
		return yOffset;
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
		Log.i("ABP", "ABPW measure : " + widthSpecSize + "," + heightSpecSize + " of " + widthMeasureSpec + "," + heightMeasureSpec);
		actionBarPlus.measure(View.MeasureSpec.makeMeasureSpec(widthSpecSize, View.MeasureSpec.EXACTLY), 
				View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY));
		if(contentView != null) {
			SampleView.setOffsY(yOffset);
			contentView.measure(View.MeasureSpec.makeMeasureSpec(widthSpecSize, View.MeasureSpec.EXACTLY), 
					View.MeasureSpec.makeMeasureSpec(heightSpecSize-actionBarPlus.getMeasuredHeight()-yOffset, View.MeasureSpec.EXACTLY));
		}
		super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(heightSpecSize, View.MeasureSpec.EXACTLY));
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.i("ABP", "ActionBarWrapper l:"+l+",t:"+t+",r:"+r+",b:"+b);
		actionBarPlus.layout(l, t+yOffset, l+actionBarPlus.getMeasuredWidth(), t+yOffset+actionBarPlus.getMeasuredHeight());
		if(contentView != null) {
			contentView.layout(l, t+yOffset+actionBarPlus.getMeasuredHeight(),
					l+contentView.getMeasuredWidth(), b /*t+yOffset+actionBarPlus.getMeasuredHeight()+contentView.getMeasuredHeight()*/);
		} else {
			Log.i("ABP", "ActionBarWrapper content view null");
		}
	}

}
