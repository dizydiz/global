package com.autolog.AppUtil;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

public class Display extends Application{
	private static DisplayMetrics metrics;
	
	public static int getHeightPixels(Activity activity){
		metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
	
	public static int getWidthPixels(Activity activity){
		metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}
	
	public static int getDensity(Activity activity){
		metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.densityDpi;
	}
}
