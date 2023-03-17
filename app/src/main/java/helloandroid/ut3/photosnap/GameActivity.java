package helloandroid.ut3.photosnap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import helloandroid.ut3.photosnap.sensors.AcceleroMeterSensor;
import helloandroid.ut3.photosnap.sensors.LightSensor;
import helloandroid.ut3.photosnap.sensors.OnLightChangeListener;
import helloandroid.ut3.photosnap.sensors.OnMouvementListener;

public class GameActivity extends AppCompatActivity implements OnLightChangeListener, OnMouvementListener {

    private LightSensor lightSensor;
    private AcceleroMeterSensor acceleroMeterSensor;
    private ImageView balle;

    public Bitmap bitmap;
    private ConstraintLayout gameView;
    private Drawable gameBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get picture
        bitmap = getIntent().getParcelableExtra("bitmap");

        System.out.println("----------- bitmap -------------");
        System.out.println(bitmap);
        System.out.println("----------- bitmap -------------");
        lightSensor = new LightSensor(this, this);
        acceleroMeterSensor = new AcceleroMeterSensor(this,this);

        setContentView(R.layout.activity_game);
        //convert bitmap to Drawable
        gameBackground = new BitmapDrawable(getResources(), bitmap);
        // create game interface
        gameView = findViewById(R.id.Game);
        //set background of game interface
        gameView.setBackground(gameBackground);
        //add balle in game interface
        balle = findViewById(R.id.balle);
    }

    @Override
    public void onLightChange(int lightLevel) {
        ImageView v= findViewById(R.id.balle);
        if(lightLevel==0){
            v.setImageResource(R.drawable.balledark);

        }
        if(lightLevel==1){
            v.setImageResource(R.drawable.balle);

        }
        if(lightLevel==2){
            v.setImageResource(R.drawable.balledark);

        }


    }

    @Override
    public void onMouvementChange(int x, int y) {
        //here Jossy
        Toast.makeText(getApplicationContext(),"x :"+x+" y:"+y,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(acceleroMeterSensor!=null) {
            acceleroMeterSensor.onStop();
        }
        if(lightSensor!=null){
            lightSensor.onStop();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(acceleroMeterSensor!=null) {
            acceleroMeterSensor.onStart();
        }
        if(lightSensor!=null){
            lightSensor.onStart();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(acceleroMeterSensor!=null) {
            acceleroMeterSensor.onResume();
        }
        if(lightSensor!=null){
            lightSensor.onResume();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(acceleroMeterSensor!=null) {
            acceleroMeterSensor.onPause();
        }
        if(lightSensor!=null){
            lightSensor.onPause();
        }
    }

}