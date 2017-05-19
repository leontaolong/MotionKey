package edu.uw.longt8.motionkey;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.inputmethodservice.InputMethodService;

/**
 * Created by Leon on 5/18/17.
 */

public class MotionKey extends InputMethodService implements SensorEventListener {

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
