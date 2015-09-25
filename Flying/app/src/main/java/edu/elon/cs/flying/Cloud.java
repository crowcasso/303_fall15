package edu.elon.cs.flying;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

/**
 * A flying cloud -- wait do clouds fly?
 *
 * @author J. Hollingsworth
 */

public class Cloud {

    protected float x, y;
    private  float speed;
    private float width, height;
    private Bitmap bitmap;

    private final float SCALE = 1.0f;
    private final float MAX_SPEED = 3.0f;
    private final float MIN_SPEED = 1.0f;

    private int screenWidth, screenHeight;

    public Cloud(Context context) {

        // get the image
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);

        // scale the size
        width = bitmap.getWidth() * SCALE;
        height = bitmap.getHeight() * SCALE;

        // figure out the screen width
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // start in top/left corner
        x = (float) (Math.random() * screenWidth);
        y = (float) (Math.random() * screenHeight);

        // random speed
        speed = (float) (Math.random() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED);
    }

    public void doDraw(Canvas canvas) {
        // draw the cloud
        canvas.drawBitmap(bitmap,
                null,
                new Rect((int) (x - width/2), (int) (y- height/2),
                        (int) (x + width/2), (int) (y + height/2)),
                null);
    }

    public void doUpdate(double elapsed) {
        // easing based on touch point
        x = (x + speed);

        // Is the cloud off the screen?
        if (x - width/2 > screenWidth) {
            // make it look like a new cloud appears
            x = -width/2;
            y = (float) (Math.random() * screenHeight);
            speed = (float) (Math.random() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED);
        }
    }
}