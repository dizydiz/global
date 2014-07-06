package com.sportsbetting.gcm;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sportsbetting.Home;
import com.sportsbetting.R;
import com.sportsbetting.GlobalData.GlobalField;

/**
 * Handling of GCM messages.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
	static final String TAG = "GCMDemo";
	public static int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	Context ctx;

	@Override
	public void onReceive(Context context, Intent intent) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
		ctx = context;
		String messageType = gcm.getMessageType(intent);
		if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
			sendNotification("Send error: " + intent.getExtras().toString(),
					context);
		} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
				.equals(messageType)) {
			sendNotification("Deleted messages on server: "
					+ intent.getExtras().toString(), context);
		} else {
			sendNotification(intent.getStringExtra("message"), context);
		}
		setResultCode(Activity.RESULT_OK);
	}

	// Put the GCM message into a notification and post it.
	private void sendNotification(String msg, Context context) {

		// Log.v("msg",">"+msg);
		mNotificationManager = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent intent = new Intent(ctx, Home.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(GlobalField.COME_FROM,
				GlobalField.COME_FROM_CHECK_LOGIN);

		PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, intent,
				0);

		Uri alarmSound = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				ctx).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(GlobalField.APP_TITLE)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);
		mBuilder.setSound(alarmSound);
		mBuilder.setAutoCancel(true);
		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID++, mBuilder.build());

	}
}