package net.imreh.dropd.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import static android.util.FloatMath.sqrt;

public class ScreamService extends Service implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mGravity;
    private SoundPool sp;
    private int[] soundIds;
    private static final String TAG = "DropdSensorBack";
    private boolean isFlying;
    private int screamId, ouchId;
    private long lastStop;

    public ScreamService() {
        sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        lastStop = 0;
        soundIds = new int[2];
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "sensor registered");

        soundIds[0] = sp.load(this, R.raw.wilhem_scream, 1);
        soundIds[1] = sp.load(this, R.raw.ouch, 1);
        isFlying = false;
        screamId = -1;

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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
        if (total < 1.0) {
            Log.i(TAG, "acceleration< = " + total + "->" + gx + ";" + gy + ";" + gz);
            if (!isFlying && (screamId < 0) && (event.timestamp - lastStop > 1e9)) {
                screamId = sp.play(soundIds[0], 100, 100, 1, -1, (float) 1.0);
                Log.i(TAG, "playScream");
                isFlying = true;
            }
        } else if (total > 11.0){
            Log.i(TAG, "acceleration> = " + total + "->" + gx + ";" + gy + ";" + gz);
            if (isFlying) {
                lastStop = event.timestamp;
                sp.stop(screamId);
                ouchId = sp.play(soundIds[1], 100, 100, 1, 0, (float) 1.0);
                Log.i(TAG, "playOuch");
                isFlying = false;
                screamId = -1;
            }
        }
    }


    @Override
    public final void onDestroy() {
        mSensorManager.unregisterListener(this);
        Log.i(TAG, "sensor unregistered");
    }
}
