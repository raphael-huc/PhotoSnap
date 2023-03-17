package helloandroid.ut3.photosnap.object;

import android.graphics.Rect;
import android.widget.ImageView;

public class ObjectImageView {

    private final ImageView imageView;

    public ObjectImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Rect getCollisionShape () {
        return new Rect(
                (int) getPositionX(),
                (int) getPositionY(),
                (int) (getPositionX() + getWidth()),
                (int) (getPositionY() + getHeight()));
    }

    public float getPositionX() {
        return imageView.getX();
    }

    public float getPositionY() {
        return imageView.getY();
    }

    public void setPositionX(float x) {
        imageView.setX(x);
    }

    public void setPositionY(float y) {
        imageView.setY(y);
    }


    public int getWidth(){
        return imageView.getWidth();
    }

    public int getHeight(){
        return imageView.getHeight();
    }
}
