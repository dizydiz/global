package com.instagolf.AppUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class AppUtils {
   
	public static void setCredential(Context cont, String uId){ 
    	try{
    		
    		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cont);
    		SharedPreferences.Editor editor = preferences.edit();
    		editor.putString("userid", uId);
    		editor.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
   
	public static void setUserCred(Activity act, String uId){ 
    	try{
    		SharedPreferences pref = act.getApplicationContext().getSharedPreferences(
    	            "sharedata", act.MODE_PRIVATE);
    		Editor editor;
    		editor = pref.edit();
    		editor.putString("userid", uId);
    	    //Log.e("userid","+"+uId);
    	    editor.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	public static String getUserId(Activity act){
    	String strUserId = "";
    	try{
    		SharedPreferences preferences = act.getApplicationContext().getSharedPreferences(
    	            "sharedata", act.MODE_PRIVATE);
    		//SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cont);
    		strUserId = preferences.getString("userid", "");  		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return strUserId;
    }
	public static void clearCredential(Activity act){
    	try{
    		SharedPreferences pref = act.getApplicationContext().getSharedPreferences(
	                 "sharedata", act.MODE_PRIVATE);
			Editor editor = pref.edit();
			editor.clear();
			editor.commit();
			
//    		if(isUserLogin(cont)){
//    			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cont);    		
//    			isClear = preferences.edit().clear().commit();
//    		}else
//    			AlertDialogManager.showMessage(cont, "You are not Loged In...");
    		/*SharedPreferences sharedPrefs = getDefaultSharedPreferences(this);
    		Editor editor = sharedPrefs.edit();
    		editor.clear();
    		editor.commit();*/
//    		editor.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
