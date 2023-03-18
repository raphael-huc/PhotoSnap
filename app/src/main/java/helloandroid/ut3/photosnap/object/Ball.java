package helloandroid.ut3.photosnap.object;

import android.graphics.Rect;
import android.widget.ImageView;

public class Ball extends ObjectImageView {

    public Ball(ImageView goalView) {
        super(goalView);
    }

    public Rect getCollisionShapInPosition (int x, int y) {
        return new Rect(
                (int) x,
                (int) y,
                (int) (x + getWidth()),
                (int) (y + getHeight()));
    }
}
