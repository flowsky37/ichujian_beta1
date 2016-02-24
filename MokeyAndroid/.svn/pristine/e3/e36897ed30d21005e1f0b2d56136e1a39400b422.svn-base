package com.net.mokey.service;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TouchView extends LinearLayout implements GestureDetector.OnGestureListener{
	private Context mContext;
	private GestureDetector mGestureDetector;
	public TouchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.mGestureDetector = new GestureDetector(this);
		this.setBackgroundColor(Color.RED);
	}
	public TouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
	}
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
		Toast.makeText(mContext, "touch"+e.getX(), 1).show();
		return false;
	}
	public boolean onTouchEvent(MotionEvent paramMotionEvent)
	  {
	    this.mGestureDetector.onTouchEvent(paramMotionEvent);
	    return true;
	  }
}
