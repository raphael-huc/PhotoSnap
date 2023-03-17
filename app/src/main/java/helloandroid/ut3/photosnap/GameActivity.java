package helloandroid.ut3.photosnap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    private ImageView balle;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitmap = getIntent().getParcelableExtra("bitmap");
        System.out.println("----------- bitmap -------------");
        System.out.println(bitmap);
        System.out.println("----------- bitmap -------------");

        setContentView(R.layout.activity_game);

        balle = findViewById(R.id.balle);
    }
}