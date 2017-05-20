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
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

/**
 * Created by Leon on 5/18/17.
 */

public class MotionKey extends InputMethodService implements SensorEventListener {

    private static final String TAG = "MotionKey";

    private FrameLayout keyboardView;
    private DrawingView view;

    private InputMethodManager mInputMethodManager;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private ArrayList<Float> valueXList;
    private ArrayList<Float> valueYList;
    private ArrayList<Float> valueZList;

    private int startingTime;

    private int xAxisSensitivity;
    private int yAxisSensitivity;
    private int zAxisSensitivity;

    @Override
    public void onCreate() {
        super.onCreate();

        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        // Create linear acceleration sensor
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        valueXList = new ArrayList<Float>();
        valueYList = new ArrayList<Float>();
        valueZList = new ArrayList<Float>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        xAxisSensitivity = valueOf(prefs.getString("xAxisSensitivity", "5"));
        yAxisSensitivity = valueOf(prefs.getString("yAxisSensitivity", "5"));
        zAxisSensitivity = valueOf(prefs.getString("zAxisSensitivity", "5"));
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (FrameLayout) getLayoutInflater().inflate(R.layout.input, null);
        view = (DrawingView) keyboardView.findViewById(R.id.drawingView);
        return keyboardView;
    }


    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);

        if (info.inputType == InputType.TYPE_CLASS_TEXT) {
            // check if input is text
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
            startingTime = (int) System.currentTimeMillis();

        } else {
            // Otherwise prompt the user to change to a different keyboard
            Toast.makeText(this, "Sorry, this input type is not supported.", Toast.LENGTH_SHORT).show();
            mInputMethodManager.showInputMethodPicker();
        }
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);
        mSensorManager.unregisterListener(this, mAccelerometer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mAccelerometer != null) {

            int endingTime = (int) System.currentTimeMillis();
            float[] values = event.values;
            Float valueX = values[0];
            Float valueY = values[1];
            Float valueZ = values[2];

            // the threshold for x-axis speed
            if (valueX > xAxisSensitivity || valueX < -xAxisSensitivity) {

                view.ball.dx = valueX * 10;
                valueXList.add(valueX);

                if (valueXList.size() > 5) {

                    InputConnection ic = getCurrentInputConnection();
                    ic.commitText(new String(new int[]{0x1F602}, 0, 1), 0);
                    valueXList.clear();
                }
            }

            // the threshold for y-axis speed
            if (valueY > yAxisSensitivity || valueY < -yAxisSensitivity) {

                view.ball.dy = valueY * 10;
                valueYList.add(valueY);

                if (valueYList.size() > 5) {
                    InputConnection ic = getCurrentInputConnection();
                    ic.commitText(new String(new int[]{0x1F60A}, 0, 1), 0);
                    valueYList.clear();
                }
            }

            if (valueZ > zAxisSensitivity || valueZ < -zAxisSensitivity) {
                valueZList.add(valueZ);
                if (valueZList.size() > 5) {
                    InputConnection ic = getCurrentInputConnection();
                    ic.commitText(new String(new int[]{0x1F60C}, 0, 1), 0);
                    valueZList.clear();
                }
            }

            // Clear the lists every 0.5 second
            int timeDiff = endingTime - startingTime;
            if (timeDiff > 500) {

                startingTime = endingTime;
                // clear the list
                valueXList.clear();
                valueYList.clear();
                valueZList.clear();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
