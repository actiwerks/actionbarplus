package actiwerks.backgroundmultiapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainView extends ViewGroup {
	
	private TextView infoLabel;
	
	private TextView extraLabel;
	
	private int sizeFromExtra;
	
	private Paint BLACK_PAINT;
	
	private TypedValue tv;
	
	public MainView(Context context) {
		super(context);
		infoLabel = new TextView(context);
		addView(infoLabel);
		extraLabel = new TextView(context);
		addView(extraLabel);
		setWillNotDraw(false);
		BLACK_PAINT = new Paint();
		BLACK_PAINT.setColor(Color.BLACK);
		BLACK_PAINT.setAntiAlias(true);
		BLACK_PAINT.setStrokeWidth(3f);
		tv = new TypedValue();
	}
	
	
	public void setExtra(int extra) {
		extraLabel.setText("Peek value: " + extra);
		sizeFromExtra = extra;
		requestLayout();
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(canvas.getWidth()-100, 0, canvas.getWidth()-100, canvas.getHeight(), BLACK_PAINT);
		canvas.drawLine(canvas.getWidth()-90, canvas.getHeight() - 10, canvas.getWidth()-100, canvas.getHeight(), BLACK_PAINT);
		canvas.drawLine(canvas.getWidth()-110, canvas.getHeight() - 10, canvas.getWidth()-100, canvas.getHeight(), BLACK_PAINT);
		canvas.drawLine(canvas.getWidth()-90, 10, canvas.getWidth()-100, 0, BLACK_PAINT);
		canvas.drawLine(canvas.getWidth()-110,10, canvas.getWidth()-100, 0, BLACK_PAINT);
		
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
		infoLabel.setText("Measured height: " + heightSpecSize);
		infoLabel.measure(View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY), 
				View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY));
		extraLabel.measure(View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY), 
				View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY));
		if(sizeFromExtra >0 ) {
			getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
			int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);
			if(sizeFromExtra > actionBarHeight) {
				heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(sizeFromExtra-actionBarHeight, View.MeasureSpec.EXACTLY);
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		infoLabel.layout(10, 10, 10+infoLabel.getMeasuredWidth(), 10+infoLabel.getMeasuredHeight());
		extraLabel.layout(10, 20+infoLabel.getMeasuredHeight(), 10+extraLabel.getMeasuredWidth(), 20+infoLabel.getMeasuredHeight()+extraLabel.getMeasuredHeight());
	}

}
