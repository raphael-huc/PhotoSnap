package helloandroid.ut3.photosnap.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class LightSensor implements SensorEventListener {

    //set instances for the sensorManager, light sensor, and textViews
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Context context;
    private OnLightChangeListener lightListener;
    private int lightLevel;


    public LightSensor(Context context, OnLightChangeListener lightListener) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightLevel = 1;
        this.lightListener = lightListener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            //retrieve the current value of the light sensor
            float currentValue = sensorEvent.values[0];
            int currentlightlevel = calculateLightLevel(currentValue);
            if (lightLevel != currentlightlevel) {
                lightLevel = currentlightlevel;
                if (this.lightListener != null) {
                    this.lightListener.onLightChange(lightLevel);
                }
            }

        }
    }

    public int calculateLightLevel(float sensorValue) {
        if (sensorValue <= 50) {
            return 0;
        } else if (sensorValue > 7000) {
            return 2;
        }

        return 1;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //ambient light sensor does not report accuracy changes
    }

    //register the listener once the activity starts
    public void onStart() {
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, sensorManager.SENSOR_DELAY_FASTEST);
        }
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    //stop the sensor when the activity stops to reduce battery usage
    public void onStop() {
        sensorManager.unregisterListener(this);
    }

    //resume the sensor when the activity stops to reduce battery usage
    public void onResume() {
        sensorManager.registerListener(this, lightSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void onPause() {
        sensorManager.unregisterListener(this);
    }
}
