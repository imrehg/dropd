package net.imreh.dropd.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;

import static android.util.FloatMath.sqrt;

public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mGravity;
    private SoundPool sp;
    private int[] soundIds;
    private static final String TAG = "DropdSensor";
    private boolean isFlying;
    private int screamId, ouchId;
    private long lastStop;

    public SensorActivity() {
        sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        lastStop = 0;
        soundIds = new int[2];
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i= new Intent(this, ScreamService.class);
        startService(i);
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//
//        soundIds[0] = sp.load(this, R.raw.wilhem_scream, 1);
//        soundIds[1] = sp.load(this, R.raw.ouch, 1);
//        isFlying = false;
//        screamId = -1;
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
//        float gx = event.values[0];
//        float gy = event.values[1];
//        float gz = event.values[2];
//        float total = sqrt(gx * gx + gy * gy + gz * gz);
//        // Do something with this sensor value.
//        // Log.i(TAG, "gravity = " + gx + ";" + gy + ";" + gz);
//        if (total < 1.0) {
//            Log.i(TAG, "acceleration< = " + total + "->" + gx + ";" + gy + ";" + gz);
//            if (!isFlying && (screamId < 0) && (event.timestamp - lastStop > 1e9)) {
//                screamId = sp.play(soundIds[0], 100, 100, 1, -1, (float) 1.0);
//                Log.i(TAG, "playScream");
//                isFlying = true;
//            }
//        } else if (total > 11.0){
//            Log.i(TAG, "acceleration> = " + total + "->" + gx + ";" + gy + ";" + gz);
//            if (isFlying) {
//                lastStop = event.timestamp;
//                sp.stop(screamId);
//                ouchId = sp.play(soundIds[1], 100, 100, 1, 0, (float) 1.0);
//                Log.i(TAG, "playOuch");
//                isFlying = false;
//                screamId = -1;
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "sensor registered");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mSensorManager.unregisterListener(this);
        Log.i(TAG, "sensor unregistered");
    }
}