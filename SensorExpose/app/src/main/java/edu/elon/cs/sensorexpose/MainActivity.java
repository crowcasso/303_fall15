package edu.elon.cs.sensorexpose;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 *  Show  Accelerometer and GPS values.
 *
 *  @author J. Hollingsworth
 */


public class MainActivity extends Activity implements SensorEventListener {

    // needed for the Accelerometer
    private SensorManager sensorManager;
    private Sensor mAccel;
    private TextView xTextView, yTextView, zTextView;

    // needed for the AGPS
    private LocationManager locationManager;
    private TextView latTextView, longTextView, altTextView, accTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        xTextView = (TextView) findViewById(R.id.accel_x);
        yTextView = (TextView) findViewById(R.id.accel_y);
        zTextView = (TextView) findViewById(R.id.accel_z);

        // AGPS
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        latTextView = (TextView) findViewById(R.id.gps_lat);
        longTextView = (TextView) findViewById(R.id.gps_long);
        altTextView = (TextView) findViewById(R.id.gps_alt);
        accTextView = (TextView) findViewById(R.id.gps_acc);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Accelerometer
        sensorManager.unregisterListener(this);

        // AGPS
        locationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Accelerometer
        sensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);

        // AGPS
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }


    // AGPS listener
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latTextView.setText(location.getLatitude() + "");
            longTextView.setText(location.getLongitude() + "");
            altTextView.setText(location.getAltitude() + "");
            accTextView.setText(location.getAccuracy() + "");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    // Accelerometer listener
    @Override
    public void onSensorChanged(SensorEvent event) {
        xTextView.setText(event.values[0] + "");
        yTextView.setText(event.values[1] + "");
        zTextView.setText(event.values[2] + "");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
