package com.sportsbetting.AppUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

public class FontStyle {

	private final String HELVETICA_LIGHT = "helvetica_neue_light.ttf";
	private final String HELVETICA_MEDIUM = "helvetica_neue.ttf";
	private final String HELVETICA_BOLD = "helvetica_neue_bold.ttf";

	private Context context;

	public FontStyle(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public void setHelveticaLight(View view) {
		try {
			Typeface tf = Typeface.createFromAsset(context.getAssets(),
					HELVETICA_LIGHT);
			TextView tv = (TextView) view;
			tv.setTypeface(tf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setHelveticaRegular(View view) {
		try {
			Typeface tf = Typeface.createFromAsset(context.getAssets(),
					HELVETICA_MEDIUM);
			TextView tv = (TextView) view;
			tv.setTypeface(tf);
			tv.setTextColor(Color.BLACK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setHelveticaBold(View view) {
		try {
			Typeface tf = Typeface.createFromAsset(context.getAssets(),
					HELVETICA_BOLD);
			TextView tv = (TextView) view;
			tv.setTypeface(tf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
