package helloandroid.ut3.photosnap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import helloandroid.ut3.photosnap.object.Ball;
import helloandroid.ut3.photosnap.object.Goal;
import helloandroid.ut3.photosnap.sensors.AcceleroMeterSensor;
import helloandroid.ut3.photosnap.sensors.LightSensor;
import helloandroid.ut3.photosnap.sensors.OnLightChangeListener;
import helloandroid.ut3.photosnap.sensors.OnMouvementListener;

public class GameActivity extends AppCompatActivity implements OnLightChangeListener, OnMouvementListener, View.OnTouchListener {

    private LightSensor lightSensor;
    private AcceleroMeterSensor acceleroMeterSensor;
    private Ball balle;
    private Goal goal;

    public Bitmap bitmap;
    public ConstraintLayout gameView;
    private Drawable gameBackground;
    private int vitesse = 4;
    private int ballePositionY;
    private int ballePositionX;
    private int ordonnee;
    private int abscisse;
    private Handler bHandler;

    private float lastX;
    private float lastY;
    private float deltaX;
    private float deltaY;
    private long startTimeSwipe;
    private static final int SWIPE_THRESHOLD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get picture
        bitmap = getIntent().getParcelableExtra("bitmap");

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
        balle = new Ball(findViewById(R.id.balle));
        goal = new Goal(findViewById(R.id.cage_foot));

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        //balle.setX(((float) screenWidth)/2);
        //balle.setY(((float) screenHeight)/2);

        ballePositionX = screenWidth/2;
        ballePositionY = screenHeight/2;
        //System.out.println("***********X: "+ballePositionX+" ballePositionY: "+ballePositionY);
        bHandler = new Handler();
        gameView.setOnTouchListener(this);
    }

    /**
     * un Runnable qui sera appelé par le timer pour la gestion du mouvement du player
     */
    private Runnable mUpdateBallePositionTime = new Runnable() {
        public void run() {
            // mettre à jour la position du joueur
            bHandler.postDelayed(this, 20);
            updateBallePosition(abscisse, ordonnee);
        }
    };

    // méthode pour démarrer l'exécution du Runnable initial avec une durée spécifiée
    private void startUpdateBallePositionTimeWithDuration(int x, int y, long duration) {
        abscisse = x; // modifier la valeur de ballePositionX
        ordonnee = y; // modifier la valeur de ballePositionY
        //System.out.println("startUpdateBallePositionTimeWithDuration***********X: "+ballePositionX+" ballePositionY: "+ballePositionY);
        bHandler.post(mUpdateBallePositionTime);
        bHandler.postDelayed(() -> {
            abscisse = x; // modifier la valeur de ballePositionX
            ordonnee = y; // modifier la valeur de ballePositionY
            stopUpdatePlayerPositionTime();
        }, duration);
    }

    // méthode pour arrêter spécifiquement l'exécution du Runnable initial
    private void stopUpdatePlayerPositionTime() {
        bHandler.removeCallbacks(mUpdateBallePositionTime);
    }



    public void updateBallePosition(int x, int y) {
        int direction = 0;
        //go up
        if (y<0 && Math.abs(y) > Math.abs(x)){
            direction = 1;
        }
        //go down
        if (y>0 && Math.abs(y) > Math.abs(x)){
            direction = 2;
        }
        //go right (droite)
        if (x>0 && Math.abs(x) > Math.abs(y)){
            direction = 3;
        }
        //go left (gauche)
        if (x<0 && Math.abs(x) > Math.abs(y)){
            direction = 4;
        }

        switch(direction){
            case 1:
                ballePositionY -= vitesse;
                balle.setY((float) ballePositionY);
                //System.out.println("***********go up");
                break;
            case 2:
                ballePositionY += vitesse;
                balle.setY((float) ballePositionY);
                //System.out.println("***********go down");
                break;
            case 3:
                ballePositionX += vitesse;
                balle.setX((float) ballePositionX);
                //System.out.println("************go right (droite)");
                break;
            case 4:
                ballePositionX -= vitesse;
                balle.setX((float) ballePositionX);
                //System.out.println("*********go left (gauche)");
                break;
            default:
                System.out.println("*********immobile");
                break;
        }
    }


    /**
     * When we touche screen, move balle
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case (MotionEvent.ACTION_DOWN) :
                // Enregistre la position de départ du toucher
                lastY = event.getY();
                lastX = event.getX();
                // Enregistre le temps de départ
                startTimeSwipe = SystemClock.elapsedRealtime();
                v.setTag(startTimeSwipe); // Stocke la valeur de startTime dans la propriété tag de la vue
                return true;
            case (MotionEvent.ACTION_MOVE) :
                // Calcule la distance parcourue depuis le toucher initial
                deltaY = event.getY() - lastY;
                deltaX = event.getX() - lastX;
                return true;
            case (MotionEvent.ACTION_UP) :
                long startTime = (Long) v.getTag(); // Récupère la valeur de startTime à partir de la propriété tag de la vue
                long elapsedTime = SystemClock.elapsedRealtime() - startTime; // Calcule le temps écoulé
                if(elapsedTime > 100L){
                    startUpdateBallePositionTimeWithDuration((int) deltaX, (int) deltaY, elapsedTime);
                }
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                return true;
            default :
                return super.onTouchEvent(event);
        }
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
        //startUpdateBallePositionTimeWithDuration(x, y, 500L);
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

    private boolean checkIfBallInGoal() {
        System.out.println("----- HERE -----");
        return Rect.intersects(balle.getCollisionShape(),
                goal.getCollisionShape());
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