package com.example.myapplication5;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class TiltDetector {
    public interface CallBack_moves {
        void moveLeft();

        void moveRight();
    }


    private SensorManager mSensorManager;
    private Sensor sensor;
    private long timeStamp = 0;


    private CallBack_moves CallBack_moves;

    public TiltDetector(Context context, CallBack_moves _callBack_steps) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.CallBack_moves = _callBack_steps;
    }

    public void start() {
        mSensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        mSensorManager.unregisterListener(sensorEventListener);
    }


    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            calculateMove(x);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    private void calculateMove(float x) {
        if (System.currentTimeMillis() - timeStamp > 300) {
            timeStamp = System.currentTimeMillis();
            if (x > 3.0) {
                if (CallBack_moves != null) {
                    CallBack_moves.moveLeft();
                }
            } else if (x < -3.0) {
                if (CallBack_moves != null) {
                    CallBack_moves.moveRight();
                }
            }


        }

    }

}

