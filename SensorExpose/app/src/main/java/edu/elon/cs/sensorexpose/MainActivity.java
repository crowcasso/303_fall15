package edu.elon.cs.sensorexpose;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor mAccel;

    private TextView xTextView, yTextView, zTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        xTextView = (TextView) findViewById(R.id.accel_x);
        yTextView = (TextView) findViewById(R.id.accel_y);
        zTextView = (TextView) findViewById(R.id.accel_z);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);

    }


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
