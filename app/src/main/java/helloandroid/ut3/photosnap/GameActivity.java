package helloandroid.ut3.photosnap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    private ImageView balle;
    public Bitmap bitmap;
    private ConstraintLayout gameView;
    private Drawable gameBackground;

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
    }
}