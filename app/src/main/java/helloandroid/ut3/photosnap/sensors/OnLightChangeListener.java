package helloandroid.ut3.photosnap.sensors;

public interface OnLightChangeListener {
    public void onLightChange(int lightLevel);
}
/* To use the light listener:
    1- lightSensor = new LightSensor(this,this);
        1 param activity context
        2 a object that implement this interface
    2-put all your code in the onLightChange function...
        -lightlevel the current light that the sensor detect
        -take care that I Call this function in the construction of the sensor to get the firstlevel
 */