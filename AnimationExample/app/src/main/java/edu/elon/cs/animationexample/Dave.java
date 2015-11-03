package edu.elon.cs.animationexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

/**
 * A Dave!
 *
 * @author J. Hollingsworth
 */

public class Dave {

    protected float x, y;
    private float frameWidthScaled, frameHeightScaled;
    private Bitmap bitmap;

    private int screenWidth, screenHeight;

    private final float SCALE = 2.0f;

    // information about the sprite sheet
    private final int numFramesInSpriteSheet = 6;
    private int frameWidth, frameHeight;

    // starting frame
    private int frame = 0;

    public Dave(Context context) {

        // get the image
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dave);

        // frameWidthScaled/frameHeightScaled of a single frame (scaled)
        frameWidthScaled = (bitmap.getWidth() / numFramesInSpriteSheet) * SCALE;
        frameHeightScaled = bitmap.getHeight() * SCALE;

        // frameWidthScaled/frameHeightScaled of a single frame (not scaled)
        frameWidth = (int) (bitmap.getWidth() / numFramesInSpriteSheet);
        frameHeight = (int) bitmap.getHeight();

        // figure out the screen frameWidthScaled
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // start in center
        x = screenWidth/2;
        y = screenHeight/2;
    }

    public void doDraw(Canvas canvas) {

        // first Rect uses sprite sheet coordinates (not scaled)
        // second Rect uses screen coordinated (scaled)
        // first Rect --> drawn into second Rect
        canvas.drawBitmap(bitmap,
                new Rect(frameWidth * frame,
                        0,
                        frameWidth * frame + frameWidth,
                        frameHeight),
                new Rect((int) (x - frameWidthScaled /2), (int) (y - frameHeightScaled /2),
                        (int) (x + frameWidthScaled /2), (int) (y + frameHeightScaled /2)),
                null);
    }

    private final double STEP = 0.25;       // time (in s) between animation steps
    private final double ANIM_TIME = 5.0;   // how long to do one type of animation
    double step = 0.0;                      // timer for next animation step
    double timer = 0.0;                     // timer for next type of animation

    // what can our sprite do?
    private enum State { WALKING, JUMPING, FALLING };
    private State state = State.WALKING;    // start in WALKING state
    private int [] walking = {0, 1};        // walking = frame[0], frame[1], frame[0], ...
    private int [] jumping = {2, 3};        // jumping = frame[2], frame[3], frame[2], ...
    private int [] falling = {4, 5};        // falling = frame[4], frame[5], frame[4], ...
    private int which = 0;  // which index in a particular animation

    public void doUpdate(double elapsed, float touchX ,float touchY) {

        timer = timer + elapsed;        // how much time has elapsed?
        if (timer > ANIM_TIME) {        // time to switch animations
            if (state == State.WALKING) {
                state = State.JUMPING;
            } else if (state == State.JUMPING) {
                state = State.FALLING;
            } else {
                state = State.WALKING;
            }

            timer = 0.0;                // reset timer
        }

        step = step + elapsed;          // how much time has elapsed?
        if (step > STEP) {              // time to move to the next frame in the animation
            if (state == State.WALKING) {
                which = (which + 1) % walking.length;
                frame = walking[which];
            }

            if (state == State.JUMPING) {
                which = (which + 1) % jumping.length;
                frame = jumping[which];
            }

            if (state == State.FALLING) {
                which = (which + 1) % falling.length;
                frame = falling[which];
            }

            step = 0.0;                 // reset timer
        }

    }
}