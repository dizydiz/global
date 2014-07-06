package com.instagolf.AppUtil;

import com.instagolf.R;
import com.instagolf.Activity.Splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;


public class PlaySound {
public void playBeep(final Context context,final Activity activity) {
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			final MediaPlayer m = new MediaPlayer();
			try {
				m.setOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {	
						m.release();
						activity.startActivity(new Intent(context,Splash.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						activity.finish();
						
						activity.overridePendingTransition(R.anim.activityfadein, R.anim.splashfadeout);
					}
				});
		        AssetFileDescriptor descriptor = context.getAssets().openFd("ball.mp3");
		        m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
		       

		        descriptor.close();
		        m.prepare();
		        m.setVolume(1f, 1f);
		        m.start();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }   
		}
	}).start();
	}
}
