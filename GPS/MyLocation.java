package com.autolog.GPS;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.autolog.Dao.TrackDao;
import com.autolog.GlobalData.GlobalField;

public class MyLocation extends Service {

	private LocationManager lm;
	private Location myLocation;
	private LocationListener locationListener;
	private final long MIN_TIME = 60000;// Millisecond - 1 min
	private final float MIN_DISTANCE = 1000;// meter - 1 km

	private TrackDao trackDao;
	private String strTripId;
	@Override
	public void onStart(Intent intent, int startid) {
		Log.e("start", "Called");		
		if(intent==null){
			Log.e("return", "Called");
			return;
		}


		strTripId = intent.getStringExtra(GlobalField.TRIP_ID);
		myLocation = null;
		trackDao = new TrackDao();
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//		Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // Faster, no GPS fix.

//		String provider = lm.getBestProvider(criteria, true); // only retrieve
//																// enabled
//																// providers.
//		if (provider == null) {
//
//			stopSelf();
//			return;
//		}

		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				if (lm != null) {
					makeUseOfNewLocation(location);
				}
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// Toast.makeText(c, "Out Of Service",
				// Toast.LENGTH_LONG).show();
			}

			public void onProviderEnabled(String provider) {
				// Toast.makeText(c, "Provider enable",
				// Toast.LENGTH_LONG).show();
			}

			public void onProviderDisabled(String provider) {
				// Toast.makeText(c, "Provider disable",
				// Toast.LENGTH_LONG).show();
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
//		lm.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE,
//				locationListener);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE,
				locationListener);
		
	}

	private void makeUseOfNewLocation(Location location) {
		myLocation = location;
		trackDao.addLocation(getApplicationContext(), strTripId, myLocation);
		
//		Log.e("location", "Found>" + myLocation.getLatitude() + "."
//				+ myLocation.getLongitude());
//
//		final String latitude = "" + myLocation.getLatitude();
//		final String longitude = "" + myLocation.getLongitude();
//
//		if (locationP != null) {
//			Log.e("distance", ">>" + locationP.distanceTo(location));
//			Toast.makeText(getApplicationContext(),
//					"distance>>" + locationP.distanceTo(location),
//					Toast.LENGTH_SHORT).show();
//			float[] dis = new float[10];
//			Location.distanceBetween(locationP.getLatitude(),
//					locationP.getLongitude(), location.getLatitude(),
//					location.getLongitude(), dis);
//			Log.e("dis", ">" + dis[0]);
//
//		}
//
//		locationP = location;

	}

	void removeUpdate() {

		try {
			Log.e("location", "Destroy Called");

			lm.removeUpdates(locationListener);
			locationListener = null;
			lm = null;
			stopSelf();
		} catch (Exception e) {
			locationListener = null;
			lm = null;
			stopSelf();
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		removeUpdate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
