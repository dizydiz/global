package com.autolog.AppUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class DateTimeFunctions {

	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String tsForUpload(String ts) {
		String oldFormat = "yyyy-MM-dd HH:mm:ss";
		String newFormat = "dd/MM/yyyy HH:mm:ss";

		SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
		SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);

		String strDate = "";
		try {
			System.out.println(sdf2.format(sdf1.parse(ts)));
			strDate = sdf2.format(sdf1.parse(ts));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// Log.v("tsForUpload",">"+strDate);
		return strDate;
	}

	public static String getCurrentTime() {
		String time = "";
		Calendar c = Calendar.getInstance();
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		String strHH = "";
		String strMM = "";
		if (h < 10) {
			strHH = "0" + h;
		} else {
			strHH = "" + h;
		}

		if (m < 10) {
			strMM = "0" + m;
		} else {
			strMM = "" + m;
		}

		time = strHH + ":" + strMM;
		return time;
	}

	public static String getConvertedTime(int h, int m) {
		String time = "";
		String strHH = "";
		String strMM = "";
		if (h < 10) {
			strHH = "0" + h;
		} else {
			strHH = "" + h;
		}

		if (m < 10) {
			strMM = "0" + m;
		} else {
			strMM = "" + m;
		}

		time = strHH + ":" + strMM;
		return time;
	}

	public static int getDiffDate(String dateStart ,String dateEnd) {

		int difT = 0;
//		SimpleDateFormat inFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		
		try {
			date1 = inFormat.parse(dateStart);
			date2 = inFormat.parse(dateEnd);
			difT = (int) ((date2.getTime()/60000) - (date1.getTime()/60000));
			
			Log.e("min",">"+difT);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return difT;
	}
}
