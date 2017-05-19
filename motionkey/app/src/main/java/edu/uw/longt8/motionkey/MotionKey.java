package edu.uw.longt8.motionkey;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.inputmethodservice.InputMethodService;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Leon on 5/18/17.
 */

public class MotionKey extends InputMethodService implements SensorEventListener {

    private static final String TAG = "MotionKeyboard";

    private FrameLayout motionKeyboardView;
    private DrawingView view;

    private InputMethodManager mInputMethodManager;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private ArrayList<Float> valueXList;
    private ArrayList<Float> valueYList;
    private ArrayList<Float> valueZList;

    private int startingTime;

    private int xAxisShakeSensitivity;
    private int yAxisShakeSensitivity;
    private int zAxisShakeSensitivity;
    private int ballMovingSpeed;

    @Override
    public void onCreate() {
        super.onCreate();

        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        // Create linear acceleration sensor (XYZ, gravity excluded)
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        valueXList = new ArrayList<Float>();
        valueYList = new ArrayList<Float>();
        valueZList = new ArrayList<Float>();

        // User preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        xAxisShakeSensitivity = 10;
        yAxisShakeSensitivity = 10;
        zAxisShakeSensitivity = 10;
        ballMovingSpeed = 10;
    }

    @Override
    public View onCreateInputView() {

        // Inflate "MotionKeyboard" view
        motionKeyboardView = (FrameLayout) getLayoutInflater().inflate(R.layout.input, null);

        view = (DrawingView)motionKeyboardView.findViewById(R.id.drawingView);

        return motionKeyboardView;
    }


    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);

        if(info.inputType == InputType.TYPE_CLASS_TEXT) {
            // If the input type is text, register the sensor
            // Use the fast (game speed) delay
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
            startingTime = (int) System.currentTimeMillis();

        } else {
            // Otherwise prompt the user to change to a different keyboard
            Toast.makeText(this, "Sorry! MotionKey doesn't support this input type", Toast.LENGTH_SHORT).show();
            mInputMethodManager.showInputMethodPicker();
        }
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);

        // Unregister the sensor when the input is finished
        mSensorManager.unregisterListener(this, mAccelerometer);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
