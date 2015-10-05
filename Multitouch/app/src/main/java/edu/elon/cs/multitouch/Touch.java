package edu.elon.cs.multitouch;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Represents a single touch as a circle.
 *
 * @author J. Hollingsworth
 */
public class Touch {

    protected float x, y;
    protected int pointerID;
    private final int SIZE = 150;
    private Paint paint;

    public Touch(float x, float y, int pointerID) {
        this.x = x;
        this.y = y;
        this.pointerID = pointerID;

        paint = new Paint();
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        paint.setARGB(255, red, green, blue);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawCircle(x, y, SIZE, paint);
    }
}