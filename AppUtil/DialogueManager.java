package com.sportsbetting.AppUtil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sportsbetting.R;

public class DialogueManager {

//		public static void showDialogue(Context context, String strMsg){
//			try{
//				Toast.makeText(context, strMsg, Toast.LENGTH_LONG).show();
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
	
	public static void showDialogue(Activity activity,String strMsg){
		try{
			LayoutInflater inflater = activity.getLayoutInflater();
			View layout = inflater.inflate(R.layout.toast,
			                               (ViewGroup) activity.findViewById(R.id.toast_layout_root));

//			ImageView image = (ImageView) layout.findViewById(R.id.image);
//			image.setImageResource(R.drawable.btn_yellow);
			TextView text = (TextView) layout.findViewById(R.id.text);
			text.setText(strMsg);

			Toast toast = new Toast(activity.getApplicationContext());
//			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
		
}
