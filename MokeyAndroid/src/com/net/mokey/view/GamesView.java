package com.net.mokey.view;

import com.net.mokey.application.MoKeyApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class GamesView extends TextView implements GestureDetector.OnGestureListener{
	private GestureDetector mGestureDetector = new GestureDetector(this);
	Context context;
	public GamesView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GamesView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mGestureDetector = new GestureDetector(this);
	}
	/*@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension((int) (MoKeyApplication.getInstance().getDisplayHightAndWightPx()[1]/3-5), 2);
	}*/

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "game====", 1).show();
		return false;
	}
	public boolean onTouchEvent(MotionEvent paramMotionEvent)
	  {
	    this.mGestureDetector.onTouchEvent(paramMotionEvent);
	    return true;
	  }
}
