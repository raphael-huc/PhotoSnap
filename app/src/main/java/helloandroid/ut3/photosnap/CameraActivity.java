package helloandroid.ut3.photosnap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Bitmap photo;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Context context;
    private Bitmap generatedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_camera);
        imageView = findViewById(R.id.cameraView);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

    public void onClickTakePhoto(View v)
    {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    public void getRedGranterThanGreenPixel() {
        // Wait for the thread to finish
        if(photo != null) {
            Thread thread = new Thread(generateBitmapRunnable);
            thread.start();
            try {
                System.out.println("------ Thread start ------");
                thread.join();
                System.out.println("------ Thread finish ------");
                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra("bitmap", generatedImage);
                intent.putExtra("generatedColisionImage", generatedImage);
                startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Take a picture first !",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickStartGame(View v) {
        getRedGranterThanGreenPixel();
    }

    // Create a Runnable that generates the Bitmap pixel by pixel
    Runnable generateBitmapRunnable = new Runnable() {
        @Override
        public void run() {
            generateImageBaseOnRed();
        }
    };

    private void generateImageBaseOnRed() {

        final int height = photo.getHeight();
        final int width = photo.getWidth();
        generatedImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = photo.getPixel(x, y);

                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                if (red >= 200  && blue < 180 && green < 180) {
                    generatedImage.setPixel(x, y, Color.argb(255, 255, 0, 0));
                } else
                if (red >= 125 && blue < 100 && green < 100) {
                    generatedImage.setPixel(x, y, Color.argb(255, 255, 0, 0));
                } else
                if (red >= 80 && blue < 60 && green < 60) {
                    generatedImage.setPixel(x, y, Color.argb(255, 255, 0, 0));
                } else {
                    generatedImage.setPixel(x, y, pixel);
                }
            }
        }
    }

    // Simple generator, less precise
    private void generateImageBaseOnRedBase() {

        final int height = photo.getHeight();
        final int width = photo.getWidth();
        generatedImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = photo.getPixel(x, y);

                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                if (blue < red && green < red && red >= 80) {
                    generatedImage.setPixel(x, y, Color.argb(255, 255, 0, 0));
                } else {
                    generatedImage.setPixel(x, y, pixel);
                }
            }
        }
    }

}