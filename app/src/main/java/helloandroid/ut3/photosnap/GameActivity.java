package helloandroid.ut3.photosnap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    private ImageView balle;
    public Bitmap bitmap;
    private ConstraintLayout gameView;
    private Drawable gameBackground;
    private int vitesse = 4;
    private int ballePositionY;
    private int ballePositionX;
    private int ordonnee;
    private int abscisse;
    private Handler bHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get picture
        bitmap = getIntent().getParcelableExtra("bitmap");
        setContentView(R.layout.activity_game);
        //convert bitmap to Drawable
        gameBackground = new BitmapDrawable(getResources(), bitmap);
        // create game interface
        gameView = findViewById(R.id.Game);
        //set background of game interface
        gameView.setBackground(gameBackground);
        //add balle in game interface
        balle = findViewById(R.id.balle);

        ballePositionY = 0;
        ballePositionX = 0;
    }

    /**
     * un Runnable qui sera appelé par le timer pour la gestion du mouvement du player
     */
    private final Runnable mUpdateBallePositionTime = new Runnable() {
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
        if (y>0){
            direction = 1;
        }
        //go down
        if (y<0){
            direction = 2;
        }
        //go right (droite)
        if (x>0){
            direction = 3;
        }
        //go left (gauche)
        if (x<0){
            direction = 4;
        }

        switch(direction){
            case 1:
                ballePositionY += vitesse;
                balle.setY((float) ballePositionY);
                //balle.setPositionY(ballePositionY);
                System.out.println("go up");
                break;
            case 2:
                ballePositionY -= vitesse;
                balle.setY((float) ballePositionY);
                //balle.setPositionY(ballePositionY);
                System.out.println("go down");
                break;
            case 3:
                ballePositionX += vitesse;
                balle.setX((float) ballePositionY);
                //balle.setPositionX(ballePositionX);
                System.out.println("go right (droite)");
                break;
            case 4:
                ballePositionX -= vitesse;
                balle.setX((float) ballePositionY);
                //balle.setPositionX(ballePositionX);
                System.out.println("go left (gauche)");
                break;
            default:
                System.out.println("immobile");
                break;
        }
    }
}