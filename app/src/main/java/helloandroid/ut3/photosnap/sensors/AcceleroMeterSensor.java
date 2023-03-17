package helloandroid.ut3.photosnap.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AcceleroMeterSensor implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acceleroMeterSensor;
    private OnMouvementListener onMouvementListener;
    private final Context context;
    private float x, y, z, last_x=-99, last_y=-99, last_z=-99;
    private boolean isFirstValue;
    private float shakeThreshold = 0f;


    public AcceleroMeterSensor(Context context, OnMouvementListener mShakeListener) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        acceleroMeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.onMouvementListener = mShakeListener;
    }
    public AcceleroMeterSensor(Context context, OnMouvementListener mShakeListener,int baseX,int baseY,int baseZ) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        acceleroMeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        last_x=baseX;
        last_y=baseY;
        last_z=baseZ;
        this.onMouvementListener = mShakeListener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        if (isFirstValue) {
            float deltaX = Math.abs(last_x - x);
            float deltaY = Math.abs(last_y - y);
            float deltaZ = Math.abs(last_z - z);
            // If the values of acceleration have changed on at least two
            //axes, then we assume that we are in a shake motion
            if ((deltaX > shakeThreshold && deltaY > shakeThreshold)
                    || (deltaX > shakeThreshold && deltaZ > shakeThreshold)
                    || (deltaY > shakeThreshold && deltaZ > shakeThreshold)) {
                if((int)x != (int) last_x && (int)y != (int) last_y)
                if (onMouvementListener != null) {
                    onMouvementListener.onMouvementChange((int)x,(int)y);
                }
            }
        }
        last_x = x;
        last_y = y;
        last_z = z;
        isFirstValue = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //accelerometer does not report accuracy changes
    }

    //register the listener once the activity starts
    public void onStart() {
        if (acceleroMeterSensor != null) {
            sensorManager.registerListener(this, acceleroMeterSensor, sensorManager.SENSOR_DELAY_NORMAL);
        }
        acceleroMeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    //stop the sensor when the activity stops to reduce battery usage
    public void onStop() {
        sensorManager.unregisterListener(this);
    }

    //resume the sensor when the activity stops to reduce battery usage
    public void onResume() {
        sensorManager.registerListener(this, acceleroMeterSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }
}
