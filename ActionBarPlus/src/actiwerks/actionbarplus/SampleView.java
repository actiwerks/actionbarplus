package actiwerks.actionbarplus;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SampleView extends ViewGroup {
	
	private TextView sampleContent;
	
	private TextView bottomContent;
	
	/**
     * Slop before starting a drag.
     */
    protected int touchSlop;
    
    /**
     * Indicates whether the drawer is currently being dragged.
     */
    protected boolean dragging;
    
    
    protected float lastMotionX;
    
    
    private static int offsY = 0;

	public SampleView(Context context) {
		super(context);
		sampleContent = new TextView(context);
		setBackgroundColor(Color.YELLOW);
		sampleContent.setTextColor(Color.BLACK);
		addView(sampleContent);
		sampleContent.setText("My content here");
		bottomContent = new TextView(context);
		bottomContent.setTextColor(Color.BLACK);
		addView(bottomContent);
		bottomContent.setText("This is at the bottom of the view");
	}
	
	public static void setOffsY(int offsYS) {
		offsY = offsYS;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
		sampleContent.setText("My content here : " + heightSpecSize + " offsY : " + offsY);
		sampleContent.measure(View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY), 
				View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY));
		bottomContent.measure(View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY), 
				View.MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY));
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		Log.i("ABP", "SampleView l:"+l+",t:"+t+",r:"+r+",b:"+b);
//		Log.i("ABP", "sampleContent size w:"+sampleContent.getMeasuredWidth()+",h:"+sampleContent.getMeasuredHeight());
		sampleContent.layout((r-l-sampleContent.getMeasuredWidth())/2, 
				100, 
				(r-l-sampleContent.getMeasuredWidth())/2+sampleContent.getMeasuredWidth(), 
				100+sampleContent.getMeasuredHeight());
//		sampleContent.layout(10, 
//				t+200, 
//				10+sampleContent.getMeasuredWidth(), 
//				t+200+sampleContent.getMeasuredHeight());
		bottomContent.layout(10, 
				b-t-50-bottomContent.getMeasuredHeight(), 
				10+bottomContent.getMeasuredWidth(), 
				b-t-50);
	}
	
	
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
        
        if (action != MotionEvent.ACTION_DOWN) {
            if (dragging) { 
            	return true;
            }
        }
        
        switch (action) {
        	case MotionEvent.ACTION_DOWN: {
        		dragging = true;
        		break;
        	}
        	case MotionEvent.ACTION_MOVE: {
        		break;
        	}
        	case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
            	dragging = false;
            	break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
            	Log.i("SV", "Intercept Pointer down :" + ev.getPointerCount());
            	break;
            }
        }
        
        return dragging;
	}
	
	
	@Override
    public boolean onTouchEvent(MotionEvent ev) {
		final int action = ev.getAction() & MotionEvent.ACTION_MASK;
		switch (action) {
			case MotionEvent.ACTION_DOWN: {
				break;
			}
			case MotionEvent.ACTION_POINTER_DOWN: {
            	Log.i("SV", "Pointer down :" + ev.getPointerCount());
            	break;
            }
			
			case MotionEvent.ACTION_MOVE: {
				final float x = ev.getX();
                final float dx = x - lastMotionX;
                //Log.i("SV", "Action move :" + x + "," + ev.getY());
                lastMotionX = x;
                break;
			}
			
			case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                //onUpEvent(ev);
                break;
            }
		}
		
		return true;
	}

}
