package helloandroid.ut3.photosnap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitmap = getIntent().getParcelableExtra("bitmap");
        System.out.println("----------- bitmap -------------");
        System.out.println(bitmap);
        System.out.println("----------- bitmap -------------");

        setContentView(R.layout.activity_game);
    }
}