package cm.socialfitnesshub.SedentaryLifestyleMonitoring;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.view.View;

import cm.socialfitnesshub.R;

public class MotionReaderService extends Service implements SensorEventListener {

    private final int PENALTY = 3;
    private final int GROWTH = 5;

    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private float sensitivity;

    private SharedPreferences.Editor preferencesEditor;
    private SharedPreferences sp;

    public MotionReaderService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Set variables related to sensors before loading the views.
        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMan.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        sensitivity = 20.0f / getSharedPreferences("SedentaryLifestyleMonitoringPreferences", MODE_PRIVATE).getInt("sensitivity", 10);

        preferencesEditor = getSharedPreferences("SedentaryLifestyleMonitoring", MODE_PRIVATE).edit();
        sp = getSharedPreferences("SedentaryLifestyleMonitoring", MODE_PRIVATE);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            // The lower the more sensitive.
            if(mAccel > sensitivity){

                int secondsSinceMoved = (int) (System.currentTimeMillis() - sp.getLong("last-moved", 0)) / 1000;
                if(sp.getInt("index", 0) > 100) {
                    preferencesEditor.putInt("index", 100);
                } else if(sp.getInt("index", 0) < 0) {
                    preferencesEditor.putInt("index", 0);
                }

                if(secondsSinceMoved > 1) {
                    preferencesEditor.putLong("last-moved", System.currentTimeMillis());
                    preferencesEditor.putInt("index", sp.getInt("index", 0) + secondsSinceMoved/GROWTH - PENALTY);
                }

                System.out.println(sp.getInt("index", 0));
                preferencesEditor.apply();

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }
}
