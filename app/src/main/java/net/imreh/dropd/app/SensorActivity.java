package net.imreh.dropd.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import static android.util.FloatMath.sqrt;

public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mGravity;
    private static final String TAG = "DropdSensor";

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float gx = event.values[0];
        float gy = event.values[1];
        float gz = event.values[2];
        float total = sqrt(gx * gx + gy * gy + gz * gz);
        // Do something with this sensor value.
        // Log.i(TAG, "gravity = " + gx + ";" + gy + ";" + gz);
        if (total < 2.0) {
            Log.i(TAG, "acceleration< = " + total + "->" + gx + ";" + gy + ";" + gz);
        } else if (total > 15.0){
            Log.i(TAG, "acceleration> = " + total + "->" + gx + ";" + gy + ";" + gz);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "sensor registered");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        Log.i(TAG, "sensor unregistered");
    }
}