package com.sportsbetting.AppUtil;

import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ClickEffect implements OnTouchListener {
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			v.getBackground().setColorFilter(0xe0D1D0CE,
					PorterDuff.Mode.MULTIPLY);
			v.invalidate();
			break;
		}
		case MotionEvent.ACTION_UP: {
			v.getBackground().clearColorFilter();
			v.invalidate();
			break;
		}
		}
		return false;
	}
}
