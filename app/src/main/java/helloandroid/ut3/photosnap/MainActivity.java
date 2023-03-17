package helloandroid.ut3.photosnap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import helloandroid.ut3.battlewhat.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickStart(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
}