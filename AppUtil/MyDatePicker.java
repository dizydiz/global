package com.autolog.AppUtil;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class MyDatePicker {

	private int day,month,year;
	private Context context;
	private Button btnBirthDate;
	
	public MyDatePicker(Context context,View view){
		this.context = context;
		this.btnBirthDate = (Button) view;
		setCurrentDateOnView();
	}
	public void datePicker(){
		 
		new DatePickerDialog(context, datePickerListener, year, month,
				day).show();
	}
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@SuppressLint("SimpleDateFormat")
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			String strMonth,strDay;
			int tempMonth = month;
			tempMonth = (month+1);
			strMonth = tempMonth<10?"0"+tempMonth:""+tempMonth;
			strDay = day<10?"0"+day:""+day;
			
			// set selected date into textview
			btnBirthDate.setText(year+"-"+strMonth+"-"+strDay);
			
//			btnBirthDate.setText(new StringBuilder().append(year).append("-")
//					.append(month + 1).append("-").append(day).append(" "));

		}
	};
	
	public void setCurrentDateOnView() {

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
	}
}
